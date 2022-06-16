package com.example.gardenmobileapp.mvc.system;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.gardenmobileapp.command.ArduinoCommands;
import com.example.gardenmobileapp.utils.State.StateGarden;
import com.example.gardenmobileapp.btlib.BluetoothChannel;

public class StateSystem {
    private IrrigationSystem irrigationSystem;
    private LightSystem lightSystem;

    private StateGarden state;

    private Button allerBtn;
    private Button manualBtn;
    private Button automaticBtn;
    private TextView textView;

    private BluetoothChannel btChannel;

    public StateSystem(){
        this.state = StateGarden.AUTOMATIC;
    }

    public void setAllerBtn(Button view) { this.allerBtn = view; }
    public void setManualBtn(Button view) { this.manualBtn = view; }
    public void setAutomaticBtn(Button view) { this.automaticBtn = view; }
    public void setTextView(TextView view) { this.textView = view; }

    public void setIrrigationSystem(IrrigationSystem irrigationSystem) { this.irrigationSystem = irrigationSystem; }
    public void setLightSystem(LightSystem lightSystem) { this.lightSystem = lightSystem; }

    public void setBtChannel(BluetoothChannel btChannel) { this.btChannel = btChannel; }

    public void build() {
        this.allerBtn.setVisibility(android.view.View.INVISIBLE);

        this.allerBtn.setOnClickListener(l -> {
            this.allerBtn.setVisibility(android.view.View.INVISIBLE);
            this.state = StateGarden.AUTOMATIC;
            this.setTextStateInView();
            this.btChannel.sendMessage(ArduinoCommands.STATE_ALARM);
        });
        this.manualBtn.setOnClickListener(l -> {
            this.state = StateGarden.MANUAL;
            this.irrigationSystem.reset();
            this.lightSystem.reset();
            this.setTextStateInView();
            this.btChannel.sendMessage(ArduinoCommands.STATE_MANUAL);
        });
        this.automaticBtn.setOnClickListener(l -> {
            this.state = StateGarden.AUTOMATIC;
            this.setTextStateInView();
            this.btChannel.sendMessage(ArduinoCommands.STATE_AUTOMATIC);
        });

        this.setTextStateInView();
    }

    private void setTextStateInView() { this.textView.setText(this.state.getName()); }


    public void setAlarmState() {
        this.allerBtn.setVisibility(View.VISIBLE);
        this.state = StateGarden.ALARM;
        this.setTextStateInView();
    }
}
