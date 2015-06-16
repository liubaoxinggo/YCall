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

@SuppressLint("ResourceAsColor") public class RelationshipFragment extends Fragment {

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
		TextView view = new TextView(mContext);
		view.setTextSize(108);
		view.setTextColor(R.color.red);
		view.setText(mContext.getResources().getString(R.string.relationship));
		return view;
	}
}
