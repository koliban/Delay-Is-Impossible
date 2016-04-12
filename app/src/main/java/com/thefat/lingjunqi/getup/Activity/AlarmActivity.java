package com.thefat.lingjunqi.getup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.thefat.lingjunqi.getup.Adapter.AlarmListAdapter;
import com.thefat.lingjunqi.getup.Alarm;
import com.thefat.lingjunqi.getup.Database.Database;
import com.thefat.lingjunqi.getup.R;
import com.thefat.lingjunqi.getup.ViewHelper;

import java.util.List;

/**
 * @author junqi.ling@gmail.com
 */
public class AlarmActivity extends BaseActivity implements ObservableScrollViewCallbacks {

	private FloatingActionButton mAddAlarm;
	private AlarmListAdapter mAlarmListAdapter;
	private ListView mAlarmListView;
	private View mFlexibleSpaceView;
	private View mToolbarView;
	private int mFlexibleSpaceHeight;
	private TextView mTitleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_alarm);
		//setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
		ActionBar ab = getSupportActionBar();
		if (ab != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		}

		mFlexibleSpaceView = findViewById(R.id.flexible_space);
		mTitleView = (TextView) findViewById(R.id.title);
		mTitleView.setText(getTitle());
		setTitle(null);
		mToolbarView = findViewById(R.id.toolbar);

		final ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scroll);
		scrollView.setScrollViewCallbacks(this);

		mFlexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_height);
		int flexibleSpaceAndToolbarHeight = mFlexibleSpaceHeight + getActionBarSize();

		findViewById(R.id.body).setPadding(0, flexibleSpaceAndToolbarHeight, 0, 0);
		mFlexibleSpaceView.getLayoutParams().height = flexibleSpaceAndToolbarHeight;

		ScrollUtils.addOnGlobalLayoutListener(mTitleView, new Runnable() {
			@Override
			public void run() {
				updateFlexibleSpaceText(scrollView.getCurrentScrollY());
			}
		});

		mAlarmListView = (ListView) findViewById(R.id.alarm_list);
		mAlarmListView.setLongClickable(true);
		mAlarmListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO: 16/4/12 enter code;
				return true;
			}
		});

		// TODO: 16/4/12 callMathAlarmScheduleService();

		mAlarmListAdapter = new AlarmListAdapter(this);
		this.mAlarmListView.setAdapter(mAlarmListAdapter);
		mAlarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				Alarm alarm = (Alarm) mAlarmListAdapter.getItem(position);
				Intent intent = new Intent(AlarmActivity.this, AlarmPreferencesActivity.class);
				intent.putExtra("alarm", alarm);
				startActivity(intent);
			}
		});

		mAddAlarm = (FloatingActionButton) findViewById(R.id.btn_add_alarm);
		mAddAlarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(AlarmPreferencesActivity.createIntent(AlarmActivity.this));
			}
		});
	}

	protected boolean isFirstActivity() {
		return true;
	}

	@Override
	protected boolean isMainActivity() {
		return true;
	}

	@Override
	protected void onPause() {
		Database.deactivate();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateAlarmList();
	}

	public void updateAlarmList() {
		Database.init(AlarmActivity.this);
		final List<Alarm> alarms = Database.getAll();
		mAlarmListAdapter.setAlarms(alarms);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//reload content
				AlarmActivity.this.mAlarmListAdapter.notifyDataSetChanged();
				if (alarms.size() > 0) {
					//findViewById(android.R.id.empty).setVisibility(View.INVISIBLE);
				} else {
					//findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
		updateFlexibleSpaceText(scrollY);
	}

	@Override
	public void onDownMotionEvent() {
	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {
	}

	private void updateFlexibleSpaceText(final int scrollY) {
		ViewHelper.setTranslationY(mFlexibleSpaceView, -scrollY);
		int adjustedScrollY = (int) ScrollUtils.getFloat(scrollY, 0, mFlexibleSpaceHeight);
		float maxScale = (float) (mFlexibleSpaceHeight - mToolbarView.getHeight()) / mToolbarView.getHeight();
		float scale = maxScale * ((float) mFlexibleSpaceHeight - adjustedScrollY) / mFlexibleSpaceHeight;

		ViewHelper.setPivotX(mTitleView, 0);
		ViewHelper.setPivotY(mTitleView, 0);
		ViewHelper.setScaleX(mTitleView, 1 + scale);
		ViewHelper.setScaleY(mTitleView, 1 + scale);
		int maxTitleTranslationY = mToolbarView.getHeight() + mFlexibleSpaceHeight - (int) (mTitleView.getHeight() * (1 + scale));
		int titleTranslationY = (int) (maxTitleTranslationY * ((float) mFlexibleSpaceHeight - adjustedScrollY) / mFlexibleSpaceHeight);
		ViewHelper.setTranslationY(mTitleView, titleTranslationY);
	}

	//// TODO: 16/4/12 kaiguan
}
