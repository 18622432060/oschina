package com.itheima.oschina.util;

import com.lidroid.xutils.BitmapUtils;

public class BitmapHelper {

	private static BitmapUtils mBitmapUtils = null;

	// 单例, 懒汉模式
	public static BitmapUtils getBitmapUtils() {
		if (mBitmapUtils == null) {
			synchronized (BitmapHelper.class) {//同步锁
				if (mBitmapUtils == null) {
					mBitmapUtils = new BitmapUtils(UIUtils.getContext());
				}
			}
		}
		return mBitmapUtils;
	}
}
