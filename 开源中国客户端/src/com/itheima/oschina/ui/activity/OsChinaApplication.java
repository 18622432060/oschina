package com.itheima.oschina.ui.activity;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 
 * 自定义Application进行全局初始化
 * 
 * @author Administrator
 * 
 */
public class OsChinaApplication extends Application {

	private static Context context;
	private static Handler handler;
	private static int mainThreadId;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		handler = new Handler();
		mainThreadId = android.os.Process.myTid();
	}

	public static Context getContext() {
		return context;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static int getMainThreadId() {
		return mainThreadId;
	}

}
