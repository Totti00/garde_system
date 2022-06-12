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
    private Button manualBtn;
    private TextView textState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lightSystem = new LightSystem();
        final CounterView led3View = new CounterView(findViewById(R.id.incrLed3), findViewById(R.id.textLed3), findViewById(R.id.decrLed3));
        final CounterView led4View = new CounterView(findViewById(R.id.incrLed4), findViewById(R.id.textLed4), findViewById(R.id.decrLed4));
        this.lightSystem.setLed1View(findViewById(R.id.btnLed1));
        this.lightSystem.setLed2View(findViewById(R.id.btnLed2));
        this.lightSystem.setLed3View(led3View);
        this.lightSystem.setLed4View(led4View);
        this.lightSystem.build();

        this.irrigationSystem = new IrrigationSystem();
        this.irrigationSystem.setIrrigationView(findViewById(R.id.irrigationBtn));
        final CounterView counterView = new CounterView(findViewById(R.id.incrIrrig), findViewById(R.id.textIrrig), findViewById(R.id.decrIrrig));
        this.irrigationSystem.setCounterView(counterView);

        this.allerBtn = findViewById(R.id.allerBtn);
        this.manualBtn = findViewById(R.id.requireManualBtn);
        this.textState = findViewById(R.id.textState);

        this.build();
    }

    private void build(){
        this.allerBtn.setOnClickListener(l -> this.textState.setText(StateGarden.AUTOMATIC.getName()));
        this.manualBtn.setOnClickListener(l -> this.textState.setText(StateGarden.MANUAL.getName()));
    }
}