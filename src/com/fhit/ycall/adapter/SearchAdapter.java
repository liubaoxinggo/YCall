package com.fhit.ycall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhit.ycall.R;
import com.fhit.ycall.entity.Enterprise;

public class SearchAdapter extends MBaseAdapter<Enterprise> {

	public SearchAdapter(Context context, ArrayList<Enterprise> list) {
		super(context, list);
	}
	class HolderView{
		ImageView ivPhoto;
		TextView tvName;
		TextView tvAttention;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		HolderView holder = null;
		if(convertView == null){
			holder = new HolderView();
			convertView = inflater.inflate(R.layout.search_item, null);
			holder.ivPhoto = (ImageView)convertView.findViewById(R.id.logo_ic);
			holder.tvName = (TextView)convertView.findViewById(R.id.item_name);
			holder.tvAttention = (TextView)convertView.findViewById(R.id.right_btn);
			convertView.setTag(holder);
		}else{
			holder = (HolderView)convertView.getTag();
		}
		return convertView;
	}
	
}
