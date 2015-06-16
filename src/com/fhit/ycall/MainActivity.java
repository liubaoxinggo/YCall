package com.fhit.ycall;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhit.ycall.adapter.TabPagerAdapter;
import com.fhit.ycall.customview.MViewPager;
import com.fhit.ycall.fragment.DiscoveryFragment;
import com.fhit.ycall.fragment.FavFragment;
import com.fhit.ycall.fragment.KeyboardFragment;
import com.fhit.ycall.fragment.MeFragment;
import com.fhit.ycall.fragment.RelationshipFragment;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private static final int INDEX_FAV = 0;
	private static final int INDEX_RELATIONSHIP = 1;
	private static final int INDEX_KEYBOARD = 2;
	private static final int INDEX_DICOVERY = 3;
	private static final int INDEX_ME = 4;
	
	private int currentIndex = INDEX_KEYBOARD;
	
	//中间内容的相关的引用
	private List<Fragment> fragments;
	private Fragment fav,relationship,keyboard,discovery,me;
	private MViewPager mViewPager;
	private TabPagerAdapter adapter;
	//底部tab的相关控件
	private ImageView ivFav,ivRelationship,ivDiscovery,ivMe,ivKeyboard;
	private TextView tvFav,tvRelationship,tvDiscovery,tvMe;
	private LinearLayout llFav,llRelationship,llDiscovery,llMe;
	
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
			currentIndex = INDEX_FAV;
			mViewPager.setCurrentItem(currentIndex); 
			break;
		case R.id.tab_2:
			
			break;
		case R.id.tab_iv_keyboard:
			
			break;
		case R.id.tab_3:
			
			break;
		case R.id.tab_4:
			
			break;
		default:
			break;
		}
	}
	private void setCurrentTab(int currentIndex){
		
	}
	private class MyOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int index) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int index) {
			// TODO Auto-generated method stub
			
		}
	} 
}























