package com.lxzh123.funcdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity{

    private final static String ACTIVITYTAG="Activity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		final String[] funcStr=new String[]{
//		        "QuickMark",
//                "SendMail",
//                "XmlSaveData",
//                "Notification",
//                "FileBrowser",
//                "Record",
//                "SoundRecorder",
//                "InputMethod",
//                "Location",
//                "Weather",
//                "FTP",
//                "BCReceiver",
//                "BootStart",
//                "Sort",
//                "Find",
//                "Activity1InMainProcess",
//                "SocketClient1",
//                "SocketClient2",
//                "TestUnsafe",
//                "TestRxJava"};

        final String[] activityNames=getAllActivity();
        int len=activityNames.length;
        final String[] showNames=new String[len];
        for(int i=0;i<len;i++){
            String name=activityNames[i];
            showNames[i]=name.substring(name.lastIndexOf(".")+1).replace(ACTIVITYTAG,"");
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.main,showNames);
        this.setListAdapter(adapter);
        this.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                String className=activityNames[position];
                try{
                    Class clazz=Class.forName(className);
                    intent.setClass(MainActivity.this,clazz);
                }catch (Exception ex){
                    ex.printStackTrace();
                    return;
                }

//                switch (position) {
//                    case 0:
//                        intent.setClass(MainActivity.this, QuickMarkActivity.class);
//                        break;
//                    case 1:
//                        intent.setClass(MainActivity.this, SendMailActivity.class);
//                        break;
//                    case 2:
//                        intent.setClass(MainActivity.this, XmlSaveDataActivity.class);
//                        break;
//                    case 3:
//                        intent.setClass(MainActivity.this, NotificationActivity.class);
//                        break;
//                    case 4:
//                        intent.setClass(MainActivity.this, FileBrowserActivity.class);
//                        break;
//                    case 5:
//                        intent.setClass(MainActivity.this, RecordActivity.class);
//                        break;
//                    case 6:
//                        intent.setClass(MainActivity.this, SoundRecorderActivity.class);
//                        break;
//                    case 7:
//                        intent.setClass(MainActivity.this, InputMethodActivity.class);
//                        break;
//                    case 8:
//                        intent.setClass(MainActivity.this, LocationActivity.class);
//                        break;
//                    case 9:
//                        intent.setClass(MainActivity.this, WeatherActivity.class);
//                        break;
//                    case 10:
//                        intent.setClass(MainActivity.this, FTPActivity.class);
//                        break;
//                    case 11:
//                        intent.setClass(MainActivity.this, BCReceiverActivity.class);
//                        break;
//                    case 12:
//                        intent.setClass(MainActivity.this, BootStartActivity.class);
//                        break;
//                    case 13:
//                        intent.setClass(MainActivity.this, SortActivity.class);
//                        break;
//                    case 14:
//                        intent.setClass(MainActivity.this, FindActivity.class);
//                        break;
//                    case 15:
//                        intent.setClass(MainActivity.this, Activity1InMainProcessActivity.class);
//                        break;
//                    case 16:
//                        intent.setClass(MainActivity.this, SocketClient1Activity.class);
//                        break;
//                    case 17:
//                        intent.setClass(MainActivity.this, SocketClient2Activity.class);
//                        break;
//                    case 18:
//                        intent.setClass(MainActivity.this, TestUnsafeActivity.class);
//                        break;
//                    case 19:
//                        intent.setClass(MainActivity.this, TestRxJavaActivity.class);
//                        break;
////		case R.id.btnCycleWheelView:
////			intent.setClass(MainActivity.this, CycleWheelViewActivity.class);
////			break;
////		case R.id.btnShapeView:
////			intent.setClass(MainActivity.this, ShapeViewActivity.class);
////			break;
////		case R.id.btnSeekbar:
////			intent.setClass(MainActivity.this, SeekbarActivity.class);
////			break;
////		case R.id.btnRadioButton:
////			intent.setClass(MainActivity.this, RadioButtonActivity.class);
////			break;
////		case R.id.btnViewPager:
////			intent.setClass(MainActivity.this, ViewPagerActivity.class);
////			break;
//                    default:
//                        return;
//                }
                startActivity(intent);
            }
        });
	}

	private String[] getAllActivity(){
	    List<String> classNameList=new ArrayList<String>();
	    try{
            PackageInfo packageInfo=this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            for(ActivityInfo ai:packageInfo.activities){
                String name=ai.name;
                if (name.endsWith(ACTIVITYTAG)&&!name.contains("Main")) {
                        classNameList.add(name);
                }
            }
//            DexFile df=new DexFile(this.getPackageCodePath());
//            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
//            while (enumeration.hasMoreElements()) {//遍历
//                String className = (String) enumeration.nextElement();
//
//                if (className.endsWith(ACTIVITYTAG)&&!className.contains("Main")) {//在当前所有可执行的类里面查找包含有该包名的所有类
//                    classNameList.add(className);
//                }
//            }
        }catch (Exception ex){

        }
        String[] rst=new String[classNameList.size()];
        return classNameList.toArray(rst);
    }
}
