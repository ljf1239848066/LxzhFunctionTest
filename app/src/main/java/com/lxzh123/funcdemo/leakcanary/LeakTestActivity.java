package com.lxzh123.funcdemo.leakcanary;

import android.app.Activity;
import android.os.Bundle;

import com.lxzh123.funcdemo.R;

public class LeakTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_leak_test);
        LeakTest.getInstance(this);
    }
}
