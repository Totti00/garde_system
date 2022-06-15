package com.example.gardenmobileapp.server;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;

import com.example.gardenmobileapp.btlib.BluetoothUtils;
import com.example.gardenmobileapp.btlib.ConnectToBluetoothServerTask;
import com.example.gardenmobileapp.btlib.exceptions.BluetoothDeviceNotFound;
import com.example.gardenmobileapp.mvc.system.IrrigationSystem;
import com.example.gardenmobileapp.mvc.system.LightSystem;
import com.example.gardenmobileapp.mvc.system.StateSystem;
import com.example.gardenmobileapp.utils.ClientConfig;

import java.util.UUID;

public class BTServer {
    private ConnectToBluetoothServerTask connectToBluetoothServerTask;

    private final DefaultConnectionEvent eventListener;
    private BluetoothDevice serverDevice;
    private final UUID uuid;

    @SuppressLint("MissingPermission")
    public BTServer() {
        try {
            this.serverDevice = BluetoothUtils.getPairedDeviceByName(ClientConfig.bluetooth.BT_DEVICE_ACTING_AS_SERVER_NAME);
        } catch (BluetoothDeviceNotFound e) {
            e.printStackTrace();
        }
        this.uuid = BluetoothUtils.getEmbeddedDeviceDefaultUuid();
        this.eventListener = new DefaultConnectionEvent();
    }

    public void setIrrigationSystem(IrrigationSystem irrigationSystem) { this.eventListener.setIrrigationSystem(irrigationSystem); }
    public void setLightSystem(LightSystem lightSystem) { this.eventListener.setLightSystem(lightSystem); }
    public void setStateSystem(StateSystem stateSystem) { this.eventListener.setStateSystem(stateSystem); }



    public void connect(){
        this.connectToBluetoothServerTask = new ConnectToBluetoothServerTask(this.serverDevice, this.uuid, this.eventListener);
        this.connectToBluetoothServerTask.execute();
    }

}
