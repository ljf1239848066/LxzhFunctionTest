package com.lxzh123.quickmark;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.zxing.WriterException;
import com.lxzh123.funcdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class QuickMarkActivity extends Activity {

	private ImageView iv;
	private Button btnCreate;
	private Button btnAlpha;
	private Button btnSave;
	private EditText content;
	private Bitmap bitmap;
	
	private int screenWidth;
	private boolean isTransparent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quickmark);
        Log.d("onCreate", "1");
        btnCreate=(Button)findViewById(R.id.create);
        btnAlpha=(Button)findViewById(R.id.backalpha);
        btnSave=(Button)findViewById(R.id.save);
        iv=(ImageView)findViewById(R.id.iv_image);
        content=(EditText)findViewById(R.id.content);
        //获取屏幕宽度
        WindowManager wm = this.getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();
        Log.d("onCreate", "2");
        btnCreate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String contentString = content.getText().toString();
					if (!contentString.equals("")) {
						//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
						bitmap = EncodingHandler.createQRCode(contentString, screenWidth,isTransparent);
						iv.setImageBitmap(bitmap);
						btnSave.setVisibility(View.VISIBLE);
					}else {
						Toast.makeText(QuickMarkActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
					}
				} catch (WriterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
        btnAlpha.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				isTransparent=!isTransparent;
				if(isTransparent)
				{
					btnAlpha.setText("透明背景");
				}
				else
				{
					btnAlpha.setText("白色背景");
				}
			}
		});
        btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					saveAsPngFile(bitmap);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.print("Error:"+e.toString());
					Toast.makeText(getBaseContext(), "保存失败\n请检查存储卡是否可用", Toast.LENGTH_LONG).show();
				}
			}
		});
        Log.d("onCreate", "3");
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}

	public void saveAsPngFile(Bitmap bitmap)throws IOException
    {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd HHmmss");
    	String filename=android.os.Environment.getExternalStorageDirectory()+"/Pictures/"+sdf.format(new Date())+".png";
        File bitmapFile = new File(filename);  
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(bitmapFile));  
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);  
        bos.flush();  
        bos.close();  
        Toast.makeText(getBaseContext(), "二维码成功保存为文件\n"+filename, Toast.LENGTH_LONG).show();
    }
}
