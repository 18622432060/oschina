package com.itheima.oschina.ui.fragment;

import java.util.HashMap;

import android.support.v4.app.Fragment;

/**
 * 生产fragment工厂
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class TweetFragmentFactory {

	private static HashMap<Integer, Fragment> mFragmentMap =new HashMap<Integer, Fragment>();

	public static Fragment createFragment(int pos) {
		// 先从集合中取, 如果没有,才创建对象, 提高性能
		Fragment fragment = mFragmentMap.get(pos);

		if (fragment == null) {
			switch (pos) {
			case 0:
				fragment = new TweetNewFragment();
				break;
			case 1:
				fragment = new TweetHotFragment();
				break;
			case 2:
				fragment = new TweetMeFragment();
				break;
			default:
				fragment = new TweetMeFragment();
				break;
			}

			mFragmentMap.put(pos, fragment);// 将fragment保存在集合中
		}

		return fragment;
	}
}
