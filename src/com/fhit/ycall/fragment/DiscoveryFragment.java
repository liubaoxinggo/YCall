package com.fhit.ycall.fragment;

import com.fhit.ycall.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ResourceAsColor") public class DiscoveryFragment extends Fragment {

	Context mContext;
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
		View view = inflater.inflate(R.layout.discovery, container, false);
		initView(view);
		addListener(view);
		return view;
	}
	private void initView(View view) {
		
	}
	private void addListener(View view) {
		
	}
}
