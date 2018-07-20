package com.lxzh123.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lxzh123.funcdemo.MainActivity;
import com.lxzh123.funcdemo.R;

public class NotificationActivity extends Activity {

	Button send;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_notification);
        send=(Button)findViewById(R.id.send);
        send.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent in=new Intent(NotificationActivity.this,MainActivity.class);
				PendingIntent pi=PendingIntent.getActivity(NotificationActivity.this, 0, in, 0);
				Notification.Builder builder=new Notification.Builder(NotificationActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker(getResources().getString(R.string.notification));
				builder.setDefaults(Notification.DEFAULT_SOUND);
				builder.setContentTitle("示例");
				builder.setContentText("点击查看");
				builder.setContentIntent(pi);
				Notification notification=builder.build();
//				Notification notification=new Notification(getApplicationContext(),R.drawable.ic_launcher,
//						(CharSequence)(getResources().getString(R.string.notification)),0l,(CharSequence)"示例",(CharSequence)"点击查看",in);
//				notification.icon=R.drawable.ic_launcher;
//				notification.tickerText=getResources().getString(R.string.notification);
//				notification.flags = Notification.FLAG_AUTO_CANCEL;
//				notification.defaults=Notification.DEFAULT_SOUND;
//				notification.setLatestEventInfo(NotificationActivity.this, "示例", "点击查看", pi);
				NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
				notificationManager.notify(0, notification);
			}
		});
    }
}
