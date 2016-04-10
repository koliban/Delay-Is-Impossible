package com.thefat.lingjunqi.getup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * @author junqi.ling@gmail.com
 */
public class AlarmActivity extends BaseActivity {
	private FloatingActionButton mAddAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);

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

}
