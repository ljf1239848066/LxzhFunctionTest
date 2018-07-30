package com.lxzh123.funcdemo.filebrowser;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lxzh123.funcdemo.R;
import com.lxzh123.funcdemo.util.Common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
public class FileBrowserActivity extends ListActivity {
	private static final int Menu = 1;

	/* 文件列表 */
	private List<File> fileNameList;
	private Bundle bundle;
	private String fileNameKey = "fileName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_filebrowser);
		initFileList();
		registerForContextMenu(getListView());
	}

	private void initFileList() {
		Log.d("FileBrowser", android.os.Environment.getExternalStorageDirectory()+ Common.SDCARD_DIR);
		File path = new File(
				android.os.Environment.getExternalStorageDirectory() + Common.SDCARD_DIR);
		File[] f = path.listFiles();
		if(f!=null){
			fill(f);
		}
	}

	// 定制各菜单响应
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			finish();
		default:
			return false;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = fileNameList.get(position);
		if (file.isDirectory()) {
			File[] f = file.listFiles();
			fill(f);
		}
	}

	// 读取文件列表,并设置listview
	private void fill(File[] files) {
		fileNameList = new ArrayList<File>();
		for (File file : files) {
			if (isValidFileOrDir(file)) {
				fileNameList.add(file);
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, fileToStrArr(fileNameList));
		setListAdapter(adapter);
	}

	/* 检查是否为合法的文件名，或者是否为路径 */
	private boolean isValidFileOrDir(File file) {
		if (file.isDirectory()) {
			return true;
		} else {
			String fileName = file.getName().toLowerCase();
			if (fileName.endsWith(".txt")) {
				return true;
			}
		}
		return false;
	}

	private String[] fileToStrArr(List<File> fl) {
		ArrayList<String> fnList = new ArrayList<String>();
		for (int i = 0; i < fl.size(); i++) {
			String nameString = fl.get(i).getName();
			fnList.add(nameString);
		}
		return fnList.toArray(new String[0]);
	}
}
