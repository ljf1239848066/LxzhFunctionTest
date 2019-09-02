package com.lxzh123.funcdemo.bcreceiver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.lxzh123.funcdemo.R;
import com.lxzh123.funcdemo.bcreceiver.LxzhService.MyBinder;

public class BCReceiverActivity extends Activity {
	/** Called when the activity is first created. */
	private static final String RECEIVERTAG = "unRegisterBorder";
	private static final String CONNECTSERVICE = "ConnectService";
	private static final String DISCONNECTSERVICE = "DisConnectService";
	private static final String UNBINDSERVICE = "unbindService";
	private MyBorderReceiver receiver;
	boolean mBound = false;
	private LxzhService myService;
	private int mymsg;
	private TextView view;

	public class MyBorderReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			mymsg = intent.getIntExtra("msg", 2);
			view.setText("广播从服务端接收过来的消息是：" + mymsg);
			Message message = new Message();
			Bundle data = new Bundle();
			data.putInt("progress", mymsg);
			message.setData(data);
			message.what = 1;
			handler.sendMessage(message);
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				int progress = msg.getData().getInt("progress");
				// Toast.makeText(MainActivity.this,"++++"+progress,Toast.LENGTH_LONG).show();
				// 获取通知管理器，用于发送通知
				NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				// 实例化Notification对象，用于添加消息内容
				Notification notice = new Notification(R.drawable.icon,
						"这是一个通知消息", System.currentTimeMillis());
				// //添加扩展的消息标题和内容
				// String noticeTitle="通知的标题";
				// String noticeContent="通知扩展的内容";
				// //通过消息要打开的应用程序
				// Intent intent=new Intent("com.fit.demo.notice");
				// //等待用户来处理消息
				// PendingIntent
				// pending=PendingIntent.getActivity(MainActivity.this,0,
				// intent, 0);
				// //把所有的消息内容放到Notification里
				// notice.setLatestEventInfo(getApplicationContext(),
				// noticeTitle, noticeContent, pending);
				// //打开应用程序后消息图标消失
				// notice.flags|=Notification.FLAG_AUTO_CANCEL;
				// //通过消息管理器发送消息
				// //manager.notify(1,notice);
				//

				// //用于不断发送消息而不会覆盖以前的消息
				// manager.notify(1,notice);
				RemoteViews view = new RemoteViews(getPackageName(),
						R.layout.layout_bcreceiver_notice);
				view.setProgressBar(R.id.progress, 100, progress, false);
				view.setTextColor(R.id.myview, Color.RED);
				view.setTextViewText(R.id.myview, "进度：    " + progress + "%");
				notice.contentView = view;
				Intent intent1 = new Intent("notice");
				PendingIntent pending = PendingIntent.getActivity(
						BCReceiverActivity.this, 0, intent1, 0);
				notice.contentIntent = pending;

				// 打开应用程序后消息图标消失
				notice.flags |= Notification.FLAG_AUTO_CANCEL;
				// 通过消息管理器发送消息
				manager.notify(1, notice);
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bcreceiver_main);

		view = (TextView) findViewById(R.id.view);
		receiver = new MyBorderReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.lxzh123.funcdemo.bcreceiver");
		this.registerReceiver(receiver, filter);

		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intent = new Intent(BCReceiverActivity.this, LxzhService.class);
		this.bindService(intent, conn, Context.BIND_AUTO_CREATE);
		this.startService(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
		Log.i(RECEIVERTAG, "the receiver have been unRegistered!");
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(conn);
			Log.i(UNBINDSERVICE, "the service have been unbinded!");
			mBound = false;
		}

	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i(DISCONNECTSERVICE, "service have disConnect!");
			mBound = false;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			Log.i(CONNECTSERVICE, "service have connected!");
			MyBinder binder = (MyBinder) service;
			myService = binder.getService();
			mBound = true;
		}
	};
}
