package com.thefat.lingjunqi.getup.alert;

import android.content.Context;
import android.os.PowerManager;

/**
 * @author junqi.ling@gmail.com
 */
public class StaticWakeLock {
	private static PowerManager.WakeLock wl = null;

	public static void lockOn(Context context) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		if (wl == null)
			wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "MATH_ALARM");
		wl.acquire();
	}

	public static void lockOff(Context context) {
		try {
			if (wl != null)
				wl.release();
		} catch (Exception e) {
		}
	}
}