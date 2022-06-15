package com.example.gardenmobileapp;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gardenmobileapp.mvc.system.IrrigationSystem;
import com.example.gardenmobileapp.mvc.system.LightSystem;
import com.example.gardenmobileapp.mvc.system.StateSystem;
import com.example.gardenmobileapp.mvc.view.CounterView;
import com.example.gardenmobileapp.server.BTServer;
import com.example.gardenmobileapp.utils.ClientConfig;

public class MainActivity extends AppCompatActivity {
    private IrrigationSystem irrigationSystem;
    private LightSystem lightSystem;
    private StateSystem stateSystem;

    private BTServer btServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            super.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), ClientConfig.bluetooth.ENABLE_BT_REQUEST);
        }

        this.irrigationSystem = new IrrigationSystem();
        this.irrigationSystem.setIrrigationView(findViewById(R.id.irrigationBtn));
        final CounterView counterView = new CounterView(findViewById(R.id.incrIrrig), findViewById(R.id.textIrrig), findViewById(R.id.decrIrrig));
        this.irrigationSystem.setCounterView(counterView);

        this.lightSystem = new LightSystem();
        final CounterView led3View = new CounterView(findViewById(R.id.incrLed3), findViewById(R.id.textLed3), findViewById(R.id.decrLed3));
        final CounterView led4View = new CounterView(findViewById(R.id.incrLed4), findViewById(R.id.textLed4), findViewById(R.id.decrLed4));
        this.lightSystem.setLed1View(findViewById(R.id.btnLed1));
        this.lightSystem.setLed2View(findViewById(R.id.btnLed2));
        this.lightSystem.setLed3View(led3View);
        this.lightSystem.setLed4View(led4View);

        this.stateSystem = new StateSystem();
        this.stateSystem.setAllerBtn(findViewById(R.id.allerBtn));
        this.stateSystem.setTextView(findViewById(R.id.textState));
        this.stateSystem.setManualBtn(findViewById(R.id.requireManualBtn));

        this.btServer = new BTServer();
        this.btServer.setIrrigationSystem(this.irrigationSystem);
        this.btServer.setLightSystem(this.lightSystem);
        this.btServer.setStateSystem(this.stateSystem);
        this.btServer.connect();


        this.stateSystem.build();
        this.lightSystem.build();
        this.irrigationSystem.build();

    }


}