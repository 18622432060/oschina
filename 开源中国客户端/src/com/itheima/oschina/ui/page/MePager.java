package com.itheima.oschina.ui.page;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 首页
 * 
 * @author Kevin
 * @date 2015-10-18
 */
public class MePager extends BasePager {

	public MePager(Activity activity,FragmentManager supportFragmentManager) {
		super(activity,supportFragmentManager);
	}

	@Override
	public void initData() {
		// 清除之前旧的布局
		flContent.removeAllViews();
		// 要给帧布局填充布局对象
		TextView view = new TextView(mActivity);
		view.setText("我");
		view.setTextColor(Color.RED);
		view.setTextSize(22);
		view.setGravity(Gravity.CENTER);
//
		flContent.addView(view);
	}

}
