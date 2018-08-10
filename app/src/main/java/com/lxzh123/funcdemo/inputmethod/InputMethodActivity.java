package com.lxzh123.funcdemo.inputmethod;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.lxzh123.funcdemo.R;
import com.lxzh123.funcdemo.util.tools.ViewUtil;

public class InputMethodActivity extends Activity{
    private final static String TAG="InputMethodActivity";
	Button startInput;
	EditText etInput;
	RelativeLayout inputLayout;
	Boolean isShow = false;
	TranslateAnimation animationUp;
	TranslateAnimation animationDown;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_inputmethod);
        initComponent();
		startInput.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("startInput", "onClick");
				Log.d("inputLayout", "" + inputLayout.getTop());
				if (!isShow) {
					inputLayout.setVisibility(View.VISIBLE);
					inputLayout.startAnimation(animationUp);
				} else {
					inputLayout.startAnimation(animationDown);
				}
				isShow = !isShow;
			}
		});
		etInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					inputLayout.setVisibility(View.VISIBLE);
					inputLayout.startAnimation(animationUp);
				} else {
					inputLayout.startAnimation(animationDown);
				}
				isShow = !isShow;
			}
		});
        ViewUtil.DisableShowInput(etInput);
	}

	public void OnInputButtonClicked(View view){
	    if(view.getId()==R.id.btnok){
            inputLayout.startAnimation(animationDown);
            isShow = !isShow;
        }else{
            String text=etInput.getText().toString();
            String str=((Button)view).getText().toString();
            int start=etInput.getSelectionStart();
            int end=etInput.getSelectionEnd();
            Log.d(TAG,"OnInputButtonClicked:("+start+","+end+")");
            if(start<=end){
                text=text.substring(0,start)+str+text.substring(end);
            }else{
                text=str;
            }
            Log.d(TAG,"OnInputButtonClicked:"+str+","+text);
            etInput.setText(text);
            etInput.setSelection(start+1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initAnim();
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void initComponent() {
		startInput = (Button) findViewById(R.id.startinput);
		etInput = (EditText) findViewById(R.id.etInput);
		inputLayout = (RelativeLayout) findViewById(R.id.inputlayout);
	}

	private void initAnim(){
        ViewUtil vu=new ViewUtil(inputLayout);
        int height=vu.getHeight();
        if(height<50){
            height=300;
        }
        animationUp = new TranslateAnimation(0, 0, height, 0);
        animationUp.setFillAfter(true);
        animationUp.setDuration(500);
        animationDown = new TranslateAnimation(0, 0, 0, height);
        animationDown.setFillAfter(true);
        animationDown.setDuration(500);
    }
}
