package com.itheima.oschina.ui.page.meun;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.itheima.oschina.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 
 * @author Administrator
 * 
 */
public class TweetTabDetailPager extends BaseMenuDetailPager {

	private View view;

	public TweetTabDetailPager(Activity activity, String[] title,
			FragmentManager supportFragmentManager) {
		super(activity, title, supportFragmentManager);
	}

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.tweet_pager_tab_detail, null);
		ViewUtils.inject(this, view);

		return view;
	}

}
