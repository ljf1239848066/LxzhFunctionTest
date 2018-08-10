package com.lxzh123.funcdemo.inputmethod;

import com.lxzh123.funcdemo.R;

import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class InputMethodActivity extends Activity {

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void initComponent() {
		startInput = (Button) findViewById(R.id.startinput);
		etInput = (EditText) findViewById(R.id.et);
		inputLayout = (RelativeLayout) findViewById(R.id.inputlayout);
		etInput.setInputType(InputType.TYPE_NULL);
		animationUp = new TranslateAnimation(0, 0, 350, 0);
		animationUp.setFillAfter(true);
		animationUp.setDuration(600);
		animationDown = new TranslateAnimation(0, 0, 0, 350);
		animationDown.setFillAfter(true);
		animationDown.setDuration(600);
	}
}
