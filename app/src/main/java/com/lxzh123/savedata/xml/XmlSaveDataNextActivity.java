package com.lxzh123.savedata.xml;

import com.lxzh123.funcdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class XmlSaveDataNextActivity extends Activity {

	private int count=0;
	private Button btnGet;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_savedata_xml_next);
        btnGet=(Button)findViewById(R.id.btnget);
    }

    public void onClick(View v)
    {
    	count++;
    	SharedPreferences preferences=this.getSharedPreferences("XmlSaveData.xml",XmlSaveDataActivity.MODE_PRIVATE);
//    	SharedPreferences.Editor editor=preferences.edit();
//    	editor.putString("saveinfo",count+"");
//    	editor.commit();//存数据
    	
    	btnGet.setText(preferences.getString("saveinfo",""));//取数据
    }
}
