package com.fhit.ycall.fragment;

import android.support.v4.app.Fragment;

public abstract class HistoryBaseFragment extends Fragment {

	/**
	 * 隐藏键盘
	 */
	public void hideKeyboard(){}
	/**
	 * 
	 * @param duration 动画的持续时间
	 */
	public void hideKeyboard(long duration){}
	/**
	 * 显示键盘	 
	 */
	public void showKeyboard(){}
	/**
	 * 
	 * @param duration 动画的持续时间
	 */
	public void showKeyboard(long duration){}
	/**
	 * 清除输入
	 */
	public void clearInput(){};
	/**
	 * 呼叫
	 */
	public abstract void call();
}
