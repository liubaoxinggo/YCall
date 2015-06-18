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
		 throw new AssertionError("FunctionUtil不能实例化，它下面都是静态方法");
	}
	/**
	 * 隐藏系统键盘<br>
	 * 3.0以下 的版本可以用etPhoneNumber.setInputType(InputType.TYPE_NULL)来实现;<br>
	 * 3.0以上的版本需另行方法
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
	 * 振动【需要权限 ：android.permission.VIBRATE】
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












