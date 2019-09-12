package com.lxzh123.funcdemo.leakcanary;

import android.content.Context;

public class LeakTest {
    private volatile static LeakTest instance;
    private Context context;

    private LeakTest(Context context) {
        this.context = context;
    }

    public static LeakTest getInstance(Context context) {
        if (instance == null) {
            synchronized (LeakTest.class) {
                if (instance == null) {
                    instance = new LeakTest(context);
                }
            }
        }
        return instance;
    }
}
