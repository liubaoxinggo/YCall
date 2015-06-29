package com.fhit.ycall;

import java.net.MalformedURLException;
import java.net.URL;

import com.fhit.ycall.util.LogUtil;

import android.app.Application;
import android.content.Context;
/**
 * Git:https://github.com/liubaoxinggo/YCall.git
 * @author liubaoxing
 *
 */
public class TApplication extends Application {
	
	public static Context AppContext;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		AppContext = this;
//		test();
	}
	void test(){
		try {
			URL u = new URL("http://yuehu.fhit.com.cn/api/v1/Enterprise?tags=¿≤¿≤¿≤");
			LogUtil.iSave("ycall", ""+u.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			LogUtil.e("ycall", "“Ï≥££∫"+e.getLocalizedMessage(), e.fillInStackTrace());
		}
	}
}
