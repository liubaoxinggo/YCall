package com.fhit.ycall.fragment;


public abstract class HistoryBaseFragment extends BaseFragment {

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
