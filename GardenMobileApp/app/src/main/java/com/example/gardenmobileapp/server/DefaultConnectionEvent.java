package com.example.gardenmobileapp.server;

import android.util.Log;

import com.example.gardenmobileapp.btlib.BluetoothChannel;
import com.example.gardenmobileapp.btlib.ConnectionTask;
import com.example.gardenmobileapp.btlib.RealBluetoothChannel;
import com.example.gardenmobileapp.mvc.system.IrrigationSystem;
import com.example.gardenmobileapp.mvc.system.LightSystem;
import com.example.gardenmobileapp.mvc.system.StateSystem;

public class DefaultConnectionEvent implements ConnectionTask.EventListener {
    private IrrigationSystem irrigationSystem;
    private LightSystem lightSystem;
    private StateSystem stateSystem;

    private String receivedRemoteDeviceName;
    private String sendRemoteDeviceName;

    public void setIrrigationSystem(IrrigationSystem irrigationSystem) { this.irrigationSystem = irrigationSystem; }
    public void setLightSystem(LightSystem lightSystem) { this.lightSystem = lightSystem; }
    public void setStateSystem(StateSystem stateSystem) { this.stateSystem = stateSystem; }

    @Override
    public void onConnectionActive(BluetoothChannel channel) {
        this.irrigationSystem.setBtChannel(channel);
        this.lightSystem.setBtChannel(channel);
        this.stateSystem.setBtChannel(channel);

        channel.registerListener(new RealBluetoothChannel.Listener() {
            @Override
            public void onMessageReceived(String receivedMessage) {
                DefaultConnectionEvent.this.receivedRemoteDeviceName = channel.getRemoteDeviceName();
            }

            @Override
            public void onMessageSent(String sentMessage) {
                DefaultConnectionEvent.this.sendRemoteDeviceName = channel.getRemoteDeviceName();
            }
        });
    }

    @Override
    public void onConnectionCanceled() { }

}
