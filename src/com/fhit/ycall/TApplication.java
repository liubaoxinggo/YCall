package com.fhit.ycall;

import android.app.Application;
import android.content.Context;

public class TApplication extends Application {

	public static Context AppContext;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		AppContext = this;
	}
}
