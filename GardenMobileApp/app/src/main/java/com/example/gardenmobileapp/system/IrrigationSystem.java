package com.example.gardenmobileapp.system;

import com.example.gardenmobileapp.model.Counter;
import com.example.gardenmobileapp.model.OpenClosed;

public class IrrigationSystem {
    public static final int MIN = 0;
    public static final int MAX = 5;

    private final OpenClosed irrigation;
    private final Counter counter;

    public IrrigationSystem(){
        this.irrigation = new OpenClosed();
        this.counter = new Counter(MIN, MAX);
    }

    public void toggle() { this.irrigation.toggle(); }

    public void increase() { this.counter.increase(); }
    public void decrease() { this.counter.decrease(); }
    public String counterToString() { return this.counter.getCountToString(); }

    public String isOpenToString() { return this.irrigation.getIsOpenToString(); }

}
