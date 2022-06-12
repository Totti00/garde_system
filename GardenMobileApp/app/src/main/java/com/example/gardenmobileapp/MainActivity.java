package com.example.gardenmobileapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gardenmobileapp.State.StateGarden;
import com.example.gardenmobileapp.system.IrrigationSystem;
import com.example.gardenmobileapp.system.LightSystem;
import com.example.gardenmobileapp.view.CounterView;

public class MainActivity extends AppCompatActivity {
    private LightSystem lightSystem;
    private IrrigationSystem irrigationSystem;

    private Button allerBtn;
    private Button btnLed1;
    private Button btnLed2;
    private CounterView led3View;
    private CounterView led4View;

    private Button irrigationBtn;
    private CounterView irrigationView;

    private Button manualBtn;
    private TextView textState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lightSystem = new LightSystem();
        this.irrigationSystem = new IrrigationSystem();

        this.allerBtn = findViewById(R.id.allerBtn);

        this.btnLed1 = findViewById(R.id.btnLed1);
        this.btnLed2 = findViewById(R.id.btnLed2);
        this.led3View = new CounterView(findViewById(R.id.incrLed3), findViewById(R.id.textLed3), findViewById(R.id.decrLed3));
        this.led4View = new CounterView(findViewById(R.id.incrLed4), findViewById(R.id.textLed4), findViewById(R.id.decrLed4));
        this.btnLed1.setText(this.lightSystem.led1ToString());
        this.btnLed2.setText(this.lightSystem.led2ToString());

        this.irrigationBtn = findViewById(R.id.irrigationBtn);
        this.irrigationView = new CounterView(findViewById(R.id.incrIrrig), findViewById(R.id.textIrrig), findViewById(R.id.decrIrrig));
        this.irrigationBtn.setText(this.irrigationSystem.isOpenToString());

        this.manualBtn = findViewById(R.id.requireManualBtn);
        this.textState = findViewById(R.id.textState);

        this.build();
    }

    private void build(){
        this.allerBtn.setOnClickListener(l -> this.textState.setText(StateGarden.AUTOMATIC.getName()));

        this.btnLed1.setOnClickListener(l -> {
            this.lightSystem.toggleLed1();
            this.btnLed1.setText(this.lightSystem.led1ToString());
        });
        this.btnLed2.setOnClickListener(l -> {
            this.lightSystem.toggleLed2();
            this.btnLed2.setText(this.lightSystem.led2ToString());
        });
        this.led3View.setClickIncrementBtn(l -> {
            this.lightSystem.increaseLed3();
            this.led3View.setText(this.lightSystem.led3ToString());
        });
        this.led3View.setClickDecrementBtn(l -> {
            this.lightSystem.decreaseLed3();
            this.led3View.setText(this.lightSystem.led3ToString());
        });
        this.led4View.setClickIncrementBtn(l -> {
            this.lightSystem.increaseLed4();
            this.led4View.setText(this.lightSystem.led4ToString());
        });
        this.led4View.setClickDecrementBtn(l -> {
            this.lightSystem.decreaseLed4();
            this.led4View.setText(this.lightSystem.led4ToString());
        });

        this.irrigationBtn.setOnClickListener(l -> {
            this.irrigationSystem.toggle();
            this.irrigationBtn.setText(this.irrigationSystem.isOpenToString());
        });

        this.irrigationView.setClickIncrementBtn(l -> {
            this.irrigationSystem.increase();
            this.irrigationView.setText(this.irrigationSystem.counterToString());
        });
        this.irrigationView.setClickDecrementBtn(l -> {
            this.irrigationSystem.decrease();
            this.irrigationView.setText(this.irrigationSystem.counterToString());
        });

        this.manualBtn.setOnClickListener(l -> {
            this.textState.setText(StateGarden.MANUAL.getName());
        });
    }
}