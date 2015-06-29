package com.fhit.ycall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.adapter.SearchAdapter;
import com.fhit.ycall.customview.XListView;
import com.fhit.ycall.entity.Enterprise;
import com.fhit.ycall.http.BaseApiClient;
import com.fhit.ycall.http.HttpResponseResult;
import com.fhit.ycall.util.ToastUtil;
import com.google.gson.Gson;

public class SearchActivity extends BaseActivity {

	public static boolean isStar = false;
	private static final int GET_SEARCH = 0x101;
	public static final int GET_STAR = 0x102;
	public static final int GET_UNSTAR = 0x103;
	private EditText etInputSearch;
	private TextView tvNoSearchFlag;
	private XListView mXListView;
	private SearchAdapter adapter;
	private Handler handler = new BaseHandler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case GET_SEARCH:
				dismissDialog();
				HttpResponseResult hrr = (HttpResponseResult)msg.obj;
				if(hrr != null){
					if(hrr.getStatusCode() == BaseApiClient.HttpStatus_OK){
						Enterprise[] ens = new Gson().fromJson(hrr.getResponseBody(), Enterprise[].class);
						if(adapter.setList(ens)){
							tvNoSearchFlag.setVisibility(View.GONE);
							mXListView.setVisibility(View.VISIBLE);
						}else{
							ToastUtil.showLongToast(getResourceString(R.string.toast_no_search_result));
						}
					}else{
						ToastUtil.showLongToast(hrr.getErrorMsg());
					}
				}
				break;
			case GET_STAR:
				HttpResponseResult star = (HttpResponseResult)msg.obj;
				if(star != null){
					if(star.getStatusCode() == BaseApiClient.HttpStatus_OK){
						isStar = true;
						adapter.notifyDataSetChanged(); 
						ToastUtil.showLongToast(getResourceString(R.string.toast_star_ok));
					}else{
						ToastUtil.showLongToast(star.getErrorMsg());
					}
				}
				break;
			case GET_UNSTAR:
				HttpResponseResult unStar = (HttpResponseResult)msg.obj;
				if(unStar != null){
					if(unStar.getStatusCode() == BaseApiClient.HttpStatus_OK){
						isStar = false;
						adapter.notifyDataSetChanged(); 
						ToastUtil.showLongToast(getResourceString(R.string.toast_unstar_ok));
					}else{
						ToastUtil.showLongToast(unStar.getErrorMsg());
					}
				}
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
		addListener();
		initData();
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
		tvNoSearchFlag = (TextView)findViewById(R.id.no_search_flag);
		mXListView = (XListView)findViewById(R.id.xlistview); 
		tvNoSearchFlag.setVisibility(View.VISIBLE);
		mXListView.setVisibility(View.VISIBLE);
		mXListView.setPullLoadEnable(false);
		mXListView.setPullRefreshEnable(false);
	}
	private void initData(){
		adapter = new SearchAdapter(this, null);
		adapter.setmBinder(mHttpBinder);
		mXListView.setAdapter(adapter);
	}
	private void addListener(){
		((Button)findViewById(R.id.top_right_btn)).setOnClickListener(searchListener);
		mXListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Enterprise en = adapter.getItem(position - 1);
				goToWeb(en.getHomePage(), getResourceString(R.string.details),WebActivity.FROM_ENTERPRISE);
			}
		});
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
	public void star(int starId){
		mHttpBinder.star(handler, GET_STAR, starId);
	}
	public void unStar(int unstarId){
		mHttpBinder.star(handler, GET_UNSTAR, unstarId);
	}
}
















