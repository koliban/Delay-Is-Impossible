package com.thefat.lingjunqi.getup.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thefat.lingjunqi.getup.Activity.AlarmActivity;
import com.thefat.lingjunqi.getup.Alarm;
import com.thefat.lingjunqi.getup.Database.Database;
import com.thefat.lingjunqi.getup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author junqi.ling@gmail.com
 */
public class AlarmListAdapter extends BaseAdapter {

	private AlarmActivity alarmActivity;
	private List<Alarm> alarms = new ArrayList<>();

	public static final String ALARM_FIELDS[] = {Database.COLUMN_ALARM_ACTIVE, Database.COLUMN_ALARM_TIME,
			Database.COLUMN_ALARM_DAYS};

	public AlarmListAdapter(AlarmActivity alarmActivity) {
		this.alarmActivity = alarmActivity;
	}

	@Override
	public int getCount() {
		return alarms.size();
	}

	@Override
	public Object getItem(int position) {
		return alarms.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = LayoutInflater.from(alarmActivity).inflate(
					R.layout.item_expandable_list, null);

		Alarm alarm = (Alarm) getItem(position);
		TextView alarmTime = (TextView) convertView.findViewById(R.id.iv_alarm_time);
		alarmTime.setText(alarm.getAlarmTimeString());

		TextView alarmDays = (TextView) convertView.findViewById(R.id.iv_alarm_days);
		alarmDays.setText(alarm.getRepeatDaysString());

		return convertView;
	}

	public List<Alarm> getAlarms() {
		return alarms;
	}

	public void setAlarms(List<Alarm> alarms) {
		this.alarms = alarms;
	}
}
