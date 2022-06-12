package com.example.gardenmobileapp.system;

import android.util.Log;

import com.example.gardenmobileapp.model.Counter;
import com.example.gardenmobileapp.model.TurnOnOff;

public class LightSystem {
    public static final int MIN = 0;
    public static final int MAX = 0;

    private final TurnOnOff led1;
    private final TurnOnOff led2;

    private final Counter led3;
    private final Counter led4;

    public LightSystem(){
        this.led1 = new TurnOnOff();
        this.led2 = new TurnOnOff();

        this.led3 = new Counter(MIN, MAX);
        this.led4 = new Counter(MIN, MAX);
    }

    public void toggleLed1() { this.led1.toggle(); }
    public void toggleLed2() { this.led2.toggle(); }

    public void increaseLed3() { this.led3.increase(); }
    public void decreaseLed3() { this.led3.decrease(); }

    public void increaseLed4() { this.led4.increase(); }
    public void decreaseLed4() { this.led4.decrease(); }

    public String led1ToString() { return this.led1.getIsOnToString(); }
    public String led2ToString() { return this.led2.getIsOnToString(); }

    public String led3ToString() { return this.led3.getCountToString(); }
    public String led4ToString() { return this.led4.getCountToString(); }

}
