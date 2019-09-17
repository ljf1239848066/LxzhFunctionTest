package com.lxzh123.funcdemo.jni;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

public class JniActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_jni);
        TextView tv = findViewById(R.id.txt_from_jni);
        tv.setText(Helper.stringFromJNI());
        tv.setText(Helper.stringFromJNI1(2));
        tv.setText(Helper.stringFromJNI2(2));
    }
}
