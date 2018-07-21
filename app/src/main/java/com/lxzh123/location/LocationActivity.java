package com.lxzh123.location;

import java.util.List;

import com.lxzh123.funcdemo.R;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends Activity {

	private TextView locationView;
	private TextView locationInfoView;

	@TargetApi(9)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_location);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
				.penaltyLog() // 打印logcat
				.penaltyDeath().build());
		
		locationView = (TextView) findViewById(R.id.location);
		locationInfoView = (TextView) findViewById(R.id.locationinfo);
		
		new Thread(){

			@Override
			public void setContextClassLoader(ClassLoader cl) {
				getLocation();
				super.setContextClassLoader(cl);
			}
		}.start();
	}

	private void getLocation(){
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
		List<String> lp = lm.getAllProviders();
		for (String item : lp) {
			Log.i("8023", "可用位置服务：" + item);
		}

		Criteria criteria = new Criteria();
		criteria.setCostAllowed(false);
		// 设置位置服务免费
		criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
		// getBestProvider 只有允许访问调用活动的位置供应商将被返回
		String providerName = lm.getBestProvider(criteria, true);
		Log.i("8023", "------位置服务：" + providerName);
		if (providerName != null) {
			Location location = lm.getLastKnownLocation(providerName);
			Log.i("8023", "-------" + location);
			// 获取纬度信息
			double latitude = location.getLatitude();
			// 获取经度信息
			double longitude = location.getLongitude();
			locationView.setText("定位方式： " + providerName + "\n维度：" + latitude
					+ "\n经度：" + longitude);
			getLocation(location);
		} else {
			Toast.makeText(this, "1.请检查网络连接 \n2.请打开我的位置", Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	private void getLocation(Location location) {
		Log.d("getLocation", "1");
		String url = "http://maps.google.com/maps/api/geocode/json?latlng=Latitude,Longitude&sensor=true&language=zh-CN";
		String response = new WebAccessTools(this).getWebContent(url.replace(
				"Latitude", location.getLatitude() + "").replace("Longitude",
				location.getLongitude() + ""));
		Log.d("getLocation", response);
		if(response==null){
			Toast.makeText(getBaseContext(), "获取地址失败",Toast.LENGTH_SHORT).show();
			return;
		}
		locationInfoView.setText(response.substring(
				response.indexOf("formatted_address") + 22,
				response.indexOf("geometry") - 13));
	}
}
