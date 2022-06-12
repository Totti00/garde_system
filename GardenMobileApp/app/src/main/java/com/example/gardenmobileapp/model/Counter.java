package com.example.gardenmobileapp.model;

public class Counter {
    private static final int INIT_STEP = 0;
    private static final int STEP = 1;

    private final int max;
    private final int min;

    private int count;

    public Counter(int min, int max) {
        this.min = min;
        this.max = max;
        this.count = INIT_STEP;
    }


    public String getCountToString() { return String.valueOf(this.count); }

    //increase count by 1
    public void increase() {
//        if(this.count == this.max) return;
        this.count += STEP;
    }

    //decrease count by 1
    public void decrease() {
//        if(this.count == this.min) return;
        this.count -= STEP;
    }

}
