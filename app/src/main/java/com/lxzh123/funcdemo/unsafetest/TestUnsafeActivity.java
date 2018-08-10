package com.lxzh123.funcdemo.unsafetest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

public class TestUnsafeActivity extends Activity {

    private TextView tvUnsafeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_test_unsafe);

        tvUnsafeInfo=(TextView)findViewById(R.id.tvUnsafeInfo);
        tvUnsafeInfo.setText(TestUnsafe.Test());


    }
}
