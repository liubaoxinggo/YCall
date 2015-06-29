package com.fhit.ycall.util;

import java.lang.reflect.Method;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.android.internal.telephony.ITelephony;
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
	public static void hideSystemKeyboard(Activity mContext,EditText etInput){
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
	 * ������뷨�ڴ������Ѿ���ʾ�������أ���֮����ʾ
	 * @param mContext
	 */
	public static void showOrHideSystemKeyboard(Activity mContext){
		InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	public static boolean getSystemKeyboardState(Activity mContext){
		return ((InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).isActive();
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
	/**
	 * �������Ƿ񷢿�
	 * @param mContext
	 * @return
	 */
	public static boolean getSpeakerState(Context mContext){
		
		return ((AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE)).isSpeakerphoneOn();
	}
	/**
	 * ��������
	 * @param mContext
	 */
	public static void openSpeaker(Context mContext){
		((AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE)).setSpeakerphoneOn(true);
	}
	/**
	 * �ر�������
	 * @param mContext
	 */
	public static void closeSpeaker(Context mContext){
		((AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE)).setSpeakerphoneOn(false);
	}
	//����TelephonyManager��ITelephony�ӿ�˽�л�����ʵ��
	private static  ITelephony getITelephony(TelephonyManager telMgr)
			throws Exception {
		Method getITelephonyMethod = telMgr.getClass().getDeclaredMethod(
				"getITelephony");
		getITelephonyMethod.setAccessible(true);// ˽�л�����Ҳ��ʹ��
		return (ITelephony) getITelephonyMethod.invoke(telMgr);
	}
	/**
	 * �Ҷϵ绰
	 * @param telMgr
	 * @throws Exception
	 */
	public static void  endCall(TelephonyManager telMgr) throws Exception{
		getITelephony(telMgr).endCall();
	} 
	/**
	 * �Ҷϵ绰
	 * @param telMgr
	 * @throws Exception
	 */
	public static void  endCall(Context mContext) throws Exception{
		getITelephony((TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE)).endCall();
	} 
	/**
	 * �����绰
	 * @param mContext
	 */
	public static void answerCall(Context mContext){
		try {
			getITelephony((TelephonyManager)mContext.getSystemService(Context.TELEPHONY_SERVICE)).answerRingingCall();;
		} catch (Exception e) {
			LogUtil.e("ycall", "FunctionUtil answerCall �쳣��1��",e.getCause());
			try {
				Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
				KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
				intent.putExtra(Intent.EXTRA_KEY_EVENT,keyEvent);
				//��android4.1ϵͳ�Ժ� ���Ȩ��ֻ����ϵͳappʹ��
				mContext.sendOrderedBroadcast(intent,"android.permission.CALL_PRIVILEGED");
			} catch (Exception e2) {
				LogUtil.e("ycall", "FunctionUtil answerCall �쳣��2��",e.getCause());
				Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON); 
                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK); 
                meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT,keyEvent); 
                mContext.sendOrderedBroadcast(meidaButtonIntent, null); 
			}
		}
	}
}












