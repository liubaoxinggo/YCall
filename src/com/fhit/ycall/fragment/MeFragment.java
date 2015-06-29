package com.fhit.ycall.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fhit.ycall.R;
import com.fhit.ycall.util.ToastUtil;

public class MeFragment extends BaseFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.me, container, false);
		initView(view);
		addListener(view);
		initData();
		return view;
	}
	private void initView(View view) {
		
	}
	private void addListener(View view) {
		((RelativeLayout)view.findViewById(R.id.rl_op_1)).setOnClickListener(this);
		((RelativeLayout)view.findViewById(R.id.rl_op_2)).setOnClickListener(this);
		((RelativeLayout)view.findViewById(R.id.rl_op_3)).setOnClickListener(this);
		((RelativeLayout)view.findViewById(R.id.rl_op_4)).setOnClickListener(this);
	}
	private void initData() {
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_op_1://网络通讯录
			ToastUtil.showShortToast(mContext.getResources().getString(R.string.change_password));
			break;
		case R.id.rl_op_2://设备管理
			ToastUtil.showShortToast(mContext.getResources().getString(R.string.equipment_management));
			break;
		case R.id.rl_op_3://免打扰设置
			ToastUtil.showShortToast(mContext.getResources().getString(R.string.free_setting));
			break;
		case R.id.rl_op_4://黑名单
			ToastUtil.showShortToast(mContext.getResources().getString(R.string.blacklist));
			break;
		}
		
	}
}
