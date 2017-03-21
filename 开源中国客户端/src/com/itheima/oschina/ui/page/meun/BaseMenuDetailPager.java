package com.itheima.oschina.ui.page.meun;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * 菜单详情页基类
 * 
 * @author Administrator
 * 
 */
public abstract class BaseMenuDetailPager {

	public Activity mActivity;
	public View mRootView;// 当前页面的布局对象
	public FragmentManager supportFragmentManager;
	public String[] title;

	public BaseMenuDetailPager(Activity activity, String[] title,FragmentManager supportFragmentManager) {
		this.title = title;
		mActivity = activity;
		this.supportFragmentManager = supportFragmentManager;
		mRootView = initView();
	}

	// 初始化布局，必须子类实现
	public abstract View initView();

	public void initData() {

	}

}