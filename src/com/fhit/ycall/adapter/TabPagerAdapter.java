package com.fhit.ycall.adapter;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
	
	private List<Fragment> list;
	
	public void setList(List<Fragment> list) {
		this.list = list;
		this.notifyDataSetChanged();
	}

	//���캯��
	public TabPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		//LogUtil.e("infos", "TabPagerAdapter---position = "+position+" / object = "+object);
		//��д�˷��������մ�������
		//super.destroyItem(container, position, object);
	}

	@Override
	public Parcelable saveState() {
		// TODO Auto-generated method stub
		return super.saveState();
	}

}
