package com.fhit.ycall.fragment;

import android.support.v4.app.Fragment;

public abstract class HistoryBaseFragment extends Fragment {

	/**
	 * ���ؼ���
	 */
	public abstract void hideKeyboard();
	/**
	 * ��ʾ����	 
	 */
	public abstract void showKeyboard();
	/**
	 * �������
	 */
	public  void clearInput(){};
	/**
	 * ����
	 */
	public abstract void call();
}
