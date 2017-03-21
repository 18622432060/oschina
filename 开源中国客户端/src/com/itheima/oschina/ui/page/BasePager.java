package com.itheima.oschina.ui.page;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.itheima.oschina.R;

public class BasePager {

	public Activity mActivity;
	public FragmentManager supportFragmentManager;
	public TextView tvTitle;
//	public ImageButton btnMenu;
	public FrameLayout flContent;// 空的帧布局对象, 要动态添加布局
//	public ImageButton btnPhoto;
	public View mRootView;// 当前页面的布局对象

	public BasePager(Activity activity,FragmentManager supportFragmentManager) {
		this.supportFragmentManager = supportFragmentManager;
		mActivity = activity;
		mRootView = initView();
	}

	public View initView() {
		View view = View.inflate(mActivity, R.layout.base_pager, null);
//		tvTitle = (TextView) view.findViewById(R.id.tv_title);
//		btnMenu = (ImageButton) view.findViewById(R.id.btn_menu);
		flContent = (FrameLayout) view.findViewById(R.id.fl_content);
//		btnPhoto = (ImageButton) view.findViewById(R.id.btn_photo);
//
//		btnMenu.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				toggle();
//			}
//		});
		
		return view;
	}
	
	protected void toggle() {
//		MainActivity mainUi = (MainActivity) mActivity;
//		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
//		slidingMenu.toggle();
	}

	public void initData() {

	}

}
