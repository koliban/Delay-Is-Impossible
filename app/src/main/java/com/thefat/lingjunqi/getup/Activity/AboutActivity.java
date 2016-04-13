package com.thefat.lingjunqi.getup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.thefat.lingjunqi.getup.R;

/**
 * @author junqi.ling@gmail.com
 */
public class AboutActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_about);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_about_us,menu);
		return true;
	}

	public static Intent createIntent(Context context){
		Intent intent = new Intent(context,AboutActivity.class);
		return intent;
	}
}
