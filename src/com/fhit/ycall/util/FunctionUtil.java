package com.fhit.ycall.util;

import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Service;
import android.os.Vibrator;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

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
		if (android.os.Build.VERSION.SDK_INT <= 10) {
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
	public static void vibrate(Activity mContext,long milliseconds){
		((Vibrator)mContext.getSystemService(Service.VIBRATOR_SERVICE)).vibrate(milliseconds);
	}
	/**
	 * 
	 * @param mContext
	 * @param pattern
	 * @param repeat
	 */
	public static void vibrate(Activity mContext,long[] pattern,int repeat){
		((Vibrator)mContext.getSystemService(Service.VIBRATOR_SERVICE)).vibrate(pattern, repeat);
	}
	
}












