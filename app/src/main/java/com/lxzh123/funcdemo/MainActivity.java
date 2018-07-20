package com.lxzh123.funcdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lxzh123.bcreceiver.BCReceiverActivity;
import com.lxzh123.bootstart.BootStartActivity;
import com.lxzh123.filebrowser.FileBrowserActivity;
import com.lxzh123.ftp.FTPActivity;
import com.lxzh123.inputmethod.InputMethodActivity;
import com.lxzh123.location.LocationActivity;
import com.lxzh123.notification.NotificationActivity;
import com.lxzh123.quickmark.QuickMarkActivity;
import com.lxzh123.record.RecordActivity;
import com.lxzh123.record1.SoundRecorderActivity;
import com.lxzh123.savedata.xml.XmlSaveDataActivity;
import com.lxzh123.sendemail.SendMailActivity;
import com.lxzh123.sortalgo.SortActivity;
import com.lxzh123.weather.WeatherActivity;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	//方法修饰符必须为public，private和protected都不行
	public void OnButtonCLick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btnQuickMark:
			intent.setClass(MainActivity.this, QuickMarkActivity.class);
			break;
		case R.id.btnSendMail:
			intent.setClass(MainActivity.this, SendMailActivity.class);
			break;
		case R.id.btnSaveDataByXml:
			intent.setClass(MainActivity.this, XmlSaveDataActivity.class);
			break;
		case R.id.btnNotification:
			intent.setClass(MainActivity.this, NotificationActivity.class);
			break;
		case R.id.btnFileBrowser:
			intent.setClass(MainActivity.this, FileBrowserActivity.class);
			break;
		case R.id.btnRecord:
			intent.setClass(MainActivity.this, RecordActivity.class);
			break;
		case R.id.btnRecord1:
			intent.setClass(MainActivity.this, SoundRecorderActivity.class);
			break;
		case R.id.btnInputMethod:
			intent.setClass(MainActivity.this, InputMethodActivity.class);
			break;
		case R.id.btnLocation:
			intent.setClass(MainActivity.this, LocationActivity.class);
			break;
		case R.id.btnWeather:
			intent.setClass(MainActivity.this, WeatherActivity.class);
			break;	
		case R.id.btnFtp:
			intent.setClass(MainActivity.this, FTPActivity.class);
			break;
		case R.id.btnBCReceiver:
			intent.setClass(MainActivity.this, BCReceiverActivity.class);
			break;
		case R.id.btnBootStart:
			intent.setClass(MainActivity.this, BootStartActivity.class);
			break;
        case R.id.btnSort:
            intent.setClass(MainActivity.this, SortActivity.class);
            break;
//		case R.id.btnCycleWheelView:
//			intent.setClass(MainActivity.this, CycleWheelViewActivity.class);
//			break;
//		case R.id.btnShapeView:
//			intent.setClass(MainActivity.this, ShapeViewActivity.class);
//			break;
//		case R.id.btnSeekbar:
//			intent.setClass(MainActivity.this, SeekbarActivity.class);
//			break;
//		case R.id.btnRadioButton:
//			intent.setClass(MainActivity.this, RadioButtonActivity.class);
//			break;
//		case R.id.btnViewPager:
//			intent.setClass(MainActivity.this, ViewPagerActivity.class);
//			break;
		default:
			return;
		}
		startActivity(intent);
	}
	
}
