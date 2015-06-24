package com.fhit.ycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;

public class SplashActivity extends BaseActivity {

	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ImageView iv = new ImageView(this);
		iv.setImageResource(R.drawable.splash);
		setContentView(iv);
		handler = new Handler();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
				finish();
			}
		}, 300);
	}
}












