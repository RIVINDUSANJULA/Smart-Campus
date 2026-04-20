package com.smartcampus.model;

public class Sensor {
    private String id;
    private String type;
    private String status;
    private double currentValue;
    private String roomId;

    public Sensor() {}

    public Sensor(String id, String type, String status, String roomId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.roomId = roomId;
        this.currentValue = 0.0;
    }
}