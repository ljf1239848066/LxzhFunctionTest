package com.lxzh123.funcdemo.bcreceiver;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class LxzhService extends Service {

	private final IBinder mBinder = new MyBinder();
	private int m = 1;
	Timer timer = new Timer();

	@Override
	public IBinder onBind(Intent intent) {
		timer.schedule(task, new Date(), 500);
		return mBinder;
	}

	class MyBinder extends Binder {
		public LxzhService getService() {
			return LxzhService.this;
		}
	}

	private void sendMsg() {
		Intent intent = new Intent("com.lxzh123.funcdemo.bcreceiver");
		// int m=(int)(Math.random()*10);
		if (m < 100) {
			m++;
		}else{
			m=0;
		}
		intent.putExtra("msg", m);
		sendBroadcast(intent);
	}

	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			sendMsg();
		}
	};
}