package com.example.gardenmobileapp.system;

import android.widget.Button;

import com.example.gardenmobileapp.model.Counter;
import com.example.gardenmobileapp.model.TurnOnOff;
import com.example.gardenmobileapp.view.CounterView;

public class LightSystem {
    public static final int MIN = 0;
    public static final int MAX = 5;

    private final TurnOnOff led1;
    private final TurnOnOff led2;
    private final Counter led3;
    private final Counter led4;

    private Button led1View;
    private Button led2View;
    private CounterView led3View;
    private CounterView led4View;

    public LightSystem(){
        this.led1 = new TurnOnOff();
        this.led2 = new TurnOnOff();

        this.led3 = new Counter(MIN, MAX);
        this.led4 = new Counter(MIN, MAX);
    }

    public void setLed1View(Button view) { this.led1View = view; }
    public void setLed2View(Button view) { this.led2View = view; }
    public void setLed3View(CounterView view) { this.led3View = view; }
    public void setLed4View(CounterView view) { this.led4View = view; }

    public void build() {
        this.led1View.setOnClickListener(l -> {
            this.toggleLed1();
            this.led1View.setText(this.led1ToString());
        });
        this.led2View.setOnClickListener(l -> {
            this.toggleLed2();
            this.led2View.setText(this.led2ToString());
        });
        this.led3View.setClickIncrementBtn(l -> {
            this.increaseLed3();
            this.led3View.setText(this.led3ToString());
        });
        this.led3View.setClickDecrementBtn(l -> {
            this.decreaseLed3();
            this.led3View.setText(this.led3ToString());
        });
        this.led4View.setClickIncrementBtn(l -> {
            this.increaseLed4();
            this.led4View.setText(this.led4ToString());
        });
        this.led4View.setClickDecrementBtn(l -> {
            this.decreaseLed4();
            this.led4View.setText(this.led4ToString());
        });

        this.led1View.setText(this.led1ToString());
        this.led2View.setText(this.led2ToString());
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
