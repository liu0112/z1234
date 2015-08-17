package com.towery.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SdCardDBHelper extends SQLiteOpenHelper {

	public static final String TAG = "SdCardDBHelper";
	/**
	 * 数据库名称
	 **/
	public static String DATABASE_NAME = "task.db";
	/**
	 * 数据库版本
	 **/
	public static int DATABASE_VERSION = 1;
	/**
	 * 表名
	 **/
	public static String DATABASE_USERINFO = "userinfo";

	/**
	 * 构造函数
	 * 
	 * @param context
	 *            上下文环境
	 **/
	public SdCardDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * 创建数据库时触发，创建离线存储所需要的数据库表
	 * 
	 * @param db
	 **/
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.e(TAG, "开始创建数据库表");
		try {
			// 创建用户表(user)
			db.execSQL("create table if not exists userinfo"
					+ "(RecNo integer primary key autoincrement,userid varchar(20),userpswd varchar(20),username varchar(20))");
			Log.e(TAG, "创建离线所需数据库表成功");
		} catch (SQLException se) {
			se.printStackTrace();
			Log.e(TAG, "创建离线所需数据库表失败");
		}
	}

	/**
	 * 更新数据库时触发，
	 * 
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 **/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
	}
}
