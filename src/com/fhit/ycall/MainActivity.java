package com.fhit.ycall;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhit.ycall.adapter.TabPagerAdapter;
import com.fhit.ycall.customview.MViewPager;
import com.fhit.ycall.fragment.DiscoveryFragment;
import com.fhit.ycall.fragment.FavFragment;
import com.fhit.ycall.fragment.HistoryBaseFragment;
import com.fhit.ycall.fragment.HistoryFragment;
import com.fhit.ycall.fragment.MeFragment;
import com.fhit.ycall.fragment.RelationshipFragment;
import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.ScreenUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private static final int INDEX_FAV = 0;
	private static final int INDEX_RELATIONSHIP = 1;
	private static final int INDEX_KEYBOARD = 2;
	private static final int INDEX_DICOVERY = 3;
	private static final int INDEX_ME = 4;
	
	/**
	 * 键盘和键盘Tab的动画的持续时间
	 */
	public static final long KEYBOARD_ANIM_DURATION = 500;
	/**
	 * 当currentIndex!=INDEX_KEYBOARD时,这是一种缺省状态
	 */
	public static final int KEYBOARD_STATE_COMM = 0;
	/**
	 * 当currentIndex==INDEX_KEYBOARD时，键盘处于【隐藏】状态，且键盘输入框内【无】数字内容
	 */
	public static final int KEYBOARD_STATE_NO_INPUT_HIDE = 1;
	/**
	 * 当currentIndex==INDEX_KEYBOARD时，键盘处于【显示】状态，且键盘输入框内【无】数字内容
	 */
	public static final int KEYBOARD_STATE_NO_INPUT_SHOW = 2;
	/**
	 * 当currentIndex==INDEX_KEYBOARD时，键盘处于【显示】状态，且键盘输入框内【有】数字内容
	 */
	public static final int KEYBOARD_STATE_CALL_SHOW = 3;
	/**
	 * 当currentIndex==INDEX_KEYBOARD时，键盘处于【隐藏】状态，且键盘输入框内【有】数字内容
	 */
	public static final int KEYBOARD_STATE_CALL_HIDE = 4;
	
	/**
	 * 当前键盘tab的状态
	 */
	private int currentKeyboardState = KEYBOARD_STATE_NO_INPUT_SHOW;
	//当前keyboard的旋转的角度
	private float currentKeyboardRotationX = 0;
	
	private int currentIndex = INDEX_KEYBOARD;
	//顶部相关控件
	private TextView tvMsgCount;
	private TextView netState;
	//中间内容的相关的引用
	private List<Fragment> fragments;
	private Fragment fav,relationship,discovery,me;
	private HistoryBaseFragment history;
	private MViewPager mViewPager;
	private TabPagerAdapter adapter;
	//底部tab的相关控件
	private ImageView ivFav,ivRelationship,ivDiscovery,ivMe,ivKeyboard;
	private TextView tvFav,tvRelationship,tvDiscovery,tvMe;
	private LinearLayout llFav,llRelationship,llDiscovery,llMe,llBottom;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) { 
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	initView();
    	addListener();
    	initData();
    }
 
    /**
     * 初始化控件
     */
    private void initView(){
    	tvMsgCount = (TextView)findViewById(R.id.top_right_tv);
    	netState = (TextView)findViewById(R.id.net_state);
    	
    	llBottom = (LinearLayout)findViewById(R.id.bottom_layout);
    	llFav = (LinearLayout)findViewById(R.id.tab_1);
    	
    	
    	ivFav = (ImageView)findViewById(R.id.tab_iv_1);
    	ivRelationship = (ImageView)findViewById(R.id.tab_iv_2);
    	ivDiscovery = (ImageView)findViewById(R.id.tab_iv_3);
    	ivMe = (ImageView)findViewById(R.id.tab_iv_4);
    	ivKeyboard = (ImageView)findViewById(R.id.tab_iv_keyboard);
    	
    	tvFav = (TextView)findViewById(R.id.tab_tv_1);
    	tvRelationship = (TextView)findViewById(R.id.tab_tv_2);
    	tvDiscovery = (TextView)findViewById(R.id.tab_tv_3);
    	tvMe = (TextView)findViewById(R.id.tab_tv_4);
    	
    	mViewPager = (MViewPager) findViewById(R.id.content_viewpager);
    	//可以控制MViewPager的左右滑动
    	mViewPager.setScanScroll(false);
    }
    /**
     * 添加监听器
     */
    private void addListener(){
    	((LinearLayout)findViewById(R.id.tab_1)).setOnClickListener(this);
    	((LinearLayout)findViewById(R.id.tab_2)).setOnClickListener(this);
    	((LinearLayout)findViewById(R.id.tab_3)).setOnClickListener(this);
    	((LinearLayout)findViewById(R.id.tab_4)).setOnClickListener(this);
    	((RelativeLayout)findViewById(R.id.top_right_layout_1)).setOnClickListener(this);
    	((ImageView)findViewById(R.id.top_right_search)).setOnClickListener(this);
    	
    	ivKeyboard.setOnClickListener(this);
    	
    	mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    /**
     * 初始化数据
     */
    private void initData(){
    	//初始化5个fragment
    	fav = new FavFragment();
    	relationship = new RelationshipFragment();
    	history = new HistoryFragment();
    	discovery = new DiscoveryFragment();
    	me = new MeFragment();
    	//将初始化的5个fragment加入list集合
    	fragments = new ArrayList<Fragment>();
    	fragments.add(fav);
    	fragments.add(relationship);
    	fragments.add(history);
    	fragments.add(discovery);
    	fragments.add(me);
    	//初始化adapter
        adapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
      	mViewPager.setAdapter(adapter);
      	mViewPager.setCurrentItem(currentIndex); 
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tab_1:
			setCurrentTab(INDEX_FAV);
			break;
		case R.id.tab_2:
			setCurrentTab(INDEX_RELATIONSHIP);
			break;
		case R.id.tab_iv_keyboard:
			handleClickKeyboardTab();
			break;
		case R.id.tab_3:
			setCurrentTab(INDEX_DICOVERY);
			break;
		case R.id.tab_4:
			setCurrentTab(INDEX_ME);
			break;
		case R.id.top_right_layout_1://右上角 msg
			 
			break;
		case R.id.top_right_search://右上角 search
			
			break;
		default:
			break;
		}
	}
	private void handleClickKeyboardTab(){
		if(currentIndex == INDEX_KEYBOARD){
			LogUtil.i("infos", "MainActivity: keyboardState[3] = "+ currentKeyboardState);
			switch (currentKeyboardState) {
			case KEYBOARD_STATE_COMM://0
				break;
			case KEYBOARD_STATE_NO_INPUT_HIDE://1[由1->2]
				currentKeyboardState = KEYBOARD_STATE_NO_INPUT_SHOW;
//				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
				ObjectAnimator oa2 = ObjectAnimator.ofFloat(ivKeyboard, "rotationX", 180f,0f).setDuration(KEYBOARD_ANIM_DURATION);
				oa2.addListener(new AnimatorListener() {
					@Override
					public void onAnimationStart(Animator arg0) {
					}
					@Override
					public void onAnimationRepeat(Animator arg0) {
					}
					@Override
					public void onAnimationEnd(Animator arg0) {
						ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
					}
					@Override
					public void onAnimationCancel(Animator arg0) {
					}
				});
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
				oa2.start();
				history.showKeyboard(KEYBOARD_ANIM_DURATION);
				break;
			case KEYBOARD_STATE_NO_INPUT_SHOW://2[由2->1]
				currentKeyboardState = KEYBOARD_STATE_NO_INPUT_HIDE;
//				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
				ObjectAnimator oa1 = ObjectAnimator.ofFloat(ivKeyboard, "rotationX", -180f,0f).setDuration(KEYBOARD_ANIM_DURATION);
				oa1.addListener(new AnimatorListener() {
					@Override
					public void onAnimationStart(Animator arg0) {
					}
					@Override
					public void onAnimationRepeat(Animator arg0) {
					}
					@Override
					public void onAnimationEnd(Animator arg0) {
						ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
					}
					@Override
					public void onAnimationCancel(Animator arg0) {
					}
				});
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
				oa1.start();
				history.hideKeyboard(KEYBOARD_ANIM_DURATION); 
				break;
			case KEYBOARD_STATE_CALL_HIDE://4[由4->3]
				currentKeyboardState = KEYBOARD_STATE_CALL_SHOW;
				history.showKeyboard(KEYBOARD_ANIM_DURATION);
				ObjectAnimator oa = ObjectAnimator.ofFloat(ivKeyboard, "rotationX", 180f,0f).setDuration(KEYBOARD_ANIM_DURATION);
				oa.addUpdateListener(new AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						// TODO Auto-generated method stub
						Log.i("anim", "CurrentPlayTime="+animation.getCurrentPlayTime()+" / Duration="+animation.getDuration()+" / AnimatedValue="+animation.getAnimatedValue()); 
						if((Float)animation.getAnimatedValue() < 90f){
							ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
						}
					}
				});
				oa.start();
				break;
			case KEYBOARD_STATE_CALL_SHOW://3//呼出
				history.call();
				break;
			default:
				break;
			}
			LogUtil.i("infos", "MainActivity: currentKeyboardState = "+ currentKeyboardState);
		}else{//从其他tab 切换到 keyboard时，应该恢复原来的状态
			setCurrentTab(INDEX_KEYBOARD);
			setKeyboardState(currentKeyboardState);
		}
	}
	/**
	 * 设置keyTab的图片
	 * @param state
	 */
	public void setKeyboardState(int state){
		LogUtil.i("infos", "MainActivity: currentKeyboardState[2] = "+ currentKeyboardState+"--->"+state);
		switch (state) {
		case KEYBOARD_STATE_COMM://0
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		case KEYBOARD_STATE_NO_INPUT_HIDE://1
			if(currentKeyboardState == KEYBOARD_STATE_NO_INPUT_SHOW){//由2->1
				ObjectAnimator oa1 = ObjectAnimator.ofFloat(ivKeyboard, "rotationX", -180f,0f).setDuration(KEYBOARD_ANIM_DURATION);
				oa1.addListener(new AnimatorListener() {
					@Override
					public void onAnimationStart(Animator arg0) {
					}
					@Override
					public void onAnimationRepeat(Animator arg0) {
					}
					@Override
					public void onAnimationEnd(Animator arg0) {
						ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
					}
					@Override
					public void onAnimationCancel(Animator arg0) {
					}
				});
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
				oa1.start();
				history.hideKeyboard(KEYBOARD_ANIM_DURATION); 
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
			}
			break;
		case KEYBOARD_STATE_NO_INPUT_SHOW://2
			if(currentKeyboardState == KEYBOARD_STATE_CALL_SHOW){//由3->2
				ObjectAnimator oa = ObjectAnimator.ofFloat(ivKeyboard, "alpha", 1.0f,0.1f,01f,1f).setDuration(KEYBOARD_ANIM_DURATION);
				oa.addUpdateListener(new AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						if((Float)animation.getAnimatedValue() < 0.2f){
							ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
						}
						
					}
				});
				oa.start();
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
			}
			break;
		case KEYBOARD_STATE_CALL_SHOW://3
			if(currentKeyboardState == KEYBOARD_STATE_NO_INPUT_SHOW){//由2->3
				ObjectAnimator oa = ObjectAnimator.ofFloat(ivKeyboard, "alpha", 1.0f,0.1f,01f,1f).setDuration(KEYBOARD_ANIM_DURATION);
				oa.addUpdateListener(new AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						if((Float)animation.getAnimatedValue() < 0.2f){
							ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
						}
						
					}
				});
				oa.start();
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
			}
			break;
		case KEYBOARD_STATE_CALL_HIDE://4
			if(currentKeyboardState == KEYBOARD_STATE_CALL_SHOW){//由3->4
				ObjectAnimator oa = ObjectAnimator.ofFloat(ivKeyboard, "rotationX", -180f,0f).setDuration(KEYBOARD_ANIM_DURATION);
				oa.addUpdateListener(new AnimatorUpdateListener() {
					
					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						// TODO Auto-generated method stub
//						Log.i("anim", "CurrentPlayTime="+animation.getCurrentPlayTime()+" / Duration="+animation.getDuration()+" / AnimatedValue="+animation.getAnimatedValue()); 
						if((Float)animation.getAnimatedValue() > -90f){
							ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
						}
					}
				});
				oa.start();
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
			}
			break;
		default:
			break;
		}
		currentKeyboardState = state;
	}
	 
	@SuppressLint("ResourceAsColor")
	private void setCurrentTab(int cIndex){
//		LogUtil.i("infos", "MainActivity: keyboardState[1] = "+ currentKeyboardState);
		currentIndex = cIndex;
		mViewPager.setCurrentItem(currentIndex);
		switch (cIndex) {
		case INDEX_FAV:
			test();
			tvFav.setTextColor(getResources().getColor(R.color.tab_tv_selected));
			tvRelationship.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvDiscovery.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvMe.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			ivFav.setImageResource(R.drawable.tab_iv1_pressed);
			ivRelationship.setImageResource(R.drawable.tab_iv2_normal);
			ivDiscovery.setImageResource(R.drawable.tab_iv3_normal);
			ivMe.setImageResource(R.drawable.tab_iv4_normal);
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		case INDEX_RELATIONSHIP:
			tvFav.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvRelationship.setTextColor(getResources().getColor(R.color.tab_tv_selected));
			tvDiscovery.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvMe.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			ivFav.setImageResource(R.drawable.tab_iv1_normal);
			ivRelationship.setImageResource(R.drawable.tab_iv2_pressed);
			ivDiscovery.setImageResource(R.drawable.tab_iv3_normal);
			ivMe.setImageResource(R.drawable.tab_iv4_normal);
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		case INDEX_KEYBOARD:
			tvFav.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvRelationship.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvDiscovery.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvMe.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			ivFav.setImageResource(R.drawable.tab_iv1_normal);
			ivRelationship.setImageResource(R.drawable.tab_iv2_normal);
			ivDiscovery.setImageResource(R.drawable.tab_iv3_normal);
			ivMe.setImageResource(R.drawable.tab_iv4_normal);
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
			break;
		case INDEX_DICOVERY:
			tvFav.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvRelationship.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvDiscovery.setTextColor(getResources().getColor(R.color.tab_tv_selected));
			tvMe.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			ivFav.setImageResource(R.drawable.tab_iv1_normal);
			ivRelationship.setImageResource(R.drawable.tab_iv2_normal);
			ivDiscovery.setImageResource(R.drawable.tab_iv3_pressed);
			ivMe.setImageResource(R.drawable.tab_iv4_normal);
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		case INDEX_ME:
			tvFav.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvRelationship.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvDiscovery.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvMe.setTextColor(getResources().getColor(R.color.tab_tv_selected));
			ivFav.setImageResource(R.drawable.tab_iv1_normal);
			ivRelationship.setImageResource(R.drawable.tab_iv2_normal);
			ivDiscovery.setImageResource(R.drawable.tab_iv3_normal);
			ivMe.setImageResource(R.drawable.tab_iv4_pressed);
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		default:
			break;
		}
	}
	AnimatorListener showAnimatorListener = new AnimatorListener() {
		@Override
		public void onAnimationStart(Animator arg0) {
			
		}
		
		@Override
		public void onAnimationRepeat(Animator arg0) {
		}
		
		@Override
		public void onAnimationEnd(Animator arg0) {
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
		}
		
		@Override
		public void onAnimationCancel(Animator arg0) {
			
		}
	};
	private class MyOnPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int index) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageSelected(int index) {
//			setCurrentTab(index);
		}
	} 
	
	void test(){
//		ScreenUtils.getScreenSize(this);
		System.out.println("60dp<----->"+ScreenUtils.dpToPx(this, 60)+"px");
		System.out.println("60px<----->"+ScreenUtils.pxToDp(this, 60)+"dp");
		System.out.println("ivFav w = "+ivFav.getWidth()+" / h = "+ivFav.getHeight());
		System.out.println("llBottom w = "+llBottom.getWidth()+" / h = "+llBottom.getHeight());
		System.out.println("llFav w = "+llFav.getWidth()+" / h = "+llFav.getHeight());
	}
}























