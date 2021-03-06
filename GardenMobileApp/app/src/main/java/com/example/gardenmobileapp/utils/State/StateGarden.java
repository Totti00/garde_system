package com.example.gardenmobileapp.utils.State;

public enum StateGarden {
    AUTOMATIC("AUTOMATIC"),
    MANUAL("MANUAL"),
    ALARM("ALARM");

    private final String name;

    private StateGarden(String name) { this.name = name; }

    public String getName() { return this.name; }
}
