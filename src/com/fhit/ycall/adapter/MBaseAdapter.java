package com.fhit.ycall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MBaseAdapter<T> extends BaseAdapter {
	
	protected Context context;
	protected LayoutInflater inflater;
	protected ArrayList<T> list;
	
	public MBaseAdapter(Context context, ArrayList<T> list) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	public void setList(ArrayList<T> list) {
		if(list != null){
			this.list = list;
		}else{
			this.list = new ArrayList<T>();
		}
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
