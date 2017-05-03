package com.itheima.oschina.ui.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.oschina.R;
import com.itheima.oschina.po.News;
import com.itheima.oschina.util.UIUtils;

/**
 * 首页holder
 * 
 * @author Kevin
 * @date 2015-10-28
 */
public class HomeHolder extends BaseHolder<News> implements OnClickListener {

	public TextView tvTitle, tvBody, tvAuthor, tvPubdate, tvCommentcount,tv_hide;
    public ImageView im_comment_count;
	@Override
	public View initView() {
		// 1. 加载布局
		View view = UIUtils.inflate(R.layout.list_item_news);
		// 2. 初始化控件
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvBody = (TextView) view.findViewById(R.id.tv_body);
		tvAuthor = (TextView) view.findViewById(R.id.tv_author);
		tvPubdate = (TextView) view.findViewById(R.id.tv_pubdate);
		tvCommentcount = (TextView) view.findViewById(R.id.tv_commentcount);
		tv_hide = (TextView) view.findViewById(R.id.tv_hide);
		im_comment_count = (ImageView) view.findViewById(R.id.im_comment_count);
		return view;
	}

	@Override
	public void refreshView(News news) {
		tvTitle.setText(news.getTitle());
		tvBody.setText(news.getBody());
		tvAuthor.setText(news.getAuthor());
		tvPubdate.setText(news.getPubDate());
		tvCommentcount.setText(news.getCommentCount()+"");
	}

	@Override
	public void onClick(View v) {
		// switch (v.getId()) {
		// case R.id.fl_progress:
		// // 根据当前状态来决定下一步操作
		// if (mCurrentState == DownloadManager.STATE_UNDO || mCurrentState ==
		// DownloadManager.STATE_ERROR || mCurrentState ==
		// DownloadManager.STATE_PAUSE) {
		// mDM.download(getData());// 开始下载
		// } else if (mCurrentState == DownloadManager.STATE_DOWNLOADING ||
		// mCurrentState == DownloadManager.STATE_WAITING) {
		// mDM.pause(getData());// 暂停下载
		// } else if (mCurrentState == DownloadManager.STATE_SUCCESS) {
		// mDM.install(getData());// 开始安装
		// }
		// break;
		// default:
		// break;
		// }
	}

}