package com.thefat.lingjunqi.getup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author junqi.ling@gmail.com
 */
public class Database extends SQLiteOpenHelper {
	static Database instance = null;
	static SQLiteDatabase database = null;

	static final String DATABASE_NAME = "DB";
	static final int DATABASE_VERSION = 1;

	public static final String ALARM_TABLE = "alarm";
	public static final String COLUMN_ALARM_ID = "_id";
	public static final String COLUMN_ALARM_ACTIVE = "alarm_active";
	public static final String COLUMN_ALARM_TIME = "alarm_time";
	public static final String COLUMN_ALARM_DAYS = "alarm_days";
	public static final String COLUMN_ALARM_DIFFICULTY = "alarm_difficulty";
	public static final String COLUMN_ALARM_TONE = "alarm_tone";
	public static final String COLUMN_ALARM_VIBRATE = "alarm_vibrate";
	public static final String COLUMN_ALARM_NAME = "alarm_name";


	public static void init(Context context){
		if (instance==null){
			instance = new Database(context);
		}
	}

	public static SQLiteDatabase getDatabase(){
		if (database==null){
			database=instance.getWritableDatabase();
		}
		return database;
	}

	public static void deactivate(){
		if (database!=null&&database.isOpen()){
			database.close();
		}
		database = null;
		instance = null;
	}

	/*public static long create(Alarm alarm){

	}*/

}
