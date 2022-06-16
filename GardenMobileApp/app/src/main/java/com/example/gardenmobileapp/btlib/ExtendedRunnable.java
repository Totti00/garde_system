package com.example.gardenmobileapp.btlib;

public interface ExtendedRunnable extends Runnable {
    void write(byte[] bytes);
    void cancel();
}
