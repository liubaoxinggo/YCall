package com.fhit.ycall.util;

import android.content.Context;
import android.util.DisplayMetrics;
/**
 * 屏幕大小相关属性   px与dp
 * @author liubaoxing
 *
 */
public class ScreenUtils {

	public ScreenUtils() {
		 throw new AssertionError("ScreenUtils不能实例化，它下面都是静态方法");
	}
	public static float dpToPx(Context context,float dp){
		if(context == null)return -1;
		return dp * context.getResources().getDisplayMetrics().density;
	}
	public static float pxToDp(Context context,float px){
		if(context == null)return -1;
		return px / context.getResources().getDisplayMetrics().density;
	}
	public static int dpToPxInt(Context context,float dp){
		if(context == null)return -1;
		return (int)(dpToPx(context, dp) + 0.5f);
	}
	public static int pxToDpInt(Context context,float px){
		if(context == null)return -1;
		return (int)(pxToDp(context, px) + 0.5f);
	}
	public static void getScreenSize(Context context){
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		System.out.println("density = "+dm.density);
		System.out.println("scaledDensity = "+dm.scaledDensity);
		System.out.println("densityDpi = "+dm.densityDpi);
		System.out.println("heightPixels = "+dm.heightPixels);
		System.out.println("widthPixels = "+dm.widthPixels);
		System.out.println("xdpi = "+dm.xdpi);
		System.out.println("ydpi = "+dm.ydpi);
		System.out.println("DENSITY_MEDIUM = "+DisplayMetrics.DENSITY_MEDIUM);
		System.out.println("DENSITY_HIGH = "+DisplayMetrics.DENSITY_HIGH);
		System.out.println("DENSITY_XHIGH = "+DisplayMetrics.DENSITY_XHIGH);
		System.out.println("DENSITY_XXHIGH = "+DisplayMetrics.DENSITY_XXHIGH);
		System.out.println("DENSITY_XXXHIGH = "+DisplayMetrics.DENSITY_XXXHIGH);
	} 
}













