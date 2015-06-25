package com.fhit.ycall.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.util.ToastUtil;

public class RegisterActivity extends BaseActivity {

	//узуж╡Ц
	private View floatingLayer;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		initView();
	}
	private void initView() {
		((TextView)findViewById(R.id.center_input_et)).setVisibility(View.INVISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.left_title_tv)).setText(R.string.register_title);
		((ImageView)findViewById(R.id.left_back_ic)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		floatingLayer = (View)findViewById(R.id.floating_layer);
		((Button)findViewById(R.id.register_btn)).setOnClickListener(goToRegisterListener);
		((Button)findViewById(R.id.register_get_sms_verification_code)).setOnClickListener(getSmsVrifyCodeListener);
		((Button)findViewById(R.id.cancle)).setOnClickListener(cancleListener);
		((Button)findViewById(R.id.confirm)).setOnClickListener(okListener);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(floatingLayer.getVisibility() == View.GONE){
			super.onBackPressed();
		}else{
			floatingLayer.setVisibility(View.GONE);
		}
	}
	private  OnClickListener goToRegisterListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ToastUtil.showLongToast(getResources().getString(R.string.register));
			floatingLayer.setVisibility(View.VISIBLE);
			 
		}
	};
	private  OnClickListener getSmsVrifyCodeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ToastUtil.showLongToast(getResources().getString(R.string.submit));
			floatingLayer.setVisibility(View.VISIBLE);
			 
		}
	};
	private  OnClickListener okListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			floatingLayer.setVisibility(View.GONE);
			 
		}
	};
	private  OnClickListener cancleListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			floatingLayer.setVisibility(View.GONE);
			 
		}
	};
}
