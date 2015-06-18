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
import com.fhit.ycall.fragment.HistoryBaseFragment;
import com.fhit.ycall.fragment.HistoryFragment;
import com.fhit.ycall.fragment.MeFragment;
import com.fhit.ycall.fragment.RelationshipFragment;
import com.fhit.ycall.util.LogUtil;
import com.fhit.ycall.util.ScreenUtils;

public class MainActivity extends FragmentActivity implements OnClickListener{
	
	private static final int INDEX_FAV = 0;
	private static final int INDEX_RELATIONSHIP = 1;
	private static final int INDEX_KEYBOARD = 2;
	private static final int INDEX_DICOVERY = 3;
	private static final int INDEX_ME = 4;
	
	/**
	 * ��currentIndex!=INDEX_KEYBOARDʱ
	 */
	public static final int KEYBOARD_STATE_COMM = 0;
	/**
	 * ��currentIndex==INDEX_KEYBOARDʱ�����̴��ڡ����ء�״̬���Ҽ���������ڡ��ޡ���������
	 */
	public static final int KEYBOARD_STATE_NO_INPUT_HIDE = 1;
	/**
	 * ��currentIndex==INDEX_KEYBOARDʱ�����̴��ڡ���ʾ��״̬���Ҽ���������ڡ��ޡ���������
	 */
	public static final int KEYBOARD_STATE_NO_INPUT_SHOW = 2;
	/**
	 * ��currentIndex==INDEX_KEYBOARDʱ�����̴��ڡ���ʾ��״̬���Ҽ���������ڡ��С���������
	 */
	public static final int KEYBOARD_STATE_CALL_SHOW = 3;
	/**
	 * ��currentIndex==INDEX_KEYBOARDʱ�����̴��ڡ����ء�״̬���Ҽ���������ڡ��С���������
	 */
	public static final int KEYBOARD_STATE_CALL_HIDE = 4;
	
	/**
	 * ��ǰ����tab��״̬
	 */
	private int currentKeyboardState = KEYBOARD_STATE_NO_INPUT_SHOW;
	 
	private int currentIndex = INDEX_KEYBOARD;
	//������ؿؼ�
	private TextView tvMsgCount;
	private TextView netState;
	//�м����ݵ���ص�����
	private List<Fragment> fragments;
	private Fragment fav,relationship,discovery,me;
	private HistoryBaseFragment history;
	private MViewPager mViewPager;
	private TabPagerAdapter adapter;
	//�ײ�tab����ؿؼ�
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
     * ��ʼ���ؼ�
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
    	//���Կ���MViewPager�����һ���
    	mViewPager.setScanScroll(false);
    }
    /**
     * ��Ӽ�����
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
     * ��ʼ������
     */
    private void initData(){
    	//��ʼ��5��fragment
    	fav = new FavFragment();
    	relationship = new RelationshipFragment();
    	history = new HistoryFragment();
    	discovery = new DiscoveryFragment();
    	me = new MeFragment();
    	//����ʼ����5��fragment����list����
    	fragments = new ArrayList<Fragment>();
    	fragments.add(fav);
    	fragments.add(relationship);
    	fragments.add(history);
    	fragments.add(discovery);
    	fragments.add(me);
    	//��ʼ��adapter
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
		case R.id.top_right_layout_1://���Ͻ� msg
			 
			break;
		case R.id.top_right_search://���Ͻ� search
			
			break;
		default:
			break;
		}
	}
	private void handleClickKeyboardTab(){
		if(currentIndex == INDEX_KEYBOARD){
//			LogUtil.i("infos", "MainActivity: keyboardState[3] = "+ currentKeyboardState);
			switch (currentKeyboardState) {
			case KEYBOARD_STATE_COMM://0
				break;
			case KEYBOARD_STATE_NO_INPUT_HIDE://1
				currentKeyboardState = KEYBOARD_STATE_NO_INPUT_SHOW;
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
				history.showKeyboard();
				break;
			case KEYBOARD_STATE_NO_INPUT_SHOW://2
				currentKeyboardState = KEYBOARD_STATE_NO_INPUT_HIDE;
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
				history.hideKeyboard(); 
				break;
			case KEYBOARD_STATE_CALL_HIDE://4
				currentKeyboardState = KEYBOARD_STATE_CALL_SHOW;
				ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
				history.showKeyboard();
				break;
			case KEYBOARD_STATE_CALL_SHOW://3//����
				history.call();
				break;
			default:
				break;
			}
//			LogUtil.i("infos", "MainActivity: currentKeyboardState = "+ currentKeyboardState);
		}else{//������tab �л��� keyboardʱ��Ӧ�ûָ�ԭ����״̬
			setCurrentTab(INDEX_KEYBOARD);
			setKeyboardState(currentKeyboardState);
		}
	}
	/**
	 * ����keyTab��ͼƬ
	 * @param state
	 */
	public void setKeyboardState(int state){
		currentKeyboardState = state;
//		LogUtil.i("infos", "MainActivity: keyboardState[2] = "+ currentKeyboardState);
		switch (currentKeyboardState) {
		case KEYBOARD_STATE_COMM://0
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_1);
			break;
		case KEYBOARD_STATE_NO_INPUT_HIDE://1
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
			break;
		case KEYBOARD_STATE_NO_INPUT_SHOW://2
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_3);
			break;
		case KEYBOARD_STATE_CALL_SHOW://3
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_4);
			break;
		case KEYBOARD_STATE_CALL_HIDE://4
			ivKeyboard.setImageResource(R.drawable.tab_iv_keyboard_2);
			break;

		default:
			break;
		}
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























