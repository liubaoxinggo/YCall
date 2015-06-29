package com.fhit.ycall.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fhit.ycall.R;
/**
 * 可自定义圆角大小的ImageView
 * @author liubaoxing
 *
 */
@SuppressLint("DrawAllocation") public class CornerImageView extends ImageView {

	private float corner;
	private Path path;
	public CornerImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		corner = 0;
	}

	public CornerImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public CornerImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}
	private void init(AttributeSet attrs){
		TypedArray t = getContext().obtainStyledAttributes(attrs, R.styleable.CornerImageView);
		corner = t.getDimension(R.styleable.CornerImageView_corner, 0);
		t.recycle();
	}
	@SuppressLint("DrawAllocation") @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = this.getWidth();
		int h = this.getHeight();
		path = new Path();
		path.addRoundRect(new RectF(0, 0, w, h), corner, corner, Direction.CCW);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.clipPath(path);
		super.onDraw(canvas);
	}
}
















