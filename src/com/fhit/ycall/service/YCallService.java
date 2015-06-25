package com.fhit.ycall.service;

import java.util.HashMap;
import java.util.Map;

import com.fhit.ycall.entity.LoginUser;
import com.fhit.ycall.entity.RegisterUser;
import com.fhit.ycall.http.ApiClient;
import com.fhit.ycall.http.URLs;
import com.fhit.ycall.util.LogUtil;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class YCallService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		LogUtil.eSave("ycall", "YCallService---onBind()");
		return new HttpBinder();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		LogUtil.eSave("ycall", "YCallService---onUnbind()");
		return super.onUnbind(intent);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		LogUtil.eSave("ycall", "YCallService---onStartCommand()");
		flags = START_STICKY;
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		LogUtil.eSave("ycall", "YCallService---onLowMemory()");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		LogUtil.eSave("ycall", "YCallService---onDestroy()");
	}
	public class HttpBinder extends Binder{
		public void getVerifyCode(Handler handler,int what,String cellphone){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cellphone", cellphone);
			ApiClient.getInstence().getObject(handler,what, URLs.VerifyCode, params);
		}
		public void register(Handler handler,int what,String cellphone,String password,String verifyCode){
			RegisterUser ru = new RegisterUser();
			ru.setCellphone(cellphone);
			ru.setPassword(password);
			ru.setVerifyCode(verifyCode);
			ApiClient.getInstence().postObject(handler, what, URLs.User, ru.toGsonString());
		}
		public void login(Handler handler,int what,String cellphone,String password){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("Cellphone", cellphone);
			params.put("Password", cellphone);
			ApiClient.getInstence().getObject(handler,what, URLs.User, params);
		}
	}
}











