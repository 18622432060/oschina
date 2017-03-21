package com.itheima.oschina.ui.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.itheima.oschina.R;
import com.itheima.oschina.ui.page.BasePager;
import com.itheima.oschina.ui.page.ExplorePager;
import com.itheima.oschina.ui.page.HomePager;
import com.itheima.oschina.ui.page.MePager;
import com.itheima.oschina.ui.page.NewsCenterPager;
import com.itheima.oschina.ui.view.NoScrollViewPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 主页面
 * 
 * @author Kevin
 * @date 2015-10-17
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	
	private ActionBarDrawerToggle toggle;
	private ArrayList<BasePager> mPagers;// 四个标签页集合
	
	@ViewInject(R.id.vp_content)
	private NoScrollViewPager mViewPager;

	@ViewInject(R.id.rg_group)
	private RadioGroup rgGroup;

	@ViewInject(R.id.menu_item_quests)
	private LinearLayout menuItemQuests;

	@ViewInject(R.id.menu_item_opensoft)
	private LinearLayout menuItemOpensoft;
	
	@ViewInject(R.id.menu_item_blog)
	private LinearLayout menuItemBlog;

	@ViewInject(R.id.menu_item_gitapp)
	private LinearLayout menuItemGitapp;

	@ViewInject(R.id.menu_item_rss)
	private LinearLayout menuItemRss;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_main);
		ViewUtils.inject(this);

		menuItemQuests.setOnClickListener(this);
		menuItemOpensoft.setOnClickListener(this);
		menuItemBlog.setOnClickListener(this);
		menuItemGitapp.setOnClickListener(this);
		menuItemRss.setOnClickListener(this);
		
		initActionBar();
		initData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void initActionBar() {
		ActionBar actionBar = getActionBar();// 可以做侧拉 谷歌电子市场Day05 10.Actionbar的使用
		actionBar.setTitle("开源中国");// 设置标题
		actionBar.setLogo(R.drawable.ic_launcher);// 设置logo
		actionBar.setHomeButtonEnabled(true);// logo是否可以点击
		actionBar.setDisplayShowHomeEnabled(false);// 隐藏logo
		actionBar.setDisplayHomeAsUpEnabled(true);// 显示返回键

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

		// 初始化抽屉开关
		toggle = new ActionBarDrawerToggle(this, drawer,R.drawable.icon_pic_menu, R.string.drawer_open,R.string.drawer_close);

		toggle.syncState();// 同步状态, 将DrawerLayout和开关关联在一起
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				toggle.onOptionsItemSelected(item);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void initData() {
		
		mPagers = new ArrayList<BasePager>();
		FragmentManager supportFragmentManager = getSupportFragmentManager();
		// 添加四个标签页
		mPagers.add(new HomePager(MainActivity.this,supportFragmentManager));
		mPagers.add(new NewsCenterPager(MainActivity.this,supportFragmentManager));
		mPagers.add(new ExplorePager(MainActivity.this,supportFragmentManager));
		mPagers.add(new MePager(MainActivity.this,supportFragmentManager));
		mViewPager.setOffscreenPageLimit(mPagers.size()-1);

		mViewPager.setAdapter(new ContentAdapter());
		// 底栏标签切换监听
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					// 综合
					mViewPager.setCurrentItem(0, false);// 参2:表示是否具有滑动动画
					break;
				case R.id.rb_news:
					// 动态
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_smart:
					// 加号
					// mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_gov:
					// 发现
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_setting:
					// 我
					mViewPager.setCurrentItem(3, false);
					break;
				default:
					break;
				}
			}
		});

		// 手动加载第一页数据
		mPagers.get(0).initData();

	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagers.get(position);
			View view = pager.mRootView;// 获取当前页面对象的布局
			pager.initData();// 初始化数据
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_item_quests:
			Intent intent = new Intent(this,QuestsActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}