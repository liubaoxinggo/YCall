package com.fhit.ycall.activity;

import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpStatus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.http.HttpHelper;
import com.fhit.ycall.http.HttpResponseResult;
import com.fhit.ycall.util.ConfigUtil;
import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.StringUtil;
import com.fhit.ycall.util.ToastUtil;

public class LoginActivity extends BaseActivity {

	private static final int TO_LOGIN = 0x001;
	private EditText etPhone,etPwd;
	private Handler handler = new BaseHandler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case TO_LOGIN:
				dismissDialog();
				HttpResponseResult mHttpResponseResultA = (HttpResponseResult) msg.obj;
				if(mHttpResponseResultA != null){
					if(mHttpResponseResultA.getStatusCode() == HttpStatus.SC_NO_CONTENT 
							|| mHttpResponseResultA.getStatusCode() == HttpStatus.SC_OK){
						ConfigUtil.getInstance().setConfigString("phone", etPhone.getText().toString());
						ConfigUtil.getInstance().setConfigString("password", etPwd.getText().toString());
						HttpHelper.setAuthorization(etPhone.getText().toString(), etPwd.getText().toString());
						goToMain();
					}else{
						ToastUtil.showLongToast(mHttpResponseResultA.getErrorMsg());
					}
				}
				break;
			default:
				break;
			}
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bindService();
		setContentView(R.layout.login);
		initView();
		LogUtil.i("ycall", this.getLocalClassName()+"--onCreate()");
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtil.i("ycall", this.getLocalClassName()+"--onStart()");
		initData();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		LogUtil.i("ycall", this.getLocalClassName()+"--onRestart()");
		super.onRestart();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		LogUtil.i("ycall", this.getLocalClassName()+"--onResume()");
		super.onResume();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		LogUtil.i("ycall", this.getLocalClassName()+"--onWindowFocusChanged():hasFocus = "+hasFocus);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		LogUtil.i("ycall", this.getLocalClassName()+"--onPause()");
		super.onPause();
	}
	@Override
	protected void onStop() {
		LogUtil.i("ycall", this.getLocalClassName()+"--onStop()");
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		LogUtil.i("ycall", this.getLocalClassName()+"--onDestroy()");
		super.onDestroy();
		unBindService();
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
		
		etPhone = (EditText)findViewById(R.id.login_phone_et);
		etPwd = (EditText)findViewById(R.id.login_pwd_et);
		
		((Button)findViewById(R.id.top_right_btn)).setVisibility(View.VISIBLE);
		((Button)findViewById(R.id.top_right_btn)).setText(R.string.register);
		((Button)findViewById(R.id.top_right_btn)).setOnClickListener(goToRegisterListener);
		((Button)findViewById(R.id.login_btn)).setOnClickListener(goToLoginListener);
	}
	private void initData(){
		etPhone.setText(ConfigUtil.getInstance().getConfigString("phone"));
		etPwd.setText(ConfigUtil.getInstance().getConfigString("password"));
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
			String phone = etPhone.getText().toString();
			String password = etPwd.getText().toString();
			if(checkPhone(phone) && checkPwd(password)){
				showDialog(getResourceString(R.string.logining));
				mHttpBinder.login(handler, TO_LOGIN, phone, password);
			}
		}
	};
	private void goToMain(){
		startActivity(new Intent(LoginActivity.this, MainActivity.class));
		finish();
	}
	private boolean checkPhone(String phone){
		if(TextUtils.isEmpty(phone)){
			ToastUtil.showLongToast(getResources().getString(R.string.toast_phone_is_null));
			return false;
		}
		if(!Pattern.matches(StringUtil.MobileRegex, phone)){
			ToastUtil.showLongToast(getResources().getString(R.string.toast_phone_is_not_right));
			return false;
		}
		return true;
	}
	private boolean checkPwd(String pwd){
		if(TextUtils.isEmpty(pwd)){
			ToastUtil.showLongToast(getResources().getString(R.string.toast_pwd_is_null));
			return false;
		}
//		if(pwd.length() < 5){
//			ToastUtil.showLongToast(getResources().getString(R.string.register_toast_pwd_length_longer));
//			return false;
//		}
		return true;
	}
}
