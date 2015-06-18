package com.fhit.ycall.fragment;

import android.support.v4.app.Fragment;

public abstract class HistoryBaseFragment extends Fragment {

	/**
	 * Òş²Ø¼üÅÌ
	 */
	public abstract void hideKeyboard();
	/**
	 * ÏÔÊ¾¼üÅÌ	 
	 */
	public abstract void showKeyboard();
	/**
	 * Çå³ıÊäÈë
	 */
	public  void clearInput(){};
	/**
	 * ºô½Ğ
	 */
	public abstract void call();
}
