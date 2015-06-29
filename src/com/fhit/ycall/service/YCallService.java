package com.fhit.ycall.service;

import java.util.HashMap;
import java.util.Map;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.fhit.ycall.entity.RegisterUser;
import com.fhit.ycall.http.ApiClient;
import com.fhit.ycall.http.URLs;
import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.ThreadPoolManager;

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
		/**
		 * 获取验证码
		 * @param handler
		 * @param what
		 * @param cellphone
		 */
		public void getVerifyCode(Handler handler,int what,String cellphone){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cellphone", cellphone);
			ApiClient.getInstence().getObject(handler,what, URLs.VerifyCode, params);
		}
		/**
		 * 注册
		 * @param handler
		 * @param what
		 * @param cellphone
		 * @param password
		 * @param verifyCode
		 */
		public void register(Handler handler,int what,String cellphone,String password,String verifyCode){
			RegisterUser ru = new RegisterUser();
			ru.setCellphone(cellphone);
			ru.setPassword(password);
			ru.setVerifyCode(verifyCode);
			ApiClient.getInstence().postObject(handler, what, URLs.User, ru.toGsonString());
		}
		/**
		 * 登录
		 * @param handler
		 * @param what
		 * @param cellphone
		 * @param password
		 */
		public void login(Handler handler,int what,String cellphone,String password){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("username", cellphone);
			params.put("password", password);
			ApiClient.getInstence().getObject(handler,what, URLs.User, params);
		}
		/**
		 * 搜索
		 * @param handler
		 * @param what
		 * @param searchTag
		 */
		public void search(Handler handler,int what,String searchTag){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("tags", searchTag);
			ApiClient.getInstence().getObject(handler,what, URLs.Enterprise, params);
		}
		/**
		 * 关注企业
		 * @param handler
		 * @param what
		 * @param starId
		 */
		public void star(Handler handler,int what,int starId){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("starId", starId);
			ApiClient.getInstence().getObject(handler,what, URLs.Enterprise, params);
		}
		/**
		 * 取消关注企业
		 * @param handler
		 * @param what
		 * @param starId
		 */
		public void unstarId(Handler handler,int what,int unstarId){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("unstarId", unstarId);
			ApiClient.getInstence().getObject(handler,what, URLs.Enterprise, params);
		}
		/**
		 * 关注企业
		 * @param handler
		 * @param what
		 * @param starId
		 */
		public void call(Handler handler,int what,int uid){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", uid);
			ApiClient.getInstence().getObject(handler,what, URLs.Enterprise, params);
		}
		public void test(){
			ThreadPoolManager.getInstance().addTask(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						LogUtil.i("ycall", "测试 n = "+(n++));
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							LogUtil.i("ycall", "测试 n = "+(n)+" / 异常："+e.getLocalizedMessage());
						}
					}
				}
			});
		}
	}
	static long n = 0;
}











