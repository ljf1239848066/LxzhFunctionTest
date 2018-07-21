package com.lxzh123.weather;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;

public class WeatherActivity extends Activity {

	private TextView weatherInfo;
	private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            weatherInfo.setText(new GetWeather("武汉市","0").toString());
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        weatherInfo=(TextView)findViewById(R.id.weatherInfo);
        new Thread(){

			@Override
			public void run() {
			    handler.sendEmptyMessage(0);

				super.run();
			}
        	
        }.start();
    }
}