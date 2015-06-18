package com.fhit.ycall.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import android.util.Log;

public class LogUtil {
	public static boolean isDebug = true;
	public static boolean isToSD = true;

	public static void v(String tag, String msg) {
		if (isDebug)
			android.util.Log.v(tag, msg);
	}

	public static void v(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.v(tag, msg, t);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			android.util.Log.d(tag, msg);
	}

	public static void d(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.d(tag, msg, t);
	}

	public static void i(String tag, String msg) {
		if (isDebug)
			android.util.Log.i(tag, msg);
	}

	public static void i(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.i(tag, msg, t);
	}

	public static void w(String tag, String msg) {
		if (isDebug)
			android.util.Log.w(tag, msg);
	}

	public static void w(String tag, String msg, Throwable t) {
		if (isDebug)
			android.util.Log.w(tag, msg, t);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			android.util.Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable t) {
		if (isDebug){
			android.util.Log.e(tag, msg, t);
			android.util.Log.e(tag, logCrashInfo(t));
		}
	}
	public static void iSave(String tag,String msg){
		if(isDebug) android.util.Log.i(tag, msg);
//		if(isToSD)UCallUtil.saveLog(msg);
	}
	public static void eSave(String tag,String msg){
		if(isDebug) android.util.Log.e(tag, msg);
//		if(isToSD)UCallUtil.saveLog(msg);
	}
	public static void eSave(String tag,String msg,Throwable t){
		if(isDebug) android.util.Log.e(tag, msg,t);
//		if(isToSD)UCallUtil.saveLog(msg+"\n“Ï≥£ƒ⁄»›£∫"+Log.getStackTraceString(t));
	}
	private static String logCrashInfo(Throwable ex){
		if(ex != null){
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			ex.printStackTrace(printWriter);
			Throwable cause = ex.getCause();
			while(cause != null){
				cause.printStackTrace(printWriter);
				cause = cause.getCause();
			}
			printWriter.close();
			return writer.toString();
		}
		return "cause end";
	}
	
}
