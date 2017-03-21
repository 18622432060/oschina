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
import com.itheima.oschina.ui.fragment.BaseFragment;
import com.itheima.oschina.ui.fragment.FragmentFactory;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.TabPageIndicator;
/**
 * 综合
 * 
 * ViewPagerIndicator使用流程: 1.引入库 2.解决support-v4冲突(让两个版本一致) 3.从例子程序中拷贝布局文件
 * 4.从例子程序中拷贝相关代码(指示器和viewpager绑定; 重写getPageTitle返回标题) 5.在清单文件中增加样式 6.背景修改为白色
 * 7.修改样式-背景样式&文字样式
 * 
 * @author liupeng
 * @date 2015-10-18
 */
@SuppressWarnings("unused")
public class NewsMenuDetailPager extends BaseMenuDetailPager{
	
	@ViewInject(R.id.indicator)
	private TabPageIndicator mIndicator;

	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager mViewPager;

	
	private ArrayList<TabDetailPager> mPagers;

	public NewsMenuDetailPager(Activity activity,String[] title,FragmentManager supportFragmentManager) {
		super(activity,title,supportFragmentManager);
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.pager_news_menu_detail,null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		
		mViewPager.setOffscreenPageLimit(3);
		mPagers = new ArrayList<TabDetailPager>();
		
		for(String s :title){
			TabDetailPager pager = new TabDetailPager(mActivity,title,supportFragmentManager);//咨询
			mPagers.add(pager);
		}
		
		NewsMeunDetailAdapter adapter = new NewsMeunDetailAdapter(supportFragmentManager);
		mViewPager.setAdapter(adapter);
		mIndicator.setViewPager(mViewPager);//将ViewPager和指示器绑定在一起。注意必须在设置数据后才能绑定
		mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				BaseFragment fragment = FragmentFactory.createFragment(position);
				fragment.loadData();
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		
//		mIndicator.setCurrentItem(0);
		
	}

	class NewsMeunDetailAdapter extends FragmentPagerAdapter {

		public NewsMeunDetailAdapter(FragmentManager fm) {
			super(fm);
		}

		//指示器的标题
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
			BaseFragment fragment = FragmentFactory.createFragment(position);
			if(position==0){
				fragment.loadData();
			}
			return fragment;
		}
	}

}