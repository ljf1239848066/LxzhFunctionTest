package com.lxzh123.funcdemo.ftp;

import java.util.Vector;

import org.apache.commons.net.ftp.FTPClient;

import com.lxzh123.funcdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FTPActivity extends Activity {
	Button btnStart;
	FTPManager ftpMan;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ftp_main);
        Log.d("start", "程序启动");
        btnStart=(Button)findViewById(R.id.start);
        ftpMan=new FTPManager();
        Log.d("start", "初始化完毕");
        btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("btnStart", "按钮被按下");
				try {
					Log.d("btnStart", "进入try");
					FTPClient ftpClient = ftpMan.getFTPClient();
					Log.d("btnStart", "连接ftp服务器");
					Vector<String> fileNames = ftpMan.getFileListOfFtp(ftpClient);			
					for (String fileName : fileNames) {
						Log.d("for", "拷贝文件"+fileName);
						ftpMan.downloadFile(ftpClient, ftpMan.code(fileName), "download_"+fileName);
					}			
					ftpMan.disconnetFTPServer(ftpClient);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("catch", "异常");
				}
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
