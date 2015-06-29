package com.fhit.ycall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhit.ycall.R;
import com.fhit.ycall.activity.SearchActivity;
import com.fhit.ycall.entity.Enterprise;
import com.fhit.ycall.service.YCallService.HttpBinder;
import com.fhit.ycall.util.LogUtil;

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
		final Enterprise en = list.get(position);
		holder.tvName.setText(en.getName());
		if(!SearchActivity.isStar){
			holder.tvAttention.setTextColor(context.getResources().getColor(R.color.c_ffffffff));
			holder.tvAttention.setBackgroundResource(R.drawable.btn_bg_4);
			holder.tvAttention.setText(((SearchActivity)context).getResourceString(R.string.add_attention));
		}else{
			holder.tvAttention.setTextColor(context.getResources().getColor(R.color.c_999999));
			holder.tvAttention.setBackgroundResource(R.drawable.btn_bg_5);
			holder.tvAttention.setText(((SearchActivity)context).getResourceString(R.string.delete_attention));
		}
		holder.tvAttention.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!SearchActivity.isStar){
					((SearchActivity)context).star(en.getId());
				}else{
					((SearchActivity)context).unStar(en.getId());
				}
			}
		});
		return convertView;
	}
	
}
