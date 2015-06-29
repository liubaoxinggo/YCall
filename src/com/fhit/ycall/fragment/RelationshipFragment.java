package com.fhit.ycall.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fhit.ycall.R;
import com.fhit.ycall.activity.AttentionBusinessActivity;
import com.fhit.ycall.activity.FriendActivity;
import com.fhit.ycall.activity.InternetPhoneBookActivity;
import com.fhit.ycall.util.ToastUtil;

public class RelationshipFragment extends BaseFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.relationship, container, false);
		initView(view);
		addListener(view);
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
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_op_1://网络通讯录
//			ToastUtil.showShortToast(mContext.getResources().getString(R.string.internet_phone_book));
			mContext.startActivity(new Intent(mContext, InternetPhoneBookActivity.class));
			break;
		case R.id.rl_op_2://朋友
//			ToastUtil.showShortToast(mContext.getResources().getString(R.string.friend));
			mContext.startActivity(new Intent(mContext, FriendActivity.class));
			break;
		case R.id.rl_op_3://关注企业
//			ToastUtil.showShortToast(mContext.getResources().getString(R.string.attention_to_business));
			mContext.startActivity(new Intent(mContext, AttentionBusinessActivity.class));
			break;
		case R.id.rl_op_4://本地通讯录
			ToastUtil.showShortToast(mContext.getResources().getString(R.string.local_phone_book));
			break;
		}
	}
}
