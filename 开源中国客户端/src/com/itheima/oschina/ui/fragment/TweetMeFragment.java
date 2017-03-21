package com.itheima.oschina.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.oschina.R;

/**
 * 综合-资讯
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class TweetMeFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View View = inflater.inflate(R.layout.tweet_pager_tab_detail, container);
		return View;
	}
	
}