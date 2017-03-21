package com.itheima.oschina.http.protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.itheima.oschina.http.HttpHelper;
import com.itheima.oschina.http.HttpHelper.HttpResult;
import com.itheima.oschina.po.NewsList;
import com.itheima.oschina.util.IOUtils;
import com.itheima.oschina.util.UIUtils;
import com.itheima.oschina.util.XmlUtils;

/**
 * 访问网络的基类
 * 
 * @author Kevin
 * @date 2015-10-28
 */
@SuppressWarnings("unchecked")
public abstract class BaseProtocol<T> {

	// index表示的是从哪个位置开始返回20条数据, 用于分页
	public T getData(int index) {
		// 先判断是否有缓存, 有的话就加载缓存
		T result = getCache(index);
		if (result == null) {// 如果没有缓存,或者缓存失效
			// 请求服务器
			return getDataFromServer(index);
		}
		return result;
	}

	// 从网络获取数据
	// index表示的是从哪个位置开始返回20条数据, 用于分页
	private T getDataFromServer(int index) {
		// http://www.itheima.com/home?index=0&name=zhangsan&age=18
		String fileName = HttpHelper.URL + getKey() + "page" + index + ".xml";
		HttpResult httpResult = HttpHelper.get(fileName);
		if (httpResult != null) {
			InputStream is = null;
			try {
				is = httpResult.getmResponse().getEntity().getContent();
				// 写缓存
				NewsList newlist = XmlUtils.toBean(NewsList.class, is);
				if (is != null) {
					setCache(fileName, newlist);
				}
				return (T) newlist;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.close(is);
			}
		}
		return null;
	}

	private void setCache(String fileName, Object obj) {
		File cacheDir = UIUtils.getContext().getCacheDir();
		File cacheFile = new File(cacheDir, fileName.replaceAll("/", ""));
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(cacheFile);
			ObjectOutputStream objOut = new ObjectOutputStream(out);
			objOut.writeObject(obj);
			objOut.flush();
			objOut.close();
			System.out.println("write object success!");
		} catch (IOException e) {
			System.out.println("write object failed");
			e.printStackTrace();
		} finally {
			IOUtils.close(out);
		}
	}

	// 获取网络链接关键词, 子类必须实现
	public abstract String getKey();

	// 获取网络链接参数, 子类必须实现
	public abstract String getParams();

	// 读缓存
	public T getCache(int index) {
		// 以url为文件名, 以json为文件内容,保存在本地
		File cacheDir = UIUtils.getContext().getCacheDir();// 本应用的缓存文件夹
		String fileName = HttpHelper.URL + getKey() + "page" + index + ".xml";
		// 生成缓存文件
		File cacheFile = new File(cacheDir, fileName.replaceAll("/", ""));
		// 判断缓存是否存在
		if (cacheFile.exists()) {
			// 判断缓存是否有效
			FileInputStream in = null;
			ObjectInputStream objIn = null;
			try {
				in = new FileInputStream(cacheFile);
				objIn = new ObjectInputStream(in);
				return (T) objIn.readObject();
			} catch (Exception e) {
				e.printStackTrace();
				if(e instanceof InvalidClassException){
					cacheFile.delete();
				}
			} finally {
				IOUtils.close(objIn);
				IOUtils.close(in);
			}
		}
		return null;

	}

}