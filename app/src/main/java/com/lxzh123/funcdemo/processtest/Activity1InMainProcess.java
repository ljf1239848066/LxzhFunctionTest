package com.lxzh123.funcdemo.processtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

public class Activity1InMainProcess extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity1_in_main_process);

        Button btnStart=(Button)findViewById(R.id.btnStart);
        TextView tvProcessInfo=(TextView)findViewById(R.id.tvProcessInfo);
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Activity1InMainProcess.this,Activity2InOtherProcess.class);
                startActivity(intent);
            }
        });
        TestClassWithStaticValue.count++;
        tvProcessInfo.setText(this.getClass().getName()+"\n"+getApplicationInfo().processName+"\n Count="+TestClassWithStaticValue.count);
    }
}
