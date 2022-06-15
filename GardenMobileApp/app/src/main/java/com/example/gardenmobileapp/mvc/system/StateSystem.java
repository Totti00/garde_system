package com.example.gardenmobileapp.mvc.system;

import android.widget.Button;
import android.widget.TextView;

import com.example.gardenmobileapp.utils.State.StateGarden;
import com.example.gardenmobileapp.btlib.BluetoothChannel;

public class StateSystem {
    private StateGarden state;

    private Button allerBtn;
    private Button manualBtn;
    private TextView textView;

    private BluetoothChannel btChannel;

    public StateSystem(){
        this.state = StateGarden.AUTOMATIC;
    }

    public void setAllerBtn(Button view) { this.allerBtn = view; }
    public void setManualBtn(Button view) { this.manualBtn = view; }
    public void setTextView(TextView view) { this.textView = view; }

    public void setBtChannel(BluetoothChannel btChannel) { this.btChannel = btChannel; }

    public void build() {
        this.allerBtn.setOnClickListener(l -> {
            this.state = StateGarden.AUTOMATIC;
            this.setTextStateInView();
            this.btChannel.sendMessage("a");
        });
        this.manualBtn.setOnClickListener(l -> {
            this.state = StateGarden.MANUAL;
            this.setTextStateInView();
            this.btChannel.sendMessage("M");
        });

        this.setTextStateInView();
    }

    public void setTextStateInView() { this.textView.setText(this.state.getName()); }
}
