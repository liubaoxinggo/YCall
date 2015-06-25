package com.fhit.ycall.util;

import com.fhit.ycall.TApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class ConfigUtil {


	private SharedPreferences sp;	 
	public ConfigUtil(){		 
			sp = TApplication.AppContext.getSharedPreferences("YCALL_SP", 
					Context.MODE_PRIVATE);		 
	}
	public ConfigUtil(Context context){		 
		sp = context.getSharedPreferences(context.getPackageName(), 
				Context.MODE_PRIVATE);		 
	}
	private static ConfigUtil instance;
	public static ConfigUtil getInstance(){
		if(instance == null ){
			synchronized (ConfigUtil.class) {
				if(instance == null){
					instance = new ConfigUtil();
				}
			}
		}
		return instance;
	}
	public static ConfigUtil getInstance(Context context){
		if(instance == null ){
			synchronized (ConfigUtil.class) {
				if(instance == null){
					instance = new ConfigUtil(context);
				}
			}
		}
		return instance;
	}
	public String getConfigString(String key){
		if(sp != null){
			return sp.getString(key, "");
		}
		return null;
	}
	public int getConfigInt(String key){
		if(sp != null){
			return sp.getInt(key, -1);
		}
		return -1;
	}
	public boolean getConfigBoolean(String key){
		if(sp != null){
			return sp.getBoolean(key, false);
		}
		return false;
	}
	public void setConfigString(String key,String value){
		if(sp != null){
			Editor editor = sp.edit();
			editor.putString(key, value);
			editor.commit();
		}
	}
	public void setConfigBoolean(String key,boolean value){
		if(sp != null){
			Editor editor = sp.edit();
			editor.putBoolean(key, value);
			editor.commit();
		}
	}
	public void setConfigInt(String key,int value){
		if(sp != null){
			Editor editor = sp.edit();
			editor.putInt(key, value);			
			editor.commit();
		}
	}
 
	public void remove(String key){
		if(sp != null){
			Editor editor = sp.edit();
			editor.remove(key); 
			editor.commit();
		}
	}
	 
	
}
