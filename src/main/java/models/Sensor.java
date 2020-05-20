package models;

public class Sensor {

    private int id;
    private double temperature;
    private double humidity;
    private String date;
    private String time;

    public Sensor(int id, double temperature, double humidity, String date, String time) {
        this.id = id;
        this.temperature = temperature;
        this.humidity = humidity;
        this.date = date;
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }
    public double getHumidity() {
        return humidity;
    }

    public String getDate() {
        return date;
    }

    public String getTime(){
        return time;
    }

}
