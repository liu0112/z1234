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
	public SDDataBaseHandler(Context context){
		super();
		this.context=context;
		DatabaseContext dbContext = new DatabaseContext(context);
		dbHelper = new SdCardDBHelper(dbContext);
	}
	
	
	public void insert(List<userinfo> data) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		for (userinfo userinfo:data) {
			ContentValues values = new ContentValues();
			values.put("RecNo", userinfo.getRecNo());
			values.put("userid", userinfo.getUserid());
			values.put("userpswd", userinfo.getUserpswd());
			values.put("username", userinfo.getUsername());	
			db.insert(SdCardDBHelper.DATABASE_USERINFO, null, values);		
		}
		db.close();
	}
	
	public void insert2(String tableName,List<ContentValues> contentValueList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		for (ContentValues values:contentValueList) {
			db.insert(tableName, null, values);		
		}
		db.close();
	}
	public List<userinfo> query(){
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ArrayList<userinfo> list = new ArrayList<userinfo>();
		Cursor cursor = db.query(SdCardDBHelper.DATABASE_USERINFO, null, null, null,
				null, null, null, null);
		
		if (cursor.getCount()<=0) {
			
			return list;
		}
		
		while (cursor.moveToNext()) {
			userinfo userinfo = new userinfo();
			userinfo.setRecNo(cursor.getString(cursor.getColumnIndex("RecNo")));
			userinfo.setUserid(cursor.getString(cursor.getColumnIndex("userid")));
			userinfo.setUsername(cursor.getString(cursor.getColumnIndex("username")));
			userinfo.setUserpswd(cursor.getString(cursor.getColumnIndex("userpswd")));
			list.add(userinfo);
		}
		
		db.close();
		return list;
		
	}
}
