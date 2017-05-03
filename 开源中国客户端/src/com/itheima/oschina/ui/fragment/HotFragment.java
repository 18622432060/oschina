package com.itheima.oschina.ui.fragment;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.oschina.R;
import com.itheima.oschina.http.protocol.HomeProtocol;
import com.itheima.oschina.manager.ThreadManager;
import com.itheima.oschina.po.News;
import com.itheima.oschina.po.NewsList;
import com.itheima.oschina.ui.holder.HomeHolder;
import com.itheima.oschina.ui.page.LoadingPage.ResultState;
import com.itheima.oschina.ui.view.RefreshListView;
import com.itheima.oschina.ui.view.RefreshListView.OnRefreshListener;
import com.itheima.oschina.util.PrefUtils;
import com.itheima.oschina.util.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 综合-热点
 * 
 * @author Kevin
 * @date 2015-10-27
 */
public class HotFragment extends BaseFragment {

	private List<News> data;
	@ViewInject(R.id.list_view)
	private RefreshListView listview;
	private HotAdapter hotAdapter;
	
	int temp = Integer.MAX_VALUE;
	
	// 如果加载数据成功, 就回调此方法, 在主线程运行
	@Override
	public View onCreateSuccessView() {
		View view = UIUtils.inflate(R.layout.pager_hot_menu);
		ViewUtils.inject(this,view);
		hotAdapter = new HotAdapter();
		listview.setAdapter(hotAdapter);
		listview.setRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				new Thread() {
					public void run() {
						SystemClock.sleep(2000);
//						listDatas.add(0, "我是下拉刷新出来的数据!");
						UIUtils.runOnUIThread(new Runnable() {

							@Override
							public void run() {
								hotAdapter.notifyDataSetChanged();
								listview.onRefreshComplete();
							}
						});
					};

				}.start();
			}

			@Override
			public void onLoadMore() {
				ThreadManager.getThreadPool().execute(new Runnable() {

					@Override
					public void run() {
						final List<News>  moreData = onLoadMorePlus();
						UIUtils.runOnUIThread(new Runnable() {
							@Override
							public void run() {
								if (moreData != null) {
									System.out.println("moreData: "+moreData.size());
									// 每一页有20条数据, 如果返回的数据小于20条, 就认为到了最后一页了
									if (moreData.size() < 20) {
//										holder.setData(MoreHolder.STATE_MORE_NONE, MyBaseAdapter.this);
										Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
									} else {
										// 还有更多数据
//										holder.setData(MoreHolder.STATE_MORE_MORE, MyBaseAdapter.this);
									}
									// 将更多数据追加到当前集合中
//									data.clear();
									data.addAll(moreData);
									// 刷新界面
									hotAdapter.notifyDataSetChanged();
									listview.onRefreshComplete();
								} else {
									listview.onRefreshEndComplete();
									Timer timer = new Timer();
									timer.schedule(new TimerTask() {
										public void run() {
											UIUtils.runOnUIThread(new Runnable() {
												@Override
												public void run() {
													listview.onRefreshComplete();
												}
											});	
										}
									}, 3000);
								}
							}
						});
					}
				});
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
				// 因为这种实现方式有头布局 所以要去掉头布局    position-1
				String readIds = PrefUtils.getString(UIUtils.getContext(), "read_ids", "");
				if(position -1 < data.size()){
					News news = data.get(position -1);
					if(!readIds.contains(String.valueOf(news.getId()))){
						readIds = readIds + news.getId() + ",";// 1101 1102
					}
					PrefUtils.setString(UIUtils.getContext(),"read_ids",readIds);
					TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
					tvTitle.setTextColor(Color.GRAY);
				}
			}
		});
		return view;
	}
	
	// 运行在子线程,可以直接执行耗时网络操作
	@Override
	public ResultState onLoad() {
		if(data==null){//每次横向滑动时都会执行一次
			HomeProtocol protocol = new HomeProtocol();
			NewsList newsList = (NewsList) protocol.getData(0);// 加载第一页数据
			if (newsList != null) {
				data = newsList.getList();
			} else {
				data = null;
			}
		}
		return check(data);// 校验数据并返回
	}

	class HotAdapter extends BaseAdapter {
		
		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			final HomeHolder holder;
			if (convertView == null) {
				holder = new HomeHolder();// 子类返回具体对象
			} else {
				holder = (HomeHolder) convertView.getTag();
			}

			News news = (News) getItem(position);
			
			holder.tvTitle.setText(news.getTitle());
			holder.tvBody.setText(news.getBody());
			holder.tvAuthor.setText(news.getAuthor());
			holder.tvPubdate.setText(news.getPubDate());
			holder.tvCommentcount.setText(news.getCommentCount()+"");
			
			String readIds = PrefUtils.getString(UIUtils.getContext(), "read_ids", "");
			TextView tvTitle = (TextView) holder.getRootView().findViewById(R.id.tv_title);
			if(readIds.contains(String.valueOf(news.getId()))){
				tvTitle.setTextColor(Color.GRAY);
			}else{
				tvTitle.setTextColor(Color.BLACK);
			}
			
			
			holder.im_comment_count.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(temp == position){
						temp = Integer.MAX_VALUE;
						holder.tv_hide.setVisibility(View.GONE);
					}else{
						temp = position;
					}
					hotAdapter.notifyDataSetChanged();
				}
			});
			
			if(temp == position){
				holder.tv_hide.setVisibility(View.VISIBLE);
			}else{
				holder.tv_hide.setVisibility(View.GONE);
			}
			return holder.getRootView();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

	}
	
	public List<News> onLoadMorePlus() {
		HomeProtocol protocol = new HomeProtocol();
		// 20, 40, 60.... 下一页数据的位置 等于 当前集合大小
		NewsList moreData = (NewsList) protocol.getData(data.size() / 20 - 1);
		if (moreData != null) {
			return moreData.getList();
		}
		return null;
	}

}