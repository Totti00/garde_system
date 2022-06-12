package com.example.gardenmobileapp.model;

public class TurnOnOff {
    private static final boolean ON = true;
    private boolean isOn;

    public TurnOnOff() {
        this.isOn = false;
    }

    public void toggle() {
        this.isOn = !this.isOn;
    }

    public void turnOn() {
        this.isOn = ON;
    }

    public void turnOff() {
        this.isOn = !ON;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public String getIsOnToString() {
        return this.isOn ? "ON" : "OFF";
    }


}
