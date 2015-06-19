package com.fhit.ycall.util;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.os.Build;
import android.os.Vibrator;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;
/**
 * ϵͳ���ܸ���ʵ��
 * @author liubaoxing
 *
 */
public class FunctionUtil {

	public FunctionUtil() {
		 throw new AssertionError("FunctionUtil����ʵ�����������涼�Ǿ�̬����");
	}
	/**
	 * ����ϵͳ����<br>
	 * 3.0���� �İ汾������etPhoneNumber.setInputType(InputType.TYPE_NULL)��ʵ��;<br>
	 * 3.0���ϵİ汾�����з���
	 */
	public static void setCursor(Activity mContext,EditText etInput){
		if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
			etInput.setInputType(InputType.TYPE_NULL);
		} else {
			mContext.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
				setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(etInput, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * �񶯡���ҪȨ�� ��android.permission.VIBRATE��
	 * @param mContext
	 * @param milliseconds
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
	public static void vibrate(Activity mContext,long milliseconds){
		if(((Vibrator)mContext.getSystemService(Service.VIBRATOR_SERVICE)).hasVibrator()){
			((Vibrator)mContext.getSystemService(Service.VIBRATOR_SERVICE)).vibrate(milliseconds);
		}
		
	}
	/**
	 *  �񶯡���ҪȨ�� ��android.permission.VIBRATE��
	 * @param mContext
	 * @param pattern
	 * @param repeat
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
	public static void vibrate(Activity mContext,long[] pattern,int repeat){
		if(((Vibrator)mContext.getSystemService(Service.VIBRATOR_SERVICE)).hasVibrator()){
			((Vibrator)mContext.getSystemService(Service.VIBRATOR_SERVICE)).vibrate(pattern, repeat);
		}
	}
	
}












