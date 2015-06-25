package com.fhit.ycall.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fhit.ycall.BaseActivity;
import com.fhit.ycall.R;
import com.fhit.ycall.R.color;
import com.fhit.ycall.R.drawable;
import com.fhit.ycall.R.id;
import com.fhit.ycall.R.layout;
import com.fhit.ycall.R.string;
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
import com.fhit.ycall.util.ToastUtil;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

public class MainActivity extends BaseActivity implements OnClickListener{
	
	private static final int INDEX_FAV = 0;
	private static final int INDEX_RELATIONSHIP = 1;
	private static final int INDEX_KEYBOARD = 2;
	private static final int INDEX_DICOVERY = 3;
	private static final int INDEX_ME = 4;
	
	/**
	 * 键盘和键盘Tab的动画的持续时间
	 */
	public static final long KEYBOARD_ANIM_DURATION = 300;
	
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
	
	/**
	 * ivKeyboard_1作为ivKeyboard的副本，记录状态,使其原本ivKeyboard全程完成动画效果，而不参与状态的记录。
	 * ivKeyboard_1的显示效果要与原始ivKeyboard的显示效果保持一样，即ivKeyboard_1要与ivKeyboard动画前或动画后显示 的状态图片一样
	 */
	private ImageView ivKeyboard_1;//
	
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
    	netState.setVisibility(View.GONE);
    	
    	llBottom = (LinearLayout)findViewById(R.id.bottom_layout);
    	llFav = (LinearLayout)findViewById(R.id.tab_1);
    	
    	
    	ivFav = (ImageView)findViewById(R.id.tab_iv_1);
    	ivRelationship = (ImageView)findViewById(R.id.tab_iv_2);
    	ivDiscovery = (ImageView)findViewById(R.id.tab_iv_3);
    	ivMe = (ImageView)findViewById(R.id.tab_iv_4);
    	ivKeyboard = (ImageView)findViewById(R.id.tab_iv_keyboard);
    	ivKeyboard_1 = (ImageView)findViewById(R.id.tab_iv_keyboard_1);
    	 
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
    	ivKeyboard_1.setOnClickListener(this);
    	
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
		case R.id.tab_iv_keyboard://原
//			ToastUtil.showShortToast("原");
			handleClickKeyboardTab();
			break;
		case R.id.tab_iv_keyboard_1://副
//			ToastUtil.showShortToast("副");
			setCurrentTab(INDEX_KEYBOARD);
			break;
		case R.id.tab_3:
			setCurrentTab(INDEX_DICOVERY);
			break;
		case R.id.tab_4:
			setCurrentTab(INDEX_ME);
			break;
		case R.id.top_right_layout_1://右上角 msg
			ToastUtil.showShortToast(getResources().getString(R.string.top_msg)); 
			break;
		case R.id.top_right_search://右上角 search
//			ToastUtil.showShortToast(getResources().getString(R.string.top_search));
			startActivity(new Intent(this, SearchActivity.class));
			break;
		default:
			break;
		}
	}
	/**
	 * 处理各种状态的切换
	 */
	private void handleClickKeyboardTab(){
		int stateTemp = currentKeyboardState; 
		switch (currentKeyboardState) {
		case KEYBOARD_STATE_COMM://0
			break;
		case KEYBOARD_STATE_NO_INPUT_HIDE://1[由1->2]
			currentKeyboardState = KEYBOARD_STATE_NO_INPUT_SHOW;
			handleFromHideToShow_1_2();
			break;
		case KEYBOARD_STATE_NO_INPUT_SHOW://2[由2->1]
			currentKeyboardState = KEYBOARD_STATE_NO_INPUT_HIDE;
			handleFromShowToHide_2_1();
			break;
		case KEYBOARD_STATE_CALL_HIDE://4[由4->3]
			currentKeyboardState = KEYBOARD_STATE_CALL_SHOW;
			handleFromHideToShow_4_3();
			break;
		case KEYBOARD_STATE_CALL_SHOW://3//呼出
			history.call();
			break;
		}
		LogUtil.i("infos", "MainActivity: currentKeyboardState[1] = "+ stateTemp+"--->"+currentKeyboardState);
	}
	/**
	 * 设置keyTab的图片，及图片动画
	 * @param state
	 */
	public void setKeyboardState(int state){
		switch (state) {
		case KEYBOARD_STATE_COMM://0
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		case KEYBOARD_STATE_NO_INPUT_HIDE://1
			if(currentKeyboardState == KEYBOARD_STATE_NO_INPUT_SHOW){//由2->1
				handleFromShowToHide_2_1();
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
				ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_2);
			}
			break;
		case KEYBOARD_STATE_NO_INPUT_SHOW://2
			if(currentKeyboardState == KEYBOARD_STATE_CALL_SHOW){//由3->2
				handleSwitchState_3_2();
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
			}
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_3);
			break;
		case KEYBOARD_STATE_CALL_SHOW://3
			if(currentKeyboardState == KEYBOARD_STATE_NO_INPUT_SHOW){//由2->3
				handleSwitchState_2_3();
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
			}
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_4);
			break;
		case KEYBOARD_STATE_CALL_HIDE://4
			if(currentKeyboardState == KEYBOARD_STATE_CALL_SHOW){//由3->4
				handleFromShowToHide_3_4();
			}else{
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
				ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_2);
			}
			break;
		default:
			break;
		}
		LogUtil.i("infos", "MainActivity: currentKeyboardState[2] = "+ currentKeyboardState+"--->"+state);
		currentKeyboardState = state;
	}
	/**
	 * 处理输入框从【有】内容到【无】内容<br>
	 * KEYBOARD_STATE_CALL_SHOW-->KEYBOARD_STATE_NO_INPUT_SHOW
	 */
	private void handleSwitchState_3_2() {
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
	}
	/**
	 * 处理输入框从【无】内容到【有】内容<br>
	 * KEYBOARD_STATE_NO_INPUT_SHOW--> KEYBOARD_STATE_CALL_SHOW
	 */
	private void handleSwitchState_2_3() {
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
	}

	/**
	 * 处理 由 显示 到 隐藏<br>
	 * KEYBOARD_STATE_NO_INPUT_SHOW  --> KEYBOARD_STATE_NO_INPUT_HIDE
	 */
	private void handleFromShowToHide_2_1(){//2->1
		/*ObjectAnimator.ofFloat(ivKeyboard, "rotationX", 0f,180f).setDuration(KEYBOARD_ANIM_DURATION).start();
		history.hideKeyboard(KEYBOARD_ANIM_DURATION); 
		//ivKeyboard旋转后的显示效果图是【R.drawable.tab_iv_keyboard_2】，所以ivKeyboard_1的图片资源就为R.drawable.tab_iv_keyboard_2
		ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_2);*/
		ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
		history.hideKeyboard(KEYBOARD_ANIM_DURATION); 
	} 
	/**
	 * 处理 由 隐藏 到 显示<br>
	 *  KEYBOARD_STATE_NO_INPUT_HIDE   --> KEYBOARD_STATE_NO_INPUT_SHOW
	 */
	private void handleFromHideToShow_1_2(){//1->2
		/*ObjectAnimator.ofFloat(ivKeyboard, "rotationX", 180f,0f).setDuration(KEYBOARD_ANIM_DURATION).start();
		history.showKeyboard(KEYBOARD_ANIM_DURATION);
		//ivKeyboard旋转后的显示效果图是【R.drawable.tab_iv_keyboard_3】，所以ivKeyboard_1的图片资源就为R.drawable.tab_iv_keyboard_3
		ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_3);*/
		ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
		history.showKeyboard(KEYBOARD_ANIM_DURATION);
	} 
	/**
	 * 处理 由 显示 到 隐藏<br>
	 * KEYBOARD_STATE_CALL_SHOW  --> KEYBOARD_STATE_CALL_HIDE
	 */
	private void handleFromShowToHide_3_4(){//3->4
		/*ObjectAnimator oa = ObjectAnimator.ofFloat(ivKeyboard, "rotationX", 0f,180f).setDuration(KEYBOARD_ANIM_DURATION);
		oa.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if((Float)animation.getAnimatedValue() > 90f){
					ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
				}
			}
		});
		oa.start();
		history.hideKeyboard(KEYBOARD_ANIM_DURATION); 
		//ivKeyboard的资源图片【R.drawable.tab_iv_keyboard_3】旋转后的显示效果图是【R.drawable.tab_iv_keyboard_2】，所以ivKeyboard_1的图片资源就为R.drawable.tab_iv_keyboard_2
		ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_2);*/
		ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
		history.hideKeyboard(KEYBOARD_ANIM_DURATION); 
	} 
	/**
	 * 处理 由 隐藏 到 显示<br>
	 *  KEYBOARD_STATE_CALL_HIDE   --> KEYBOARD_STATE_CALL_SHOW
	 */
	private void handleFromHideToShow_4_3(){//4->3
		/*ObjectAnimator oa = ObjectAnimator.ofFloat(ivKeyboard, "rotationX", 180f,0f).setDuration(KEYBOARD_ANIM_DURATION);
		oa.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				if((Float)animation.getAnimatedValue() < 90f){
					ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
				}
			}
		});
		oa.start();
		history.showKeyboard(KEYBOARD_ANIM_DURATION);
		ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_4);*/
		ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
		history.showKeyboard(KEYBOARD_ANIM_DURATION);
	} 
	/**
	 * 设置选中的Tab
	 * @param cIndex 
	 */
	@SuppressLint("ResourceAsColor")
	private void setCurrentTab(int cIndex){
		if(cIndex != INDEX_KEYBOARD){//当选中的不是键盘Tab时，副本显示
			ivKeyboard_1.setVisibility(View.VISIBLE);
			ivKeyboard.setVisibility(View.INVISIBLE);
		}else{//当选中的是键盘Tab时，原始键盘tab显示，副本隐藏
			ivKeyboard_1.setVisibility(View.INVISIBLE);
			ivKeyboard.setVisibility(View.VISIBLE);
		}
		currentIndex = cIndex;
		mViewPager.setCurrentItem(currentIndex);
		switch (cIndex) {
		case INDEX_FAV:
			tvFav.setTextColor(getResources().getColor(R.color.tab_tv_selected));
			tvRelationship.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvDiscovery.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			tvMe.setTextColor(getResources().getColor(R.color.tab_tv_normal));
			ivFav.setImageResource(R.drawable.tab_iv1_pressed);
			ivRelationship.setImageResource(R.drawable.tab_iv2_normal);
			ivDiscovery.setImageResource(R.drawable.tab_iv3_normal);
			ivMe.setImageResource(R.drawable.tab_iv4_normal);
//			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_1);
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
//			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_1);
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
//			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_3);
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
//			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_1);
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
//			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			ivKeyboard_1.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		default:
			break;
		}
	}
	private class MyOnPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int index) {
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageSelected(int index) {
			setCurrentTab(index);
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























