package models;

import java.time.LocalDateTime;
import java.util.Date;

public class SensorClass {

    int id;
    int temperature;
    LocalDateTime time;

    public SensorClass(int id, int temperature) {
        this.id = id;
        this.temperature = temperature;
    }

    SensorClass(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

}
