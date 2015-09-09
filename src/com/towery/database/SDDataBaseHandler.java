package com.towery.database;

import java.util.ArrayList;
import java.util.List;
import com.towery.beans.userinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SDDataBaseHandler {
	public Context context;
	public SdCardDBHelper dbHelper;

	public SDDataBaseHandler(Context context) {
		super();
		this.context = context;
		DatabaseContext dbContext = new DatabaseContext(context);
		dbHelper = new SdCardDBHelper(dbContext);
	}

	public void insert2(String tableName, List<ContentValues> contentValueList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		for (ContentValues values : contentValueList) {
			db.insert(tableName, null, values);
		}
		db.close();
	}

	public Cursor query(String tableName, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy, String limit) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		Cursor cursor = db.query(tableName, columns, selection, selectionArgs,
				groupBy, having, orderBy, limit);
		System.out.println(cursor.getCount()+"==============="+cursor.getCount());
		if (cursor.getCount() <= 0) {
			cursor.close();
			db.close();
			return null;
		}

		db.close();
		return cursor;

	}

	public Cursor queryMax(String sql, String[] args) {

		// "select max(t.questionid) from mediadata t where t.taskid = ?",
		// new String[]{"20141231004130p"}
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		Cursor cursor = db.rawQuery(sql, args);
		if (cursor.getCount() <= 0) {
			cursor.close();
			db.close();
			return null;
		}

		db.close();
		return cursor;

	}

	public void delete(String tableName, String whereClause, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(tableName, whereClause, whereArgs);
		db.close();
	}

	public void update(String table, ContentValues values, String whereClause, String[] whereArgs){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.update(table, values, whereClause, whereArgs);
		db.close();
	}
}
