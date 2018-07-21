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
            weatherInfo.setText(msg.obj.toString());
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
			    GetWeather gw=new GetWeather("武汉","0");
			    String weatherInfo=gw.toString();
			    Message message=Message.obtain();
			    message.obj=weatherInfo;
			    message.what=0;
			    handler.sendMessage(message);

				super.run();
			}
        	
        }.start();
    }
}