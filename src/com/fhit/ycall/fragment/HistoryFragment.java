package com.fhit.ycall.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fhit.ycall.R;
import com.fhit.ycall.activity.CallActivity;
import com.fhit.ycall.activity.MainActivity;
import com.fhit.ycall.util.DTMFUtil;
import com.fhit.ycall.util.FunctionUtil;
import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.ToastUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

@SuppressLint("ResourceAsColor") 
public class HistoryFragment extends HistoryBaseFragment implements OnClickListener,OnTouchListener{

	/**
	 * ��������볤��
	 */
	private static final int INPUT_PHONE_NUMBER_LENGTH = 20;
	private LinearLayout keyboard;
	private EditText etPhoneNumber;
	private MainActivity mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = (MainActivity) this.getActivity();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.history, container, false);
		initView(view);
		addListener(view);
		initData();
		//����ϵͳ����
		FunctionUtil.hideSystemKeyboard(mContext, etPhoneNumber);
		return view;
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		LogUtil.i("infos", "HistoryFragment--onStart()");
		//��ʼ��DTMF
		DTMFUtil.getInstance().init(mContext);
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		LogUtil.i("infos", "HistoryFragment--onPause()");
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		LogUtil.i("infos", "HistoryFragment--onStop()");
		DTMFUtil.getInstance().release();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.i("infos", "HistoryFragment--onDestroy()");
	}
	private void initView(View v){
		keyboard  = (LinearLayout)v.findViewById(R.id.keyboard_layout);
		etPhoneNumber = (EditText)v.findViewById(R.id.input_phone_number_et);
	}
	private void addListener(View v){
		for (int i = 0; i < 12; i++) {
			View iv = v.findViewById(R.id.dialNum1 + i);
			iv.setOnClickListener(this);
			iv.setOnTouchListener(this);
		}
		((Button)v.findViewById(R.id.dialb)).setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				etPhoneNumber.setText("");
				return true;
			}
		}); 
		etPhoneNumber.addTextChangedListener(textWatcher);
	}
	private void initData(){
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dialNum0:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(1);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum1:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(1);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum2:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(2);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum3:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(3);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum4:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(4);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum5:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(5);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum6:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(6);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum7:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(7);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum8:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(8);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialNum9:
			if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(9);
				input(v.getTag().toString());
			}
			break;
		case R.id.dialx:// - [����]
			/*if (etPhoneNumber.getText().length() < INPUT_PHONE_NUMBER_LENGTH) {
				play(11);
				input(v.getTag().toString());
			}*/
//			keyboard.setVisibility(View.GONE);
			//
			if(TextUtils.isEmpty(etPhoneNumber.getText().toString())){//��2->1
				setKeyboardState(MainActivity.KEYBOARD_STATE_NO_INPUT_HIDE);
			}else{//��3->4
				setKeyboardState(MainActivity.KEYBOARD_STATE_CALL_HIDE);
			}
			break;
		case R.id.dialb://ɾ����  backspace
//			play(12);
			delete();
			break;
		default:
			break;
		}
	}
	private void input(String str) {
		int c = etPhoneNumber.getSelectionStart();
		String p = etPhoneNumber.getText().toString();
		etPhoneNumber.setText(p.substring(0, c) + str + p.substring(etPhoneNumber.getSelectionStart(), p.length()));
		etPhoneNumber.setSelection(c + 1, c + 1);
	}
	/**
	 * �������� �� ��
	 * @param i
	 */
	private void play(int i) {
		DTMFUtil.getInstance().play(i);
	}
	private void delete() {
		int c = etPhoneNumber.getSelectionStart();
		if (c > 0) {
			String p = etPhoneNumber.getText().toString();
			etPhoneNumber.setText(p.substring(0, c - 1) + p.substring(etPhoneNumber.getSelectionStart(), p.length()));
			etPhoneNumber.setSelection(c - 1, c - 1);
		}else if(c == 0){
			etPhoneNumber.setText("");
		}
	}
	@Override
	public boolean onTouch(View v, MotionEvent e) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialNum0:
		case R.id.dialNum1:
		case R.id.dialNum2:
		case R.id.dialNum3:
		case R.id.dialNum4:
		case R.id.dialNum5:
		case R.id.dialNum6:
		case R.id.dialNum7:
		case R.id.dialNum8:
		case R.id.dialNum9:
		case R.id.dialx:
		case R.id.dialb:
			if(e.getAction() == MotionEvent.ACTION_DOWN){
				FunctionUtil.vibrate(mContext,15);
//				FunctionUtil.vibrate(mContext,50);
			}
			break;
		}
		return false;
	}
	private void setKeyboardState(int state){
		mContext.setKeyboardState(state);
	}
	@Override
	public void call() {
//		ToastUtil.showLongToast("call");
		Intent callIntent = new Intent(mContext, CallActivity.class);
		mContext.startActivity(callIntent);
	}
	@Override
	public void hideKeyboard(long duration){
		ObjectAnimator oa1 = ObjectAnimator.ofFloat(keyboard, "translationY", 0f,keyboard.getHeight()).setDuration(duration);
//		oa.addListener(hideAnimatorListener);
//		oa.start();
		ObjectAnimator oa2 = ObjectAnimator.ofFloat(keyboard, "alpha", 1f,0.5f).setDuration(duration);
		AnimatorSet as = new AnimatorSet();
		as.playTogether(oa1,oa2);
		as.setTarget(keyboard);
		as.addListener(hideAnimatorListener);
		as.start();
//		keyboard.setVisibility(View.GONE);
	}
	@Override
	public void showKeyboard(long duration){
		ObjectAnimator oa1 = ObjectAnimator.ofFloat(keyboard, "translationY", keyboard.getHeight(),0f).setDuration(duration);
//		oa.addListener(showAnimatorListener);
//		oa.start();
		ObjectAnimator oa2 = ObjectAnimator.ofFloat(keyboard, "alpha", 0.5f,1f).setDuration(duration);
		AnimatorSet as = new AnimatorSet();
		as.playTogether(oa1,oa2);
		as.setTarget(keyboard);
		as.addListener(showAnimatorListener);
		as.start();
//		keyboard.setVisibility(View.VISIBLE);
	}
	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if(TextUtils.isEmpty(s)){//
				if(keyboard.getVisibility() == View.VISIBLE){
					setKeyboardState(MainActivity.KEYBOARD_STATE_NO_INPUT_SHOW);
				}else{
					setKeyboardState(MainActivity.KEYBOARD_STATE_NO_INPUT_HIDE);
				}
			}else{
				if(keyboard.getVisibility() == View.VISIBLE){
					setKeyboardState(MainActivity.KEYBOARD_STATE_CALL_SHOW);
				}else{
					setKeyboardState(MainActivity.KEYBOARD_STATE_CALL_HIDE);
				} 
			}
		}
		@Override
		public void afterTextChanged(Editable s) {
		}
	};
	private AnimatorListener hideAnimatorListener = new AnimatorListener() {
		@Override
		public void onAnimationStart(Animator arg0) {
		}
		
		@Override
		public void onAnimationRepeat(Animator arg0) {
		}
		
		@Override
		public void onAnimationEnd(Animator arg0) {
			keyboard.setVisibility(View.GONE);
		}
		
		@Override
		public void onAnimationCancel(Animator arg0) {
			
		}
	};
	private AnimatorListener showAnimatorListener = new AnimatorListener() {
		@Override
		public void onAnimationStart(Animator arg0) {
			keyboard.setVisibility(View.VISIBLE);
		}
		
		@Override
		public void onAnimationRepeat(Animator arg0) {
		}
		
		@Override
		public void onAnimationEnd(Animator arg0) {
		}
		
		@Override
		public void onAnimationCancel(Animator arg0) {
			
		}
	};
}













