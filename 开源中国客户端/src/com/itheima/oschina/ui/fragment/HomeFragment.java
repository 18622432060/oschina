package com.itheima.oschina.ui.fragment;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.oschina.R;
import com.itheima.oschina.http.protocol.HomeProtocol;
import com.itheima.oschina.po.News;
import com.itheima.oschina.po.NewsList;
import com.itheima.oschina.ui.adapter.MyBaseAdapter;
import com.itheima.oschina.ui.holder.BaseHolder;
import com.itheima.oschina.ui.holder.HomeHolder;
import com.itheima.oschina.ui.page.LoadingPage.ResultState;
import com.itheima.oschina.ui.view.MyListView;
import com.itheima.oschina.util.PrefUtils;
import com.itheima.oschina.util.UIUtils;

/**
 * 综合-资讯
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class HomeFragment extends BaseFragment {

	private List<News> data;

	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		ListView view = new MyListView(UIUtils.getContext());

		view.setAdapter(new HomeAdapter(data));
		view.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// News News = data.get(position - 1);// 去掉头布局
				// if (News != null) {
				// Intent intent = new
				// Intent(UIUtils.getContext(),HomeDetailActivity.class);
				// intent.putExtra("packageName", News.packageName);
				// startActivity(intent);
				// }
				String readIds = PrefUtils.getString(UIUtils.getContext(), "read_ids", "");
				News news = data.get(position);
				if(!readIds.contains(String.valueOf(news.getId()))){
					readIds = readIds + news.getId() + ",";// 1101 1102
				}
				PrefUtils.setString(UIUtils.getContext(),"read_ids",readIds);
				TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
				tvTitle.setTextColor(Color.GRAY);
			}
		});
		return view;
	}

	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public ResultState onLoad() {
		HomeProtocol protocol = new HomeProtocol();
		NewsList newsList = (NewsList) protocol.getData(0);// 加载第一页数据
		if (newsList != null) {
			data = newsList.getList();
		} else {
			data = null;
		}
		return check(data);// 校验数据并返回
	}

	class HomeAdapter extends MyBaseAdapter<News> {

		public HomeAdapter(List<News> data) {
			super(data);
		}

		@Override
		public BaseHolder<News> getHolder(int position) {
			return new HomeHolder();
		}

		@Override
		public List<News> onLoadMore() {
			HomeProtocol protocol = new HomeProtocol();
			// 20, 40, 60.... 下一页数据的位置 等于 当前集合大小
			NewsList moreData = (NewsList) protocol.getData(getListSize() / 20 - 1);
			if (moreData != null) {
				return moreData.getList();
			}
			return null;
		}
	}

}