package com.itheima.oschina.ui.page;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.itheima.oschina.ui.page.meun.BaseMenuDetailPager;
import com.itheima.oschina.ui.page.meun.NewsMenuDetailPager;

/**
 * 综合
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class HomePager extends BasePager {
	String [] title = {"资讯","热点","博客","推荐"} ;
	private ArrayList<BaseMenuDetailPager> mMenuDetailPagers;

	public HomePager(Activity activity,FragmentManager supportFragmentManager) {
		super(activity,supportFragmentManager);
	}

	@Override
	public void initData() {

		// 要给帧布局填充布局对象

		mMenuDetailPagers = new ArrayList<BaseMenuDetailPager>();
		mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity,title,supportFragmentManager));

		// 将新闻菜单详情页设置为默认页面
		setCurrentDetailPager(0);
	}

	// 设置菜单详情页
	public void setCurrentDetailPager(int position) {
		// 重新给frameLayout添加内容

		BaseMenuDetailPager pager = mMenuDetailPagers.get(position);// 获取当前应该显示的页面
		View view = pager.mRootView;// 当前页面的布局

		// 清除之前旧的布局
		flContent.removeAllViews();

		flContent.addView(view);// 给帧布局添加布局

		// 初始化页面数据
		pager.initData();
	}
}