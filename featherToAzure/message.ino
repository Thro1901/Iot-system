#include <Adafruit_Sensor.h>
#include <ArduinoJson.h>
#include <DHT.h>

static DHT dht(DHT_PIN, DHT_TYPE);

void initSensor()
{
    dht.begin();
}

void handleValues() {
  String values = String(dht.readTemperature())+"\n"+String(dht.readHumidity())+"\n"+getTime();
 server.send(200, "text/plain", values);
}

void handleSaveToDB() {
  saveToDB = true;
  String res = "Values saved to database.";
  server.send(200, "text/plain", res);
}

bool readMessage(int messageId, char *payload)
{
    float temperature = dht.readTemperature();
    float humidity = dht.readHumidity();

    const int capacity = JSON_OBJECT_SIZE(15);
    StaticJsonDocument<capacity> doc;
    if (std::isnan(temperature))
        doc["temperature"].set(NULL);
    else
        doc["temperature"].set(temperature);
    
    if (std::isnan(humidity))
        doc["humidity"].set(NULL);
    else
        doc["humidity"].set(humidity);

    serializeJson(doc, payload,MESSAGE_MAX_LEN);
    return true;
}

void parseTwinMessage(char *message)
{
    StaticJsonDocument<15> jsonBuffer;
    DeserializationError err = deserializeJson(jsonBuffer, message);
    if (err) {
    Serial.print(F("deserializeJson() failed with code "));
    Serial.println(err.c_str());
    return;
    }
    interval = jsonBuffer["desired"]["interval"];
}
