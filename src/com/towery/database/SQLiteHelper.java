package com.towery.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

	public static int MYDATA_VERSION = 1;
	public static String MYDATA_NAME = "sdata";
	public static String MYDATA_SETUP = "setup";

	public SQLiteHelper(Context context) {
		super(context, MYDATA_NAME, null, MYDATA_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL("create table if not exists setup"
				+ "(_id integer primary key autoincrement,userid varchar(20),djms varchar(20),tzms varchar(20),dwrwq varchar(20),xzbz varchar(20),xsbz varchar(20),qhrwdw varchar(20),xspz varchar(20),sjkgx varchar(20),sjksj varchar(20))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
