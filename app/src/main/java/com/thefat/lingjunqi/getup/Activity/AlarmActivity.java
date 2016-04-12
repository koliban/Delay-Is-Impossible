package com.thefat.lingjunqi.getup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.thefat.lingjunqi.getup.Adapter.AlarmListAdapter;
import com.thefat.lingjunqi.getup.Alarm;
import com.thefat.lingjunqi.getup.Database.Database;
import com.thefat.lingjunqi.getup.R;

import java.util.List;

/**
 * @author junqi.ling@gmail.com
 */
public class AlarmActivity extends BaseActivity {

	private FloatingActionButton mAddAlarm;
	private AlarmListAdapter mAlarmListAdapter;
	private ListView mAlarmListView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		mAlarmListView = (ListView)findViewById(R.id.alarm_list);
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
				Alarm alarm = (Alarm)mAlarmListAdapter.getItem(position);
				Intent intent = new Intent(AlarmActivity.this,AlarmPreferencesActivity.class);
				intent.putExtra("alarm",alarm);
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

	@Override
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

	public void updateAlarmList(){
		Database.init(AlarmActivity.this);
		final List<Alarm>alarms = Database.getAll();
		mAlarmListAdapter.setAlarms(alarms);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				//reload content
				AlarmActivity.this.mAlarmListAdapter.notifyDataSetChanged();
				if (alarms.size()>0){
					//findViewById(android.R.id.empty).setVisibility(View.INVISIBLE);
				}else {
					//findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	//// TODO: 16/4/12 kaiguan
}
