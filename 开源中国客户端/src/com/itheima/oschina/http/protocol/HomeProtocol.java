package com.itheima.oschina.http.protocol;

import java.util.List;

import com.itheima.oschina.po.News;

/**
 * 首页网络数据解析
 * 
 * @author Kevin
 * @date 2015-10-28
 */
public class HomeProtocol extends BaseProtocol<List<News>> {

	@Override
	public String getKey() {
		return "oschina/list/news/";
	}

	@Override
	public String getParams() {
		return "";// 如果没有参数,就传空串,不要传null
	}


}
