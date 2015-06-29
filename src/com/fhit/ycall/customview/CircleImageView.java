package com.fhit.ycall.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.ImageView;
/**
 * Ô²ÐÎÏÔÊ¾ImageView
 * @author liubaoxing
 *
 */
public class CircleImageView extends ImageView {

	Path path;
	public CircleImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int w = this.getWidth();
		int h = this.getHeight();
		int r = w > h ? h / 2 : w / 2;
		path = new Path();
		path.addCircle(w / 2, h / 2, r, Direction.CCW);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.clipPath(path, Region.Op.INTERSECT);
		super.onDraw(canvas);
	}
}



























