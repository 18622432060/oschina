package com.itheima.oschina.ui.adapter;

import java.util.List;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.oschina.R;
import com.itheima.oschina.manager.ThreadManager;
import com.itheima.oschina.po.News;
import com.itheima.oschina.ui.holder.BaseHolder;
import com.itheima.oschina.ui.holder.MoreHolder;
import com.itheima.oschina.util.PrefUtils;
import com.itheima.oschina.util.UIUtils;

/**
 * 对adapter的封装
 * 
 * @author Kevin
 * @date 2015-10-28
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	// 注意: 此处必须要从0开始写
	private static final int TYPE_NORMAL = 1;// 正常布局类型
	private static final int TYPE_MORE = 0;// 加载更多类型

	private List<T> data;

	public MyBaseAdapter(List<T> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.size() + 1;// 增加加载更多布局数量 +1
	}

	@Override
	public T getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 返回布局类型个数
	@Override
	public int getViewTypeCount() {
		return 2;// 返回两种类型,普通布局+加载更多布局
	}

	// 返回当前位置应该展示那种布局类型
	@Override
	public int getItemViewType(int position) {
		if (position == getCount() - 1) {// 最后一个
			return TYPE_MORE;
		} else {
			return getInnerType(position);
		}
	}

	// 子类可以重写此方法来更改返回的布局类型
	public int getInnerType(int position) {
		return TYPE_NORMAL;// 默认就是普通类型
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseHolder holder;
		if (convertView == null) {
			// 1. 加载布局文件
			// 2. 初始化控件 findViewById
			// 3. 打一个标记tag
			if (getItemViewType(position) == TYPE_MORE) {
				// 加载更多的类型
				holder = new MoreHolder(hasMore());
			} else {
				holder = getHolder(position);// 子类返回具体对象
			}
		} else {
			holder = (BaseHolder) convertView.getTag();
		}

		// 4. 根据数据来刷新界面
		if (getItemViewType(position) != TYPE_MORE) {
			holder.setData(getItem(position), MyBaseAdapter.this);
			String readIds = PrefUtils.getString(UIUtils.getContext(), "read_ids", "");
			News news = (News) getItem(position);
			TextView tvTitle = (TextView) holder.getRootView().findViewById(R.id.tv_title);
			if(readIds.contains(String.valueOf(news.getId()))){
				tvTitle.setTextColor(Color.GRAY);
			}else{
				tvTitle.setTextColor(Color.BLACK);
			}
		} else {
			// 加载更多布局
			// 一旦加载更多布局展示出来, 就开始加载更多
			// 只有在有更多数据的状态下才加载更多
			MoreHolder moreHolder = (MoreHolder) holder;
			if (moreHolder.getData() == MoreHolder.STATE_MORE_MORE) {
				loadMore(moreHolder);
			}
		}
		return holder.getRootView();
	}

	// 子类可以重写此方法来决定是否可以加载更多
	public boolean hasMore() {
		return true;// 默认都是有更多数据的
	}

	private boolean isLoadMore = false;// 标记是否正在加载更多

	// 加载更多数据
	public void loadMore(final MoreHolder holder) {
		if (!isLoadMore) {
			isLoadMore = true;
			ThreadManager.getThreadPool().execute(new Runnable() {

				@Override
				public void run() {
					final List<T> moreData = onLoadMore();
					UIUtils.runOnUIThread(new Runnable() {
						@Override
						public void run() {
							if (moreData != null) {
								// 每一页有20条数据, 如果返回的数据小于20条, 就认为到了最后一页了
								if (moreData.size() < 20) {
									holder.setData(MoreHolder.STATE_MORE_NONE, MyBaseAdapter.this);
									Toast.makeText(UIUtils.getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
								} else {
									// 还有更多数据
									holder.setData(MoreHolder.STATE_MORE_MORE, MyBaseAdapter.this);
								}
								// 将更多数据追加到当前集合中
								data.addAll(moreData);
								// 刷新界面
								MyBaseAdapter.this.notifyDataSetChanged();
							} else {
								// 加载更多失败
								holder.setData(MoreHolder.STATE_MORE_ERROR, MyBaseAdapter.this);
							}
							isLoadMore = false;
						}
					});
				}
			});
		}
	}

	public int getListSize() {
		return data.size();
	}

	// 返回当前页面的holder对象必须子类实现
	public abstract BaseHolder<T> getHolder(int position);

	// 加载更多数据, 必须由子类实现
	public abstract List<T> onLoadMore();
}