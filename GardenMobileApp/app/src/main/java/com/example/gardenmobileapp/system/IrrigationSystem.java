package com.example.gardenmobileapp.system;

import android.widget.Button;

import com.example.gardenmobileapp.model.Counter;
import com.example.gardenmobileapp.model.OpenClosed;
import com.example.gardenmobileapp.view.CounterView;

public class IrrigationSystem {
    public static final int MIN = 0;
    public static final int MAX = 5;

    private final OpenClosed irrigation;
    private final Counter counter;

    private Button irrigationView;
    private CounterView counterView;

    public IrrigationSystem(){
        this.irrigation = new OpenClosed();
        this.counter = new Counter(MIN, MAX);
    }

    public void setIrrigationView(Button view) { this.irrigationView = view; }
    public void setCounterView(CounterView view) { this.counterView = view; }

    public void build() {
        this.irrigationView.setOnClickListener(l -> {
            this.toggle();
            this.irrigationView.setText(this.isOpenToString());
        });

        this.counterView.setClickIncrementBtn(l -> {
            this.increase();
            this.counterView.setText(this.counterToString());
        });
        this.counterView.setClickDecrementBtn(l -> {
            this.decrease();
            this.counterView.setText(this.counterToString());
        });

        this.irrigationView.setText(this.isOpenToString());
        this.counterView.setText(this.counterToString());
    }

    public void toggle() { this.irrigation.toggle(); }

    public void increase() { this.counter.increase(); }
    public void decrease() { this.counter.decrease(); }
    public String counterToString() { return this.counter.getCountToString(); }

    public String isOpenToString() { return this.irrigation.getIsOpenToString(); }

}
