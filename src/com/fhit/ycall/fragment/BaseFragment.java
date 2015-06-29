package com.fhit.ycall.fragment;

import com.fhit.ycall.activity.WebActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class BaseFragment extends Fragment implements OnClickListener{

	public Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this.getActivity();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 获取字符串资源
	 * @param id
	 * @return
	 */
	public String getResourceString(int id){
		return this.getResources().getString(id);
	}
	public void goToWeb(String url,String title){
		Intent webIntent = new Intent(getActivity(), WebActivity.class);
		webIntent.putExtra(WebActivity.LOAD_URL, url);
		webIntent.putExtra(WebActivity.LOAD_TITLE, title);
		startActivity(webIntent);
	}
	public void goToWeb(String url,String title,int from){
		Intent webIntent = new Intent(getActivity(), WebActivity.class);
		webIntent.putExtra(WebActivity.LOAD_URL, url);
		webIntent.putExtra(WebActivity.LOAD_TITLE, title);
		webIntent.putExtra(WebActivity.LOAD_FROM, from);
		startActivity(webIntent);
	}
}
