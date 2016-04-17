package com.thefat.lingjunqi.getup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import com.thefat.lingjunqi.getup.alert.AlarmAlertBroadcastReceiver;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author junqi.ling@gmail.com
 */
public class Alarm implements Serializable {

	public enum Difficulty {
		EASY,
		MEDIUM,
		HARD;

		@Override
		public String toString() {
			switch (this.ordinal()) {
				case 0:
					return "简单";
				case 1:
					return "中等";
				case 2:
					return "困难";
			}
			return super.toString();
		}
	}

	public enum Day {
		SUNDAY,
		MONDAY,
		TUESDAY,
		WEDNESDAY,
		THURSDAY,
		FRIDAY,
		SATURDAY;

		@Override
		public String toString() {
			switch (this.ordinal()) {
				case 0:
					return "周日";
				case 1:
					return "周一";
				case 2:
					return "周二";
				case 3:
					return "周三";
				case 4:
					return "周四";
				case 5:
					return "周五";
				case 6:
					return "周六";
			}
			return super.toString();
		}

	}

	private static final long serialVersionUID = 8699489847426803789L;
	private int id;
	private Boolean alarmActive = true;
	private Calendar alarmTime = Calendar.getInstance();
	private Day[] days = {Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY};
	private String alarmTonePath = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString();
	private Boolean vibrate = true;
	private String alarmName = "未命名闹钟";
	private Difficulty difficulty = Difficulty.EASY;
	private String phoneNumber = "请输入监督人号码";

	public Alarm() {

	}

	/**
	 * @return the alarmActive
	 */
	public Boolean getAlarmActive() {
		return alarmActive;
	}

	/**
	 * @param alarmActive the alarmActive to set
	 */
	public void setAlarmActive(Boolean alarmActive) {
		this.alarmActive = alarmActive;
	}

	/**
	 * @return the alarmTime
	 */
	public Calendar getAlarmTime() {
		if (alarmTime.before(Calendar.getInstance()))
			alarmTime.add(Calendar.DAY_OF_MONTH, 1);
		while (!Arrays.asList(getDays()).contains(Day.values()[alarmTime.get(Calendar.DAY_OF_WEEK) - 1])) {
			alarmTime.add(Calendar.DAY_OF_MONTH, 1);
		}
		return alarmTime;
	}

	/**
	 * @return the alarmTime
	 */
	public String getAlarmTimeString() {

		String time = "";
		if (alarmTime.get(Calendar.HOUR_OF_DAY) <= 9)
			time += "0";
		time += String.valueOf(alarmTime.get(Calendar.HOUR_OF_DAY));
		time += ":";

		if (alarmTime.get(Calendar.MINUTE) <= 9)
			time += "0";
		time += String.valueOf(alarmTime.get(Calendar.MINUTE));

		return time;
	}

	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(Calendar alarmTime) {
		this.alarmTime = alarmTime;
	}

	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(String alarmTime) {

		String[] timePieces = alarmTime.split(":");

		Calendar newAlarmTime = Calendar.getInstance();
		newAlarmTime.set(Calendar.HOUR_OF_DAY,
				Integer.parseInt(timePieces[0]));
		newAlarmTime.set(Calendar.MINUTE, Integer.parseInt(timePieces[1]));
		newAlarmTime.set(Calendar.SECOND, 0);
		setAlarmTime(newAlarmTime);
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the repeatDays
	 */
	public Day[] getDays() {
		return days;
	}

	public void setDays(Day[] days) {
		this.days = days;
	}

	public void addDay(Day day) {
		boolean contains = false;
		for (Day d : getDays())
			if (d.equals(day))
				contains = true;
		if (!contains) {
			List<Day> result = new LinkedList<>();
			for (Day d : getDays())
				result.add(d);
			result.add(day);
			setDays(result.toArray(new Day[result.size()]));
		}
	}

	public void removeDay(Day day) {

		List<Day> result = new LinkedList<>();
		for (Day d : getDays())
			if (!d.equals(day))
				result.add(d);
		setDays(result.toArray(new Day[result.size()]));
	}

	/**
	 * @return the alarmTonePath
	 */
	public String getAlarmTonePath() {
		return alarmTonePath;
	}

	/**
	 * @param alarmTonePath the alarmTonePath to set
	 */
	public void setAlarmTonePath(String alarmTonePath) {
		this.alarmTonePath = alarmTonePath;
	}

	/**
	 * @return the vibrate
	 */
	public Boolean getVibrate() {
		return vibrate;
	}

	/**
	 * @param vibrate the vibrate to set
	 */
	public void setVibrate(Boolean vibrate) {
		this.vibrate = vibrate;
	}

	/**
	 * @return the alarmName
	 */
	public String getAlarmName() {
		return alarmName;
	}

	/**
	 * @param alarmName the alarmName to set
	 */
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRepeatDaysString() {
		StringBuilder daysStringBuilder = new StringBuilder();
		if (getDays().length == Day.values().length) {
			daysStringBuilder.append("每天");
		} else {
			Arrays.sort(getDays(), new Comparator<Day>() {
				@Override
				public int compare(Day lhs, Day rhs) {

					return lhs.ordinal() - rhs.ordinal();
				}
			});

			for (Day d : getDays()) {
				switch (d) {
					case SUNDAY:
						daysStringBuilder.append("周日");
						break;
					case MONDAY:
						daysStringBuilder.append("周一");
						break;
					case TUESDAY:
						daysStringBuilder.append("周二");
						break;
					case WEDNESDAY:
						daysStringBuilder.append("周三");
						break;
					case THURSDAY:
						daysStringBuilder.append("周四");
						break;
					case FRIDAY:
						daysStringBuilder.append("周五");
						break;
					case SATURDAY:
						daysStringBuilder.append("周六");
						break;
					default:
						break;
				}
				daysStringBuilder.append(", ");
			}
			daysStringBuilder.setLength(daysStringBuilder.length() - 2);
		}

		return daysStringBuilder.toString();
	}

	public void schedule(Context context) {
		setAlarmActive(true);

		Intent myIntent = new Intent(context, AlarmAlertBroadcastReceiver.class);
		myIntent.putExtra("alarm", this);

		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		alarmManager.setExact(AlarmManager.RTC_WAKEUP, getAlarmTime().getTimeInMillis(), pendingIntent);
	}

	public String getTimeUntilNextAlarmMessage() {
		long timeDifference = getAlarmTime().getTimeInMillis() - System.currentTimeMillis();
		long days = timeDifference / (1000 * 60 * 60 * 24);
		long hours = (timeDifference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60);
		timeDifference %= 1000 * 60 * 60;
		long minutes = timeDifference / (1000 * 60);
		timeDifference %= 1000 * 60;
		long seconds = timeDifference / 1000;
		String alert = "闹钟会在 ";
		if (days > 0) {
			alert += String.format(
					"%d 天, %d 小时, %d 分钟 ", days,
					hours, minutes);
		} else {
			if (hours > 0) {
				alert += String.format("%d 小时, %d 分钟 ",
						hours, minutes);
			} else {
				if (minutes > 0) {
					alert += String.format("%d 分钟", minutes,
							seconds);
				} else {
					alert += String.format("%d 秒", seconds);
				}
			}
		}
		alert += "后响起";
		return alert;
	}
}
