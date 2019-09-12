package com.lxzh123.funcdemo.webview;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.lxzh123.funcdemo.R;

public class WebViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_web_view);

        RelativeLayout view = findViewById(R.id.layout_webview_container);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = 300;

        WebView webView = new WebView(this);
        webView.setLayoutParams(layoutParams);
        webView.loadUrl("file:////android_asset/webview_test.html");
        view.addView(webView);
    }
}
