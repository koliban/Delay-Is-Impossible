package com.thefat.lingjunqi.getup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.thefat.lingjunqi.getup.R;

import java.util.LinkedList;

/**
 * 实现子类需注意：
 * 如果Activity是应用的第一个Activity，需重写{@code isFirstActivity}并返回{@code true}；
 * 如果Activity是应用的主Activity(Home)，需重写{@code isMainActivity}并返回{@code true}。
 *
 * @author junqi.ling@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {

	protected boolean mStateSaved;
	private Toolbar mToolbar;
	protected LinkedList<FragmentTransaction> mCommit;

	/**
	 * @return 该Activity是否是应用的入口
	 */
	protected boolean isFirstActivity() {
		return false;
	}

	/**
	 * @return 该Activity是否是应用的主页
	 */
	protected boolean isMainActivity() {
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar ab = getSupportActionBar();
		//默认都有返回键
		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
		}

		if (isFirstActivity()) {
			onFirstActivity();
		}

		if (isMainActivity()) {
			onMainActivity();
		}

		mCommit = new LinkedList<>();
		mStateSaved = false;
		//d("onCreate");
	}

	protected Toolbar getToolbar() {
		if (mToolbar == null) {
			mToolbar = findToolbarById();
			if (mToolbar != null) {
				setSupportActionBar(mToolbar);
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		return mToolbar;
	}

	protected Toolbar findToolbarById() {
		// FIXME: 16/4/8
		return mToolbar;
		//return (Toolbar)findViewById(R.id.toolbar_base);
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		getToolbar();
	}

	public int getActionBarHeight() {
		int actionBarHeight = 0;

		if (getToolbar() != null) {
			actionBarHeight = getToolbar().getHeight();
		} else {
			TypedValue tv = new TypedValue();
			if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
				actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
			}
		}

		return actionBarHeight;
	}

	protected void onMainActivity() {
		// TODO: 16/4/8 UMENG
		/*if (Config.UMENG_AUTO_UPDATE) {
			checkUpdate();
		}*/
	}

	protected void onFirstActivity() {
		// TODO: 16/4/8 UMENG
		/*if (Config.UMENG_ENABLE) {
			MobclickAgent.onError(this);
		}*/
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		//d("onNewIntent:" + intent);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (mToolbar != null) {
			// 防止连续的activity共有的toolbar会被设置透明度
			mToolbar.getBackground().setAlpha(255);
		}

		//d("onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		//d("onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		/*if (Config.UMENG_ENABLE) {
			MobclickAgent.onPause(this);
		}*/
		//d("onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		//d("onStop");
	}

	@Override
	protected void onDestroy() {
		//d("onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//d("onSaveInstanceState");
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//d("onRestoreInstanceState");
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
}
