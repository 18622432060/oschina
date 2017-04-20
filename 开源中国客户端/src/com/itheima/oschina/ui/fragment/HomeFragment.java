package com.itheima.oschina.ui.fragment;

import java.util.List;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 综合-资讯
 * 
 * @author Kevin
 * @date 2015-10-27
 */
@SuppressWarnings("deprecation")
public class HomeFragment extends BaseFragment implements OnRefreshListener{

	private List<News> data;
	@ViewInject(R.id.swipe_refresh_widget)
	private SwipeRefreshLayout  mSwipeRefreshWidget;
	@ViewInject(R.id.my_listView)
	private MyListView mList;
	
	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		View view = UIUtils.inflate(R.layout.swipe_refresh_widget_sample);
		ViewUtils.inject(this,view);
	
		mSwipeRefreshWidget.setColorScheme(R.color.color1, R.color.color2, R.color.color3,R.color.color4);
		mList.setAdapter(new HomeAdapter(data));
		mList.setOnItemClickListener(new OnItemClickListener() {
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
		mSwipeRefreshWidget.setOnRefreshListener(this);
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

	@Override
	public void onRefresh() {
        refresh();
	}
	
   private void refresh() {
        mHandler.removeCallbacks(mRefreshDone);
        mHandler.postDelayed(mRefreshDone, 1000);
    }
   
   private Handler mHandler = new Handler();
   private final Runnable mRefreshDone = new Runnable() {
       @Override
       public void run() {
    	   mSwipeRefreshWidget.setRefreshing(false);
       }

   };

}