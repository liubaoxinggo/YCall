package com.fhit.ycall;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;

import com.fhit.ycall.activity.WebActivity;
import com.fhit.ycall.http.AppException;
import com.fhit.ycall.http.BaseApiClient;
import com.fhit.ycall.http.URLs;
import com.fhit.ycall.service.YCallService;
import com.fhit.ycall.service.YCallService.HttpBinder;
import com.fhit.ycall.util.DialogUtil;
import com.fhit.ycall.util.LogUtil;

public class BaseActivity extends FragmentActivity {

	public HttpBinder mHttpBinder;
	private Intent service;
	private Boolean isBind;
	private Dialog mDialog ;
	private ServiceConnection conn = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			String s = null;
			if(name != null){
				s = name.getClassName();
			}
//			LogUtil.i("ycall",BaseActivity.this.getLocalClassName()+":ServiceConnection--onServiceDisconnected() ComponentName = "+s+"-----"+Thread.currentThread());
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			String s = null;
			if(name != null){
				s = name.getClassName();
			}
//			LogUtil.i("ycall",BaseActivity.this.getLocalClassName()+":ServiceConnection--onServiceConnected() ComponentName = "+s+"-----"+Thread.currentThread());
			mHttpBinder = (HttpBinder)service;
		}
	};
	@SuppressLint("HandlerLeak") 
	public class BaseHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == BaseApiClient.NETWOERK_EXCEPTION){
				((AppException)msg.obj).makeToast(getApplication());
				dismissDialog();
			}
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		URLs.initUrls();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	public void showDialog(String msg){
		dismissDialog();
		mDialog = DialogUtil.createProgressDilog(this, msg);
		mDialog.setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				onDismissDialog();
			}
		});
		mDialog.show();
	}
	public void dismissDialog(){
		if(mDialog != null){
			mDialog.dismiss();
			mDialog = null;
		}
	}
	/**
	 * mDialog消失时调用，其子Activity可以复写该函数
	 */
	public void onDismissDialog(){
		
	}
	/**
	 * 获取字符串资源
	 * @param id
	 * @return
	 */
	public String getResourceString(int id){
		return this.getResources().getString(id);
	}
	/**
	 * 绑定服务
	 */
	public void bindService(){
		service = new Intent(this, YCallService.class);
		isBind = this.bindService(service, conn, Context.BIND_AUTO_CREATE);
		LogUtil.i("ycall",this.getLocalClassName()+" 绑定service 成功与否："+isBind+"-----"+Thread.currentThread());
	}
	/**
	 * 解除服务绑定
	 */
	public void unBindService(){
		if(isBind){
			LogUtil.i("ycall",this.getLocalClassName()+" 解除绑定 "+"-----"+Thread.currentThread());
			this.unbindService(conn);
			isBind = false;
			mHttpBinder = null;  
		}else{
			LogUtil.i("ycall",this.getLocalClassName()+" 未绑定，无法解除绑定 "+"-----"+Thread.currentThread());
		}
	}
	public void goToWeb(String url,String title){
		Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
		webIntent.putExtra(WebActivity.LOAD_URL, url);
		webIntent.putExtra(WebActivity.LOAD_TITLE, title);
		startActivity(webIntent);
	}
	public void goToWeb(String url,String title,int from){
		Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
		webIntent.putExtra(WebActivity.LOAD_URL, url);
		webIntent.putExtra(WebActivity.LOAD_TITLE, title);
		webIntent.putExtra(WebActivity.LOAD_FROM, from);
		startActivity(webIntent);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	
}
