#include "index.h"
#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h>
#include <ESP8266WebServer.h>
#include <WiFiUdp.h>

#include <AzureIoTHub.h>
#include <AzureIoTProtocol_MQTT.h>
#include <AzureIoTUtility.h>

#include "config.h"

static bool messagePending = false;
static bool messageSending = true;
static bool saveToDB = false;
static unsigned long interval = INTERVAL;

// FYLL I DESSA UPPGIFTER
static char *connectionString = "";
static char *ssid = "";
static char *pass = "";
time_t epochTime;
ESP8266WebServer server(80);
unsigned long currentMillis = 0;

void blinkLED()
{
    digitalWrite(LED_PIN, HIGH);
    delay(500);
    digitalWrite(LED_PIN, LOW);
}

void initWifi()
{
    Serial.printf("Attempting to connect to SSID: %s.\r\n", ssid);
    WiFi.begin(ssid, pass);
    while (WiFi.status() != WL_CONNECTED)
    {
        uint8_t mac[6];
        WiFi.macAddress(mac);
        Serial.printf("You device with MAC address %02x:%02x:%02x:%02x:%02x:%02x connects to %s failed! Waiting 10 seconds to retry.\r\n",
                mac[0], mac[1], mac[2], mac[3], mac[4], mac[5], ssid);
        WiFi.begin(ssid, pass);
        delay(10000);
    }
    Serial.printf("Connected to wifi %s.\r\n", ssid);
    Serial.println(WiFi.localIP());
}

void handleRoot(){
 String s = MAIN_page;
 server.send(200, "text/html", s);
}

time_t getTime(){
  epochTime = time(NULL);
  return epochTime;
}

void initTime()
{
    configTime(1, 0, "pool.ntp.org", "time.nist.gov");

    while (true)
    {
        epochTime = time(NULL);

        if (epochTime == 28800)
        {
            Serial.println("Fetching NTP epoch time failed! Waiting 2 seconds to retry.");
            delay(2000);
        }
        else
        {
            Serial.printf("Fetched NTP epoch time is: %lu.\r\n", epochTime);
            break;
        }
    }
}

static IOTHUB_CLIENT_LL_HANDLE iotHubClientHandle;
void setup()
{
    pinMode(LED_PIN, OUTPUT);
    Serial.begin(115200);
    initWifi();
    initSensor();
    initTime();
    server.on("/",handleRoot);
    server.on("/saveToDB", handleSaveToDB);
    server.on("/getValues", handleValues);
    server.begin();
    iotHubClientHandle = IoTHubClient_LL_CreateFromConnectionString(connectionString, MQTT_Protocol);
    if (iotHubClientHandle == NULL)
    {
        Serial.println("Failed on IoTHubClient_CreateFromConnectionString.");
        while (1);
    }

    IoTHubClient_LL_SetOption(iotHubClientHandle, "product_info", "HappyPath_AdafruitFeatherHuzzah-C");
    
    IoTHubClient_LL_SetMessageCallback(iotHubClientHandle, receiveMessageCallback, NULL);
    
    IoTHubClient_LL_SetDeviceMethodCallback(iotHubClientHandle, deviceMethodCallback, NULL);
    IoTHubClient_LL_SetDeviceTwinCallback(iotHubClientHandle, twinCallback, NULL);
}

static int messageCount = 1;
void loop()
{
  server.handleClient();
  if (!messagePending && messageSending){
    if (millis() - currentMillis > interval || currentMillis == 0 || saveToDB){
        char messagePayload[MESSAGE_MAX_LEN];
        bool temperatureAlert = readMessage(messageCount, messagePayload);
        
        sendMessage(iotHubClientHandle, messagePayload, temperatureAlert);
        
        messageCount++;
        //delay(interval);
        if (saveToDB)
          saveToDB = false;
        else
          currentMillis = millis();
    }
  }

    IoTHubClient_LL_DoWork(iotHubClientHandle);
    delay(10);
}
