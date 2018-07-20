package com.lxzh123.weather;

import com.lxzh123.funcdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class WeatherActivity extends Activity {

	private TextView weatherInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weather);
        weatherInfo=(TextView)findViewById(R.id.weatherInfo);
        new Thread(){

			@Override
			public void run() {
				weatherInfo.setText(new GetWeather("Îäºº","0").toString());
				super.run();
			}
        	
        }.start();
    }
}