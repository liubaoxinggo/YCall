package com.fhit.ycall.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fhit.ycall.R;
import com.fhit.ycall.activity.WebActivity;

@SuppressLint("ResourceAsColor") 
public class DiscoveryFragment extends BaseFragment {

	private final static String GAME_URL = "http://app100668422.imgcache.qzoneapp.com/app100668422/wxgames/index4.html";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		((RelativeLayout)view.findViewById(R.id.rl_op_1)).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.rl_op_1://сно╥
			goToWeb(GAME_URL, getResourceString(R.string.game),WebActivity.FROM_GAME);
			break;
		default:
			break;
		}
	}
}
