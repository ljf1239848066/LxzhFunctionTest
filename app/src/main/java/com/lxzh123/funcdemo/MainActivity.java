package com.lxzh123.funcdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.lxzh123.funcdemo.bcreceiver.BCReceiverActivity;
import com.lxzh123.funcdemo.bootstart.BootStartActivity;
import com.lxzh123.funcdemo.filebrowser.FileBrowserActivity;
import com.lxzh123.funcdemo.findalgo.FindActivity;
import com.lxzh123.funcdemo.ftp.FTPActivity;
import com.lxzh123.funcdemo.inputmethod.InputMethodActivity;
import com.lxzh123.funcdemo.location.LocationActivity;
import com.lxzh123.funcdemo.notification.NotificationActivity;
import com.lxzh123.funcdemo.quickmark.QuickMarkActivity;
import com.lxzh123.funcdemo.record.RecordActivity;
import com.lxzh123.funcdemo.record1.SoundRecorderActivity;
import com.lxzh123.funcdemo.xml.XmlSaveDataActivity;
import com.lxzh123.funcdemo.sendemail.SendMailActivity;
import com.lxzh123.funcdemo.sortalgo.SortActivity;
import com.lxzh123.funcdemo.weather.WeatherActivity;

public class MainActivity extends ListActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String[] funcStr=new String[]{
		        "QuickMark",
                "SendMail",
                "SaveDataByXml",
                "Notification",
                "FileBrowser",
                "Record",
                "Record1",
                "InputMethod",
                "Location",
                "Weather",
                "FTP",
                "BCReceiver",
                "BootStart",
                "Sort Algo",
                "Find Algo"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.main,funcStr);
        this.setListAdapter(adapter);
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                switch (position) {
                    case 0:
                        intent.setClass(MainActivity.this, QuickMarkActivity.class);
                        break;
                    case 1:
                        intent.setClass(MainActivity.this, SendMailActivity.class);
                        break;
                    case 2:
                        intent.setClass(MainActivity.this, XmlSaveDataActivity.class);
                        break;
                    case 3:
                        intent.setClass(MainActivity.this, NotificationActivity.class);
                        break;
                    case 4:
                        intent.setClass(MainActivity.this, FileBrowserActivity.class);
                        break;
                    case 5:
                        intent.setClass(MainActivity.this, RecordActivity.class);
                        break;
                    case 6:
                        intent.setClass(MainActivity.this, SoundRecorderActivity.class);
                        break;
                    case 7:
                        intent.setClass(MainActivity.this, InputMethodActivity.class);
                        break;
                    case 8:
                        intent.setClass(MainActivity.this, LocationActivity.class);
                        break;
                    case 9:
                        intent.setClass(MainActivity.this, WeatherActivity.class);
                        break;
                    case 10:
                        intent.setClass(MainActivity.this, FTPActivity.class);
                        break;
                    case 11:
                        intent.setClass(MainActivity.this, BCReceiverActivity.class);
                        break;
                    case 12:
                        intent.setClass(MainActivity.this, BootStartActivity.class);
                        break;
                    case 13:
                        intent.setClass(MainActivity.this, SortActivity.class);
                        break;
                    case 14:
                        intent.setClass(MainActivity.this, FindActivity.class);
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
        });
	}
}
