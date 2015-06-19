package com.fhit.ycall.util;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.fhit.ycall.TApplication;

@SuppressLint("ShowToast") 
public class ToastUtil {
	 
	public static void showLongToast(String msg){
		Toast.makeText(TApplication.AppContext, msg, Toast.LENGTH_LONG).show();
	}
	public static void showShortToast(String msg){
		Toast.makeText(TApplication.AppContext, msg, Toast.LENGTH_SHORT).show();
	}
	 
	 
	
	
}
