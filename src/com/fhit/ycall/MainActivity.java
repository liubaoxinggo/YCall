package com.fhit.ycall;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.fhit.ycall.fragment.KeyboardFragment;
import com.fhit.ycall.fragment.MeFragment;
import com.fhit.ycall.fragment.RelationshipFragment;
import com.fhit.ycall.util.ScreenUtils;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private static final int INDEX_FAV = 0;
	private static final int INDEX_RELATIONSHIP = 1;
	private static final int INDEX_KEYBOARD = 2;
	private static final int INDEX_DICOVERY = 3;
	private static final int INDEX_ME = 4;
	
	private int currentIndex = INDEX_KEYBOARD;
	//顶部相关控件
	private TextView tvMsgCount;
	private TextView netState;
	//中间内容的相关的引用
	private List<Fragment> fragments;
	private Fragment fav,relationship,keyboard,discovery,me;
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
    	mViewPager.setScanScroll(true);
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
    	keyboard = new KeyboardFragment();
    	discovery = new DiscoveryFragment();
    	me = new MeFragment();
    	//将初始化的5个fragment加入list集合
    	fragments = new ArrayList<Fragment>();
    	fragments.add(fav);
    	fragments.add(relationship);
    	fragments.add(keyboard);
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
			netState.setVisibility(View.GONE);
			setCurrentTab(INDEX_KEYBOARD);
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
	@SuppressLint("ResourceAsColor")
	private void setCurrentTab(int cIndex){
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
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
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























