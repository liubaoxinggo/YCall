package com.fhit.ycall.fragment;

import android.support.v4.app.Fragment;

public abstract class HistoryBaseFragment extends Fragment {

	/**
	 * ���ؼ���
	 */
	public void hideKeyboard(){}
	/**
	 * 
	 * @param duration �����ĳ���ʱ��
	 */
	public void hideKeyboard(long duration){}
	/**
	 * ��ʾ����	 
	 */
	public void showKeyboard(){}
	/**
	 * 
	 * @param duration �����ĳ���ʱ��
	 */
	public void showKeyboard(long duration){}
	/**
	 * �������
	 */
	public void clearInput(){};
	/**
	 * ����
	 */
	public abstract void call();
}
