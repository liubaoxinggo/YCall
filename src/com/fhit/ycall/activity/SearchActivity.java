package com.fhit.ycall.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.customview.XListView;
import com.fhit.ycall.util.ToastUtil;

public class SearchActivity extends BaseActivity {

	private static final int GET_SEARCH = 0x101;
	private EditText etInputSearch;
	private TextView tvNoSearchFlag;
	private XListView mXListView;
	private Handler handler = new BaseHandler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case GET_SEARCH:
				dismissDialog();
				tvNoSearchFlag.setVisibility(View.GONE);
				mXListView.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		bindService();
		initView();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unBindService();
	}
	private void initView(){
		((EditText)findViewById(R.id.center_input_et)).setVisibility(View.VISIBLE);
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
		etInputSearch = (EditText)findViewById(R.id.center_input_et);
		etInputSearch.setText("ю╢ак");
		tvNoSearchFlag = (TextView)findViewById(R.id.no_search_flag);
		mXListView = (XListView)findViewById(R.id.xlistview); 
		 
		((Button)findViewById(R.id.top_right_btn)).setOnClickListener(searchListener);
	}
	private OnClickListener searchListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String input = etInputSearch.getText().toString();
			if(TextUtils.isEmpty(input)){
				ToastUtil.showLongToast(getResourceString(R.string.toast_search));
			}else{
				showDialog(getResourceString(R.string.dialog_searching));
				mHttpBinder.search(handler, GET_SEARCH, input);
			}
		}
	};
}
















