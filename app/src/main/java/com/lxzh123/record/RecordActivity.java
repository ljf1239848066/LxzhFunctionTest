package com.lxzh123.record;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lxzh123.funcdemo.R;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
//<uses-permission android:name="android.permission.RECORD_AUDIO" />
public class RecordActivity extends Activity {
	private Button record, stop;
	private MediaRecorder mediarecorder;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_record);
		record = (Button) findViewById(R.id.record);
		stop = (Button) findViewById(R.id.stop);
		stop.setEnabled(false);
		record.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (setPath()) {
					Date now = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					String recordname = sdf.format(now) + ".mp3";
					String path = android.os.Environment.getExternalStorageDirectory()
							+ "/FunctionTest/record/" + recordname;
					File file = new File(path);
					Toast.makeText(getApplicationContext(), "正在录音，录音文件在" + file.getAbsolutePath(),
							Toast.LENGTH_LONG).show();
					/*
					 * 录音的各项参数设置，设置顺序不能更改
					 */
					// 创建录音对象
					mediarecorder = new MediaRecorder();
					// 从麦克风源进行录音
					mediarecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					// 设置输出格式
					mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					// 设置编码格式
					mediarecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					// 设置输出文件
					mediarecorder.setOutputFile(file.getAbsolutePath());
					try {
						// 创建文件
						file.createNewFile();
						// 准备录制
						mediarecorder.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 开始录制
					mediarecorder.start();
					record.setText("录音中……");
					record.setEnabled(false);
					stop.setEnabled(true);
				}
			}
		});
		stop.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mediarecorder != null) {
					mediarecorder.stop();
					mediarecorder.release();
					mediarecorder = null;
					record.setText("录音");
					Toast.makeText(getApplicationContext(), "录音完毕", Toast.LENGTH_LONG).show();
					record.setEnabled(true);
					stop.setEnabled(false);
				}
			}
		});
	}

	// 创建多重用户文件路径，创建成功则返回true，否则返回false
	private boolean setPath() {
		String state = android.os.Environment.getExternalStorageState();
		if (!state.equals(android.os.Environment.MEDIA_MOUNTED))
			return false;
		File directory = new File(android.os.Environment.getExternalStorageDirectory() + "/HiChang");
		if (!directory.exists()) {
			if (!directory.mkdirs())
				return false;
			else {
				directory = new File(android.os.Environment.getExternalStorageDirectory()
						+ "/FunctionTest/record");
				if (!directory.mkdirs())
					return false;
				else
					return true;
			}
		} else {
			directory = new File(android.os.Environment.getExternalStorageDirectory()
					+ "/FunctionTest/record");
			if (directory.exists())
				return true;
			if (!directory.mkdirs())
				return false;
			else
				return true;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (stop.isEnabled()) {
			mediarecorder.stop();
			stop.setEnabled(false);
			record.setEnabled(true);
			mediarecorder.release();
		}
	}
}