package com.itheima.oschina.ui.fragment;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.itheima.oschina.po.AppInfo;
import com.itheima.oschina.ui.page.LoadingPage.ResultState;
import com.itheima.oschina.ui.view.MyListView;
import com.itheima.oschina.util.UIUtils;

/**
 * 综合-资讯
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class HotFragment extends BaseFragment {

	private ArrayList<AppInfo> data;
	private ArrayList<String> mPictureList;

	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		ListView view = new MyListView(UIUtils.getContext());

		// 给ListView增加头布局
//		HomeHeaderHolder header = new HomeHeaderHolder();
//		view.addHeaderView(header.getRootView());
//		view.setAdapter(new HomeAdapter(data));
		if (mPictureList != null) {
//			header.setData(mPictureList);// 设置轮播条数据
		}
		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				AppInfo appInfo = data.get(position - 1);// 去掉头布局
				if (appInfo != null) {
//					Intent intent = new Intent(UIUtils.getContext(),HomeDetailActivity.class);
//					intent.putExtra("packageName", appInfo.packageName);
//					startActivity(intent);
				}
			}
		});
		return view;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public ResultState onLoad() {
		data = null;// protocol.getData(0);// 加载第一页数据
		return check(data);// 校验数据并返回
	}

}