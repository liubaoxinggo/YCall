package com.fhit.ycall.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;

public class SearchActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		initView();
	}
	private void initView(){
		((TextView)findViewById(R.id.center_input_et)).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setVisibility(View.INVISIBLE);
		((Button)findViewById(R.id.top_right_btn)).setVisibility(View.VISIBLE);
		((Button)findViewById(R.id.top_right_btn)).setText(getResources().getString(R.string.top_search));
		((ImageView)findViewById(R.id.left_back_ic)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		((Button)findViewById(R.id.top_right_btn)).setOnClickListener(searchListener);
	}
	private OnClickListener searchListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
}
















