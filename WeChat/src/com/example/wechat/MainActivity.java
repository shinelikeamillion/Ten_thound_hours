package com.example.wechat;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
	private ViewPager mviewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mDatas;
	private TextView tv_chat;
	private TextView tv_friend;
	private TextView tv_contact;
	private int mScreen1_3;
	private ImageView iv_tabline;
	private LayoutParams layoutParams;
	private int mCurrentPageIndex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去掉actionBar（要放在setContentView前面）
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		tv_chat = (TextView) findViewById(R.id.id_tv_chat);
		tv_friend = (TextView) findViewById(R.id.id_tv_friend);
		tv_contact = (TextView) findViewById(R.id.id_tv_contact);

		initTabLine();
		initView();
	}

	// 初始化指示器
	private void initTabLine() {
		iv_tabline = (ImageView) findViewById(R.id.id_iv_tabline);

		// 得倒整个屏幕的高度和宽度
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		DisplayMetrics outMetrics = new DisplayMetrics();
		display.getMetrics(outMetrics);
		mScreen1_3 = outMetrics.widthPixels / 3;
		// 界面切换指示器
		layoutParams = iv_tabline.getLayoutParams();
		layoutParams.width = mScreen1_3;
		iv_tabline.setLayoutParams(layoutParams);
	}

	private void initView() {
		mviewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mDatas = new ArrayList<Fragment>();
		ChatMainTabFrament chatMainTabFrament = new ChatMainTabFrament();
		FriendtMainTabFrament friendtMainTabFrament = new FriendtMainTabFrament();
		ContactMainTabFrament contactMainTabFrament = new ContactMainTabFrament();

		// 要用v4下的fragement包
		mDatas.add(chatMainTabFrament);
		mDatas.add(friendtMainTabFrament);
		mDatas.add(contactMainTabFrament);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mDatas.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mDatas.get(arg0);
			}
		};
		mviewPager.setAdapter(mAdapter);

		mviewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					tv_chat.setTextColor(Color.parseColor("#008000"));
					break;
				case 1:
					tv_friend.setTextColor(Color.parseColor("#008000"));
					break;
				case 2:
					tv_contact.setTextColor(Color.parseColor("#008000"));
					break;

				default:
					break;
				}
				mCurrentPageIndex = position;
			}

			private void resetTextView() {
				tv_chat.setTextColor(Color.BLACK);
				tv_friend.setTextColor(Color.BLACK);
				tv_contact.setTextColor(Color.BLACK);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPx) {
				LinearLayout.LayoutParams ivTabline_Params = (android.widget.LinearLayout.LayoutParams) iv_tabline.getLayoutParams();
				if (mCurrentPageIndex == 0 && position ==0) {//第0页到第1页
					ivTabline_Params.leftMargin = (int) ((positionOffset + mCurrentPageIndex) * mScreen1_3);
				} else if (mCurrentPageIndex == 1 && position ==0) {
					ivTabline_Params.leftMargin = (int) ((positionOffset + mCurrentPageIndex - 1) * mScreen1_3);
				} else if (mCurrentPageIndex == 1 && position == 1) {
					ivTabline_Params.leftMargin = (int) ((positionOffset + mCurrentPageIndex) * mScreen1_3);
				} else if (mCurrentPageIndex == 2 && position == 1) {
					ivTabline_Params.leftMargin = (int) ((positionOffset + mCurrentPageIndex - 1) * mScreen1_3);
				}
				iv_tabline.setLayoutParams(ivTabline_Params);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

}
