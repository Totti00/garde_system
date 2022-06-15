package com.example.gardenmobileapp.mvc.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterView {
    private final Button incrementBtn;
    private final Button decrementBtn;
    private final TextView text;

    public CounterView(final Button incrementBtn, final TextView text, Button decrementBtn) {
        this.incrementBtn = incrementBtn;
        this.decrementBtn = decrementBtn;
        this.text = text;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setClickIncrementBtn(final View.OnClickListener onClick) {
        this.incrementBtn.setOnClickListener(onClick);
    }

    public void setClickDecrementBtn(final View.OnClickListener onclick) {
        this.decrementBtn.setOnClickListener(onclick);
    }


}
