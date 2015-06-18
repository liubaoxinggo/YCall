package com.fhit.ycall.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MViewPager extends ViewPager {

	
	private boolean isCanScroll = true;//[Ĭ���ǿ��Ի�����]

	public MViewPager(Context context) {
		super(context);
	}

	public MViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	@Override
	public void scrollTo(int x, int y) {
			super.scrollTo(x, y);
	}

	@Override
	public void setCurrentItem(int item, boolean smoothScroll) {
		// TODO Auto-generated method stub
		super.setCurrentItem(item, smoothScroll);
	}

	@Override
	public void setCurrentItem(int item) {
		// TODO Auto-generated method stub
		super.setCurrentItem(item);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	//������д�����������������return false ʱ��ViewPager�Ͳ������ĵ���ָ�������¼��ˣ�
	//ת�����ݸ��ϲ�Viewȥ������߸��¼���ֱ����ֹ�� 
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		if(isCanScroll){
			return super.onTouchEvent(e);
		}else{
			return false;
		}
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		// TODO Auto-generated method stub
		if(isCanScroll){
			return super.onInterceptTouchEvent(e);
		}else{
			return false;
		}
	}
	


}
