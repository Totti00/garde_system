package com.example.gardenmobileapp.State;

public enum StateGarden {
    AUTOMATIC("AUTOMATIC"),
    MANUAL("MANUAL"),
    ALLER("ALLER");

    private final String name;

    private StateGarden(String name) { this.name = name; }

    public String getName() { return this.name; }
}
