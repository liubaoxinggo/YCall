package com.fhit.ycall.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.fhit.ycall.R;
import com.nineoldandroids.animation.ObjectAnimator;

public class DialogUtil {

	public static Dialog createProgressDilog(Context context,String msg){
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_progress_dialog, null);
		ImageView spaceshipIamge  =(ImageView)v.findViewById(R.id.loadingImageView);
		TextView tipTextView = (TextView)v.findViewById(R.id.id_tv_loadingmsg);
		ObjectAnimator rotation  = ObjectAnimator.ofFloat(spaceshipIamge, "rotation", 0f,-360f).setDuration(400);
		rotation.setRepeatCount(Animation.INFINITE);
		rotation.setRepeatMode(Animation.RESTART);
		rotation.setInterpolator(new LinearInterpolator());
		rotation.start();
		if(TextUtils.isEmpty(msg)){
			tipTextView.setVisibility(View.GONE);
		}else{
			tipTextView.setText(msg);
		}
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		loadingDialog.setContentView(v, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return loadingDialog;
	}
}
