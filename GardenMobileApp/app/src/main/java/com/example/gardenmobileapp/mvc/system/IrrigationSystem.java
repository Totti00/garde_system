package com.example.gardenmobileapp.mvc.system;

import android.widget.Button;

import com.example.gardenmobileapp.btlib.BluetoothChannel;
import com.example.gardenmobileapp.command.ArduinoCommands;
import com.example.gardenmobileapp.mvc.model.Counter;
import com.example.gardenmobileapp.mvc.model.OpenClosed;
import com.example.gardenmobileapp.mvc.view.CounterView;

public class IrrigationSystem {
    public static final int MIN = 1;
    public static final int MAX = 5;
    private StateSystem stateSystem;

    private final OpenClosed irrigation;
    private final Counter counter;

    private Button irrigationView;
    private CounterView counterView;

    private BluetoothChannel btChannel;


    public IrrigationSystem(){
        this.irrigation = new OpenClosed();
        this.counter = new Counter(MIN, MAX);
    }

    public void setIrrigationView(Button view) { this.irrigationView = view; }
    public void setCounterView(CounterView view) { this.counterView = view; }
    public void setBtChannel(BluetoothChannel btChannel) { this.btChannel = btChannel; }

    public void build() {
        this.irrigationView.setText(this.isOpenToString());
        this.counterView.setText(this.counterToString());

        this.irrigationView.setOnClickListener(l -> {
            if(!this.stateSystem.isManual()) return;
            this.toggle();
            this.irrigationView.setText(this.isOpenToString());
            this.btChannel.sendMessage(ArduinoCommands.IRRIGATION + this.irrigation.getIsOpenForArduino());
        });

        this.counterView.setClickIncrementBtn(l -> {
            if(!this.stateSystem.isManual()) return;
            if(!this.irrigation.isOpen()) return;
            this.increase();
            this.counterView.setText(this.counterToString());
            this.btChannel.sendMessage(ArduinoCommands.COUNTER_IRRIGATION + this.counter.getCountToString());

        });

        this.counterView.setClickDecrementBtn(l -> {
            if(!this.stateSystem.isManual()) return;
            if(!this.irrigation.isOpen()) return;
            this.decrease();
            this.counterView.setText(this.counterToString());
            this.btChannel.sendMessage(ArduinoCommands.COUNTER_IRRIGATION + this.counter.getCountToString());

        });
    }

    public void toggle() { this.irrigation.toggle(); }

    public void increase() { this.counter.increase(); }
    public void decrease() { this.counter.decrease(); }
    public String counterToString() { return this.counter.getCountToString(); }

    public String isOpenToString() { return this.irrigation.getIsOpenToString(); }

    public void reset() {
        this.irrigation.reset();
        this.counter.reset();

        this.irrigationView.setText(this.isOpenToString());
        this.counterView.setText(this.counterToString());
    }

    public void setStateSystem(StateSystem stateSystem) { this.stateSystem = stateSystem; }
}
