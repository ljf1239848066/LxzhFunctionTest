package com.lxzh123.funcdemo.processtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

public class Activity2InOtherProcessActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity2_in_other_process);

        TextView tvProcessInfo=(TextView)findViewById(R.id.tvProcessInfo);
        TestClassWithStaticValue.count++;
        tvProcessInfo.setText(this.getClass().getName()+"\n"+getApplicationInfo().processName+"\n Count="+TestClassWithStaticValue.count);
    }
}
