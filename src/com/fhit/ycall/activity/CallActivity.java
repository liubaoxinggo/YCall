package com.fhit.ycall.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.customview.FloatingLayer;
import com.fhit.ycall.customview.WaterWaveView;
import com.fhit.ycall.util.FunctionUtil;
import com.fhit.ycall.util.LogUtil;

public class CallActivity extends BaseActivity implements OnClickListener,SensorEventListener{

	private WaterWaveView wwv;//²¨ÎÆÐ§¹û
	private ImageView iv_photo;
	private ImageView lianxian;
	private TextView tv_name;
	private TextView tv_state;
	private TextView tvSystemInfo;
	private AnimationDrawable animLianxian;
	private ImageView ivMt;
	private FloatingLayer mfFloatingLayer;
	private SensorManager mSenserManager;
	private Sensor mProximitySensor;
	private int telephonyState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call);
		mSenserManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mProximitySensor = mSenserManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		initView();
	}
	private void initView(){
		wwv = (WaterWaveView)findViewById(R.id.water_wave_view);
		iv_photo = (ImageView)findViewById(R.id.call_photo_ic);
		tv_name = (TextView)findViewById(R.id.call_name_tv);
		tv_state = (TextView)findViewById(R.id.call_state_tv);
		lianxian = (ImageView)findViewById(R.id.call_state_iv);
		tvSystemInfo = (TextView)findViewById(R.id.system_info);
		mfFloatingLayer = (FloatingLayer)findViewById(R.id.floating_layer);
		mfFloatingLayer.setBackgroundResource(R.color.c_00000000);
		animLianxian = (AnimationDrawable)lianxian.getBackground();
		animLianxian.start();
		ivMt = (ImageView)findViewById(R.id.call_mt_iv);
		ivMt.setOnClickListener(this);
		((ImageView)findViewById(R.id.call_gj_iv)).setOnClickListener(this);
		
	}
	private void setMtImage(){
		LogUtil.i("ycall", this.getLocalClassName()+"-ÑïÉùÆ÷isOpen = "+FunctionUtil.getSpeakerState(this));
		if(FunctionUtil.getSpeakerState(this)){
			ivMt.setImageResource(R.drawable.call_mt_close_selector);
		}else{
			ivMt.setImageResource(R.drawable.call_mt_selector);
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setMtImage();
		mSenserManager.registerListener(this, mProximitySensor,SensorManager.SENSOR_DELAY_NORMAL);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSenserManager.unregisterListener(this);
	}
	@Override
	protected void onDestroy() {
		animLianxian.stop();
		super.onDestroy();
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		int r = iv_photo.getWidth() / 2;
		wwv.setFillWaveSourceShapeRadius(r - 30);
		super.onWindowFocusChanged(hasFocus);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.call_mt_iv:
			LogUtil.i("ycall", this.getLocalClassName()+"-onClick-Ç°-ÑïÉùÆ÷isOpen = "+FunctionUtil.getSpeakerState(this));
			if(FunctionUtil.getSpeakerState(this)){
				FunctionUtil.closeSpeaker(this);
				ivMt.setImageResource(R.drawable.call_mt_selector);
			}else{
				FunctionUtil.openSpeaker(this);
				ivMt.setImageResource(R.drawable.call_mt_close_selector);
			}
			LogUtil.i("ycall", this.getLocalClassName()+"-onClick-ºó-ÑïÉùÆ÷isOpen = "+FunctionUtil.getSpeakerState(this));
			break;
		case R.id.call_gj_iv:
			handleEndCall();
			break;

		default:
			break;
		}
	}
	private void handleEndCall() {
		try {
			FunctionUtil.endCall(getBaseContext());
			if(telephonyState == TelephonyManager.CALL_STATE_OFFHOOK){
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finish();
	}
	@SuppressLint("NewApi") @Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
			float distance = event.values[0];
			if(distance >= 0 && distance < mProximitySensor.getMaximumRange()){// 
				 mfFloatingLayer.setVisibility(View.VISIBLE);
			}else{
				mfFloatingLayer.setVisibility(View.GONE);
			}
			LogUtil.iSave("ycall", "----CallActivity---onSensorChanged---proximity = "+event.values[0]
					+" / getMaximumRange = "+mProximitySensor.getMaximumRange()+"\n"
					+"getName = "+mProximitySensor.getName()+"\n"
					+"getVersion = "+mProximitySensor.getVersion()+"\n"
					+"getVendor = "+mProximitySensor.getVendor()+"\n"
					+"getMinDelay = "+mProximitySensor.getMinDelay()+"\n"
					+"getPower = "+mProximitySensor.getPower()+"\n"
					+"getResolution = "+mProximitySensor.getResolution()+"\n"
					+"getFifoMaxEventCount = "+mProximitySensor.getFifoMaxEventCount()+"\n"
					+"getFifoReservedEventCount = "+mProximitySensor.getFifoReservedEventCount()+"\n");
		}
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	 
	
}
