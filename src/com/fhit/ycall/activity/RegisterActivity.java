package com.fhit.ycall.activity;

import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpStatus;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.Observer.SmsObserver;
import com.fhit.ycall.http.ApiClient;
import com.fhit.ycall.http.AppException;
import com.fhit.ycall.http.HttpResponseResult;
import com.fhit.ycall.util.ConfigUtil;
import com.fhit.ycall.util.FunctionUtil;
import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.StringUtil;
import com.fhit.ycall.util.ToastUtil;

public class RegisterActivity extends BaseActivity {
	private static final int Get_Verify_Code = 0x101;
	private static final int Verify_Code = 0x102;
	private static final int To_Register = 0x103;
	private ContentObserver mObserver;
	//遮罩层
	private View floatingLayer;
	LinearLayout llPop;
	private EditText etPhone,etPwd,etVerifyCode;
	
	private TextView popTvPhone;
	
	private Handler handler = new BaseHandler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case Get_Verify_Code:
				dismissDialog();
				HttpResponseResult mHttpResponseResultA = (HttpResponseResult) msg.obj;
				floatingLayer.setVisibility(View.GONE);
				if(mHttpResponseResultA != null){
					if(mHttpResponseResultA.getStatusCode() == HttpStatus.SC_NO_CONTENT 
							|| mHttpResponseResultA.getStatusCode() == HttpStatus.SC_OK){
						ToastUtil.showLongToast(getResourceString(R.string.register_toast_get_sms_verify_code));
					}else{
						ToastUtil.showLongToast(mHttpResponseResultA.getErrorMsg());
					}
				}
				break;
			case Verify_Code:
				LogUtil.i("ycall", "RegisterActivity-handleMessage-获取短信的验证码："+(String)msg.obj);
				etVerifyCode.setText((String)msg.obj);
				ToastUtil.showLongToast(getResourceString(R.string.register_toast_has_get_sms_verify_code));
				break;
			case To_Register:
				dismissDialog();
				HttpResponseResult mHttpResponseResultB = (HttpResponseResult) msg.obj;
				floatingLayer.setVisibility(View.GONE);
				if(mHttpResponseResultB != null){
					if(mHttpResponseResultB.getStatusCode() == HttpStatus.SC_NO_CONTENT){
						ToastUtil.showLongToast(getResourceString(R.string.register_toast_ok));
						ConfigUtil.getInstance().setConfigString("phone", etPhone.getText().toString());
						ConfigUtil.getInstance().setConfigString("password", etPwd.getText().toString());
						goToLogin();
					}else{
						ToastUtil.showLongToast(mHttpResponseResultB.getErrorMsg());
					}
				}
				break;
			}
		}
	};
	private void goToLogin(){
		Intent loginIntent = new Intent(this, LoginActivity.class);
		startActivity(loginIntent);
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		bindService();
		registerObserver();
		initView();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unBindService();
		unregisterObserver();
	}
	private void initView() {
		((TextView)findViewById(R.id.center_input_et)).setVisibility(View.INVISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setText(R.string.register_title);
		((ImageView)findViewById(R.id.left_back_ic)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		llPop = (LinearLayout)findViewById(R.id.pop_container);
		floatingLayer = (View)findViewById(R.id.floating_layer);
		popTvPhone = (TextView)findViewById(R.id.pop_tv_2);
		etPhone = (EditText)findViewById(R.id.register_phone_et);
		etPwd = (EditText)findViewById(R.id.register_pwd_et);
		etVerifyCode = (EditText)findViewById(R.id.register_verify_code_et);
		((Button)findViewById(R.id.register_btn)).setOnClickListener(goToRegisterListener);
		((Button)findViewById(R.id.register_get_sms_verification_code)).setOnClickListener(getSmsVrifyCodeListener);
		((Button)findViewById(R.id.cancle)).setOnClickListener(cancleListener);
		((Button)findViewById(R.id.confirm)).setOnClickListener(okListener);
		
//		 etPhone.setText("18612014089");
//		 etPwd.setText("123456");
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(llPop.getVisibility() == View.GONE){
			super.onBackPressed();
		}else{
			llPop.setVisibility(View.GONE);
			floatingLayer.setVisibility(View.GONE);
		}
	}
	private  OnClickListener goToRegisterListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String cellphone = etPhone.getText().toString(); 
			String password = etPwd.getText().toString(); 
			String verifyCode = etVerifyCode.getText().toString(); 
			if(checkPhone(cellphone) && checkPwd(password) && checkVerifyCode(verifyCode)){
				mHttpBinder.register(handler, To_Register, cellphone, password, verifyCode);
				showDialog(getResourceString(R.string.register_dialog_registing));
			}
//			goToLogin();
		}
	};
	private  OnClickListener getSmsVrifyCodeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(checkPhone(etPhone.getText().toString())){
				FunctionUtil.showOrHideSystemKeyboard(RegisterActivity.this);
				floatingLayer.setVisibility(View.VISIBLE);
				llPop.setVisibility(View.VISIBLE);
				popTvPhone.setText(etPhone.getText().toString());
				((Button)findViewById(R.id.register_get_sms_verification_code)).setClickable(false);
				handler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						((Button)findViewById(R.id.register_get_sms_verification_code)).setClickable(true);
					}
				}, 30 * 1000);
			}
		}
	};
	private  OnClickListener okListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String phone = etPhone.getText().toString();
			if(checkPhone(phone)){
				mHttpBinder.getVerifyCode(handler, Get_Verify_Code, phone);
				floatingLayer.setVisibility(View.GONE);
				llPop.setVisibility(View.GONE);
				showDialog(getResources().getString(R.string.register_dialog_getting_verify_code));
			}
		}
	};
	private  OnClickListener cancleListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			floatingLayer.setVisibility(View.GONE);
			llPop.setVisibility(View.GONE);
		}
	};
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
		if(pwd.length() < 5){
			ToastUtil.showLongToast(getResources().getString(R.string.register_toast_pwd_length_longer));
			return false;
		}
		return true;
	}
	private boolean checkVerifyCode(String verifyCode){
		if(TextUtils.isEmpty(verifyCode)){
			ToastUtil.showLongToast(getResources().getString(R.string.register_toast_verify_code_is_null));
			return false;
		}
		return true;
	}
	private void registerObserver() {
//		content://sms/inbox     收件箱         
//        content://sms/sent        已发送
//        content://sms/draft        草稿           
//        content://sms/outbox    发件箱           (正在发送的信息)
//        content://sms/failed      发送失败     
//        content://sms/queued  待发送列表  (比如开启飞行模式后，该短信就在待发送列表里)
		//对短消息的观察Uri，通过测试我发现只能监听此Uri “content://sms” (等同于"content://sms/")，而不能监听其他的Uri，比如"content://sms/outbox"等。
		Uri uri = Uri.parse("content://sms");  
		mObserver = new SmsObserver(handler,Verify_Code,this);
		getContentResolver().registerContentObserver(uri, true, mObserver);
	}
	private void unregisterObserver() {
		getContentResolver().unregisterContentObserver(mObserver);
	}
}
