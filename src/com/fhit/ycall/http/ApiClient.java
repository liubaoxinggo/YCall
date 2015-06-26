package com.fhit.ycall.http;

import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.ThreadPoolManager;


public class ApiClient {

	/**
	 * Õ¯¬Á“Ï≥£what
	 */
	public static final int NETWOERK_EXCEPTION = 0x123;
	private static ApiClient mApiClient;
	public ApiClient() {
		super();
	}
	public static ApiClient getInstence(){
		if(mApiClient == null){
			synchronized (ApiClient.class) {
				if(mApiClient == null){
					mApiClient = new ApiClient();
				}
			}
		}
		return mApiClient;
	}
	private void handleHandler(Handler handler,int what,Object mHttpResponse){
		if(handler != null){
			Message msg = handler.obtainMessage(what);
			msg.obj = mHttpResponse;
			handler.sendMessage(msg);
		}
	}
	private void handleContext(Context mContext,String action,HttpResponseResult mHttpResponse){
		if(mContext != null){
			
		}
	}
	public void getObject(Handler handler,int what, String pUrl, Map<String, Object> params){
		getObject(null, null, handler, what, pUrl, params);
	}
	public void getObject(Context mContext,String action, String pUrl, Map<String, Object> params){
		getObject(mContext, action, null, -1, pUrl, params);
	}
	public void getObject(final Context mContext,final String action,final Handler handler,final int what,final String pUrl,final Map<String, Object> params){
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HttpResponseResult mHttpResponse = HttpHelper.http_get(HttpHelper.makeURL(pUrl, params));
					if(mHttpResponse != null){
						handleHandler(handler, what, mHttpResponse);
						handleContext(mContext, action, mHttpResponse);
						LogUtil.i("ycall", "ApiClient getObject-url = "+pUrl+" - HttpResponseResult : "+mHttpResponse.toString());
					}else{
					}
				} catch (Exception e) {
					handleHandler(handler, NETWOERK_EXCEPTION, e);
					LogUtil.eSave("ycall", "ApiClient getObject “Ï≥£ "+pUrl, e.fillInStackTrace());
				} 
 
			}
		});
	}
	public void postObject(final Handler handler,final int what,final String pUrl,final String postInfo){
		postObject(null, null, handler, what, pUrl, postInfo);
	}
	public void postObject(final Context mContext,final String action,final Handler handler,final int what,final String pUrl,final String postInfo){
		ThreadPoolManager.getInstance().addTask(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					HttpResponseResult mHttpResponse = HttpHelper.http_post(pUrl, postInfo);
					if(mHttpResponse != null){
						handleHandler(handler, what, mHttpResponse);
						handleContext(mContext, action, mHttpResponse);
						LogUtil.i("ycall", "ApiClient postObject-url = "+pUrl+" - HttpResponseResult : "+mHttpResponse.toString());
					}else{
					}
				} catch (Exception e) {
					handleHandler(handler, NETWOERK_EXCEPTION, e);
					LogUtil.eSave("infos", "ApiClient postObject “Ï≥£ "+pUrl, e.fillInStackTrace());
				} 
			}
		});
	}
	
	
	
	
	
	
}
