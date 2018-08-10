package com.lxzh123.funcdemo.xml;

import com.lxzh123.funcdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class XmlSaveDataActivity extends Activity {

	private Button btnSave;
	private Button btnNext;
	private String saveText;
	private int count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_savedata_xml_main);
        btnSave=(Button)findViewById(R.id.btnsave);
        btnNext=(Button)findViewById(R.id.btnnext);
//        SharedPreferences setting=getPreferences(XmlSaveDataActivity.MODE_PRIVATE);
        //saveText=setting.getString("saveinfo","hello");//初始添加数据，其中saveinfo为字段名
    }
    
    public void onClick(View v)
    {
    	count++;
    	SharedPreferences preferences=this.getSharedPreferences("XmlSaveData.xml",XmlSaveDataActivity.MODE_PRIVATE);
    	SharedPreferences.Editor editor=preferences.edit();
    	editor.putString("saveinfo",count+"");
    	editor.commit();//存数据
    	
    	btnSave.setText(preferences.getString("saveinfo",""));//取数据
    }
    
    public void onNextClick(View v)
    {
    	Intent intent=new Intent(XmlSaveDataActivity.this,XmlSaveDataNextActivity.class);
    	startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
