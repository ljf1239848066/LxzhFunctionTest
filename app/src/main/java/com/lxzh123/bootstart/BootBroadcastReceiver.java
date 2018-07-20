package com.lxzh123.bootstart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String action_boot = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(action_boot)) {
			Log.d("onReceive", "可以启动了，以下注释内容取消后测试可用哦，这里就不启动了，免得被当做流氓软件哦~");
//			Intent ootStartIntent = new Intent(context, BootStartActivity.class);
//			ootStartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(ootStartIntent);
		}
	}
}