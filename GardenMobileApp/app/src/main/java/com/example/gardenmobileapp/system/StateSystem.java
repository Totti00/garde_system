package com.example.gardenmobileapp.system;

import android.widget.Button;
import android.widget.TextView;

import com.example.gardenmobileapp.State.StateGarden;

public class StateSystem {
    private StateGarden state;

    private Button allerBtn;
    private Button manualBtn;
    private TextView textView;

    public StateSystem(){
        this.state = StateGarden.AUTOMATIC;
    }

    public void setAllerBtn(Button view) { this.allerBtn = view; }
    public void setManualBtn(Button view) { this.manualBtn = view; }
    public void setTextView(TextView view) { this.textView = view; }

    public void build() {
        this.allerBtn.setOnClickListener(l -> {
            this.state = StateGarden.AUTOMATIC;
            this.setTextStateInView();
        });
        this.manualBtn.setOnClickListener(l -> {
            this.state = StateGarden.MANUAL;
            this.setTextStateInView();
        });

        this.setTextStateInView();
    }

    public void setTextStateInView() { this.textView.setText(this.state.getName()); }
}
