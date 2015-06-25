package com.fhit.ycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;

public class LoginActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		initView();
	}

	private void initView() {
		((TextView)findViewById(R.id.center_input_et)).setVisibility(View.INVISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setText(R.string.login_in_title);
		((ImageView)findViewById(R.id.left_back_ic)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		((Button)findViewById(R.id.top_right_btn)).setVisibility(View.VISIBLE);
		((Button)findViewById(R.id.top_right_btn)).setText(R.string.register);
		((Button)findViewById(R.id.top_right_btn)).setOnClickListener(goToRegisterListener);
		((Button)findViewById(R.id.login_btn)).setOnClickListener(goToLoginListener);
	}
	private  OnClickListener goToRegisterListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
		}
	};
	private  OnClickListener goToLoginListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
		}
	};
}
