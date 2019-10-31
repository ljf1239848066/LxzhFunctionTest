package com.lxzh123.funcdemo.remoteres;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lxzh123.funcdemo.R;

public class RemoteResActivity extends Activity {

    ImageView imageView;
    //    ActivityManager mPluginManager = ActivityManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_remote_res);
        ResourceManager.init(this);
//        mPluginManager.init(this);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    public void loadInstalledBundle(View v) {
        Drawable drawable = ResourceManager.installed().getDrawable("com.lxzh123.demo.testview", "background1");
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    public void loadUninstalledBundle(View v) {
        LoadedResource loadResource = ResourceManager.unInstalled().loadResource("/storage/sdcard0/bundle.apk");
        Drawable drawable = ResourceManager.unInstalled().getDrawable(loadResource.packageName, "image");
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }
}
