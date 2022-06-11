package com.example.gardenmobileapp.model;

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
}
