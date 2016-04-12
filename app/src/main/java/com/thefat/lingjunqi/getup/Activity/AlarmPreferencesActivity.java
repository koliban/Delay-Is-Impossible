package com.thefat.lingjunqi.getup.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.thefat.lingjunqi.getup.R;

/**
 * @author junqi.ling@gmail.com
 */
public class AlarmPreferencesActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm_preferences);
	}

	public static Intent createIntent(Context context) {

		Intent intent = new Intent(context, AlarmPreferencesActivity.class);
		return intent;
	}

}
