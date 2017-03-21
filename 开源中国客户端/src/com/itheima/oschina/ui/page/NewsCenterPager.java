package com.itheima.oschina.ui.page;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 动弹
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class NewsCenterPager extends BasePager {


	public NewsCenterPager(Activity activity,FragmentManager supportFragmentManager) {
		super(activity,supportFragmentManager);
	}

	@Override
	public void initData() {
		// 清除之前旧的布局
		flContent.removeAllViews();
		// 要给帧布局填充布局对象
		TextView view = new TextView(mActivity);
		view.setText("动弹");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
		flContent.addView(view);
	}


}