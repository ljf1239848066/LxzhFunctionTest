package com.lxzh123.funcdemo.location;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.lxzh123.funcdemo.R;
import com.lxzh123.funcdemo.util.LocationPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class LocationActivity extends Activity implements LocationListener {
    private final static String TAG = "LocationActivity";
    private TextView locationView;
    private TextView locationInfoView;
    private static final int LOCATION_CODE = 1;
    private boolean requestPerFinished = true;
    private boolean hasRegisterLocationListerner = false;
    private LocationManager locationManager;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        LocationPermission lp = hasLocationPermissions(this);
        switch (lp) {
            case LocationProviderEnabledAndPermit:
                registerLocationListener();
                EventBus.getDefault().post(handler);
                break;
            case LocationProviderEnabledButNotPermit:
                requestPerFinished = false;
                EventBus.getDefault().post(handler);
                requestLocationPermissions(this, lp);
                break;
            case LocationProviderDisabled:

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEvent(Object obj) {
        while (!requestPerFinished) {
            SystemClock.sleep(100);
        }
        getLocation();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                locationView.setText(msg.obj.toString());
            } else if (msg.what == 1) {
                locationInfoView.setText(msg.obj.toString());
            } else if (msg.what == 2) {
                Toast.makeText(getBaseContext(), msg.obj.toString(), Toast.LENGTH_SHORT)
                        .show();
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 判断是否享有定位权限
     *
     * @param context
     * @return 1:已开通定位功能且有定位权限 0:已开通定位功能但应用无定位权限 -1:未开通定位功能
     */
    private LocationPermission hasLocationPermissions(Context context) {
        locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        boolean ok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (ok) {//开了定位服务
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return LocationPermission.LocationProviderEnabledAndPermit;
            } else {
                return LocationPermission.LocationProviderEnabledButNotPermit;
            }
        }
        return LocationPermission.LocationProviderDisabled;
    }

    private void requestLocationPermissions(Context context, LocationPermission lp) {
        if (lp == LocationPermission.LocationProviderEnabledButNotPermit) {//开了定位服务
            Log.e("BRG", "没有权限");
            // 没有权限，申请权限。
            ActivityCompat.requestPermissions(LocationActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
//                        Toast.makeText(getActivity(), "没有权限", Toast.LENGTH_SHORT).show();
        } else if (lp == LocationPermission.LocationProviderDisabled) {
            Log.e("BRG", "系统检测到未开启GPS定位服务");
            Toast.makeText(context, "系统检测到未开启GPS定位服务", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 1315);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    Log.d(TAG, "onRequestPermissionsResult PERMISSION_GRANTED");
                    registerLocationListener();
                } else {
                    // 权限被用户拒绝了。
                    Toast.makeText(LocationActivity.this, "定位权限被禁止，相关地图功能无法使用！", Toast.LENGTH_LONG).show();
                }
                requestPerFinished = true;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void registerLocationListener() {
        if (hasRegisterLocationListerner) {
            return;
        }
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        String provider = "gps";
        if (providers.contains("gps")) {
            provider = "gps";
        } else if (providers.contains("passive")) {
            provider = "passive";
        } else if (providers.contains("network")) {
            provider = "network";
        }
        locationManager.requestLocationUpdates(provider, 1000, 2f, this);
        hasRegisterLocationListerner = true;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged location=" + location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 返回所有已知的位置提供者的名称列表，包括未获准访问或调用活动目前已停用的。
        List<String> lp = locationManager.getAllProviders();
        for (String item : lp) {
            Log.i(TAG, "可用位置服务：" + item);
        }

        Criteria criteria = new Criteria();
        criteria.setCostAllowed(false);
        // 设置位置服务免费
        criteria.setAccuracy(Criteria.ACCURACY_COARSE); // 设置水平位置精度
        // getBestProvider 只有允许访问调用活动的位置供应商将被返回
        String providerName = locationManager.getBestProvider(criteria, true);
        List<String> providers = locationManager.getProviders(true);
        Log.i(TAG, "------位置服务：" + providerName);
        Message message = Message.obtain();
        if (providers.size() > 0) {
            Location location = null;
            for (String provider : providers) {
                location = locationManager.getLastKnownLocation(provider);
                if (location != null) {
                    break;
                }
            }
            if (location == null) {
                return;
            }
            Log.i(TAG, "-------" + location);
            // 获取纬度信息
            double latitude = location.getLatitude();
            // 获取经度信息
            double longitude = location.getLongitude();
            String locationInfo = "定位方式： " + providerName + "\n维度：" + latitude + "\n经度：" + longitude;
            message.what = 0;
            message.obj = locationInfo;

            getLocationInfo(location);
        } else {
            message.what = 2;
            message.obj = "1.请检查网络连接 \n2.请打开我的位置";
        }
        handler.sendMessage(message);
    }

    private void getLocationInfo(Location location) {
        Log.d("getLocation", "1");
        /**
         * 定位服务:http://jwd.funnyapi.com/#/index
         * 其他数据源:https://gadm.org
         */
        String url = "http://116.196.105.215:1234/gis?auth_user=freevip&latitude=$Latitude&longitude=$Longitude";
        String response = new WebAccessTools(this).getWebContent(url.replace(
                "$Latitude", location.getLatitude() + "").replace("$Longitude",
                location.getLongitude() + ""));
        Message message = Message.obtain();
        if (response == null) {
            message.what = 2;
            message.obj = "获取地址失败";
        } else {
//            String locationInfo=response.substring(response.indexOf("formatted_address") + 22,
//                    response.indexOf("geometry") - 13);
            Log.i(TAG, "response:" + response);
            message.what = 1;
            message.obj = response;
        }
        handler.sendMessage(message);
    }
//	private void getLocation(Location location) {
//		Log.d("getLocation", "1");
//		String url = "http://maps.google.com/maps/api/geocode/json?latlng=Latitude,Longitude&sensor=true&language=zh-CN";
//		String response = new WebAccessTools(this).getWebContent(url.replace(
//				"Latitude", location.getLatitude() + "").replace("Longitude",
//				location.getLongitude() + ""));
//        Message message=Message.obtain();
//		if(response==null){
//            message.what=2;
//            message.obj="获取地址失败";
//		}else{
//            String locationInfo=response.substring(response.indexOf("formatted_address") + 22,
//                    response.indexOf("geometry") - 13);
//            Log.i(TAG,"response:"+response);
//            message.what=1;
//            message.obj=locationInfo;
//        }
//        handler.sendMessage(message);
//	}


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hasRegisterLocationListerner) {
            locationManager.removeUpdates(this);
        }
    }
}
