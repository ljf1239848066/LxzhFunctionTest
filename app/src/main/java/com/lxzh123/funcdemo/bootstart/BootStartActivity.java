package com.lxzh123.funcdemo.bootstart;

import com.lxzh123.funcdemo.R;

import android.app.Activity;
import android.os.Bundle;

public class BootStartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_bootstart);
		new Thread() {
			public void run() {
				try {
					/* 10秒后关闭页面 */
					sleep(10000);
				} catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish(); // 关闭页面
                }
            }
        }.start();
	}
}
