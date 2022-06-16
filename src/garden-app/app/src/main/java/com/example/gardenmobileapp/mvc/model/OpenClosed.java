package com.example.gardenmobileapp.mvc.model;

public class OpenClosed {
    private static final boolean OPEN = true;
    private boolean isOpen;

    public OpenClosed() {
        this.isOpen = !OPEN;
    }

    public void open() {
        this.isOpen = OPEN;
    }

    public void close() {
        this.isOpen = !OPEN;
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public String getIsOpenToString() {
        return this.isOpen ? "OPEN" : "CLOSED";
    }

    public String getIsOpenForArduino(){
        return this.isOpen ? "1" : "0";
    }

    public void toggle() {
        this.isOpen = !this.isOpen;
    }

    public void reset() { this.isOpen = !OPEN; }
}
