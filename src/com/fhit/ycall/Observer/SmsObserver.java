package com.fhit.ycall.Observer;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fhit.ycall.util.LogUtil;

public class SmsObserver extends ContentObserver {
	/**
	 * 监视号码
	 */
	private static final String OBSERVER_NUMBER_1 = "10690275402375";
	private static final String OBSERVER_NUMBER_2 = "10690204402375";
	private Context context;
	private Handler handler;
	private int what;
	public SmsObserver(Handler handler) {
		super(handler);
	}
	
	public SmsObserver(Handler handler, int what, Context context) {
		super(handler);
		this.handler = handler;
		this.what = what;
		this.context = context;
		
	}

	@Override
	public boolean deliverSelfNotifications() {
		// TODO Auto-generated method stub
		LogUtil.i("ycall", "ContentObserver deliverSelfNotifications()");
		return super.deliverSelfNotifications();
	}

	@Override
	public void onChange(boolean selfChange) {
		// TODO Auto-generated method stub
		super.onChange(selfChange);
		LogUtil.i("ycall", "ContentObserver onChange() : selfChange = "+selfChange);
		handleSms();
	}
	private synchronized void  handleSms(){
		Cursor cursor = context.getContentResolver().query(Uri.parse("content://sms/inbox"), 
				new String[]{"_id", "address", "body","read"}, 
				"read=?",          //"address=? and read=?", 
				new String[]{"0"},//new String[]{"125201510100x00y", "0"}, 
				"date desc");
		if(cursor != null && cursor.moveToFirst()){
			 String address = cursor.getString(cursor.getColumnIndex("address"));  
	         String content = cursor.getString(cursor.getColumnIndex("body"));
	         LogUtil.iSave("ycall", " SmsObserver from :"+address +" | content:"+content);
//	         06-25 21:11:33.312: I/ycall(15584):  SmsObserver from :10690275402375 | content:【北京峰华】您的验证码是:424471
	         if(address.endsWith(OBSERVER_NUMBER_1) || address.endsWith(OBSERVER_NUMBER_2)){
	        	 Message msg = handler.obtainMessage(what);
	        	 msg.obj = content.substring(content.indexOf(":")+1);
	        	 handler.sendMessage(msg);
	         }
		}
	}
}
