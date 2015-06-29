package com.fhit.ycall.fragment;


public abstract class HistoryBaseFragment extends BaseFragment {

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
