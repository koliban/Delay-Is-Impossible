package com.thefat.lingjunqi.getup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.thefat.lingjunqi.getup.adapter.AlarmListAdapter;
import com.thefat.lingjunqi.getup.Alarm;
import com.thefat.lingjunqi.getup.database.Database;
import com.thefat.lingjunqi.getup.R;

import java.util.List;

/**
 * @author junqi.ling@gmail.com
 */
public class AlarmActivity extends BaseActivity implements View.OnClickListener{

	ListView mathAlarmListView;
	AlarmListAdapter alarmListAdapter;
	private FloatingActionButton mFAB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);

		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

		mFAB = (FloatingActionButton)findViewById(R.id.btn_add_alarm);
		mFAB.setOnClickListener(this);

		mathAlarmListView = (ListView) findViewById(R.id.alarm_list);
		//mathAlarmListView.setLongClickable(true);
		mathAlarmListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

				view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
				final Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
				AlertDialog.Builder dialog = new AlertDialog.Builder(AlarmActivity.this);
				dialog.setTitle("删除闹钟");
				dialog.setMessage("删除这个闹钟?");
				dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						Database.init(AlarmActivity.this);
						Database.deleteEntry(alarm);
						AlarmActivity.this.callAlarmScheduleService();

						updateAlarmList();
					}
				});
				dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

				dialog.show();

				return true;
			}
		});

		callAlarmScheduleService();

		alarmListAdapter = new AlarmListAdapter(this);
		this.mathAlarmListView.setAdapter(alarmListAdapter);
		mathAlarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
				v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
				Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
				Intent intent = new Intent(AlarmActivity.this, AlarmPreferencesActivity.class);
				intent.putExtra("alarm", alarm);
				startActivity(intent);
			}

		});
	}


	@Override
	protected void onPause() {
		// setListAdapter(null);
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
		alarmListAdapter.setMathAlarms(alarms);

		runOnUiThread(new Runnable() {
			public void run() {
				// reload content
				AlarmActivity.this.alarmListAdapter.notifyDataSetChanged();
				if (alarms.size() > 0) {
					findViewById(R.id.container_no_alarm).setVisibility(View.INVISIBLE);
				} else {
					findViewById(R.id.container_no_alarm).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.checkBox_alarm_active) {
			CheckBox checkBox = (CheckBox) v;
			Alarm alarm = (Alarm) alarmListAdapter.getItem((Integer) checkBox.getTag());
			alarm.setAlarmActive(checkBox.isChecked());
			Database.update(alarm);
			AlarmActivity.this.callAlarmScheduleService();
			if (checkBox.isChecked()) {
				Toast.makeText(AlarmActivity.this, alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
			}
		}
		if (v.getId()==R.id.btn_add_alarm){
			startActivity(AlarmPreferencesActivity.createIntent(AlarmActivity.this));
		}

	}

}