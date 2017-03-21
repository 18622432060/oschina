package com.itheima.oschina.ui.page.meun;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.itheima.oschina.R;
import com.itheima.oschina.ui.fragment.TweetNewFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 综合
 * 
 * @author liupeng
 * @date 2015-10-18
 */
@SuppressWarnings("unused")
public class TweetMenuDetailPager extends BaseMenuDetailPager {

	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager mViewPager;

	@ViewInject(R.id.indicator)
	private TabPageIndicator mIndicator;

	private ArrayList<TweetTabDetailPager> mPagers;

	public TweetMenuDetailPager(Activity activity, String[] title,
			FragmentManager supportFragmentManager) {
		super(activity, title, supportFragmentManager);
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_news_menu_detail,
				null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		mPagers = new ArrayList<TweetTabDetailPager>();

		for (String s : title) {
			TweetTabDetailPager pager = new TweetTabDetailPager(mActivity,
					title, supportFragmentManager);// 咨询
			mPagers.add(pager);
		}

		TweetMeunDetailAdapter adapter = new TweetMeunDetailAdapter(
				supportFragmentManager);
		mViewPager.setAdapter(adapter);
		mIndicator.setViewPager(mViewPager);// 将ViewPager和指示器绑定在一起。注意必须在设置数据后才能绑定
		// mViewPager.setOnPageChangeListener(this);
		// mIndicator.setOnPageChangeListener(this);////
		// 此处必须给指示器设置页面监听,不能设置给viewpager
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				Fragment fragment = new TweetNewFragment();
				// fragment.loadData();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		mIndicator.setCurrentItem(0);
	}

	class TweetMeunDetailAdapter extends FragmentPagerAdapter {

		public TweetMeunDetailAdapter(FragmentManager fm) {
			super(fm);
		}

		// 指示器的标题
		@Override
		public CharSequence getPageTitle(int position) {
			return title[position];
		}

		@Override
		public int getCount() {
			return mPagers.size();
		}

		// 返回当前页面位置的fragment对象
		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new TweetNewFragment();
			return fragment;
		}
	}

}