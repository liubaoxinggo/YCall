package com.fhit.ycall.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MViewPager extends ViewPager {

	
	private boolean isCanScroll = true;//[默认是可以滑动的]

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
	//必须重写下面的两个方法，当return false 时，ViewPager就不会消耗掉手指滑动的事件了，
	//转而传递给上层View去处理或者该事件就直接终止了 
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
