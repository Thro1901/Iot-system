package models;

public class SensorClass {




    int id;
    int temperature;


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



}
