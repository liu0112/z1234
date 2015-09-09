package com.towery.database;

import java.util.ArrayList;
import java.util.List;

import com.towery.beans.SetUp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseHandler {
	private Context context;
	private SQLiteHelper sqLiteHelper;

	public DataBaseHandler(Context context) {
		super();
		this.context = context;
		sqLiteHelper = new SQLiteHelper(context);
	}

	public void insert(List<SetUp> data) {
		SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
		for (SetUp setUp : data) {
			ContentValues values = new ContentValues();
			values.put("userid", setUp.getUserid());
			values.put("djms", setUp.getDjms());
			values.put("tzms", setUp.getTzms());
			values.put("dwrwq", setUp.getDwrwq());
			values.put("xzbz", setUp.getXzbz());
			values.put("xsbz", setUp.getXsbz());
			values.put("qhrwdw", setUp.getQhrwdw());
			values.put("xspz", setUp.getXspz());
			values.put("sjkgx", setUp.getSjkgx());
			values.put("sjksj", setUp.getSjksj());
			db.insert(SQLiteHelper.MYDATA_SETUP, null, values);
		}
		db.close();
	}

	public ArrayList<SetUp> query() {
		SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
		ArrayList<SetUp> list = new ArrayList<SetUp>();
		Cursor cursor = db.query(SQLiteHelper.MYDATA_SETUP, null, null,
				null, null, null, null, null);

		if (cursor.getCount() <= 0) {

			return list;
		}

		while (cursor.moveToNext()) {
			SetUp setUp  = new SetUp();
			setUp.setDjms(cursor.getString(cursor.getColumnIndex("userid")));
			setUp.setDjms(cursor.getString(cursor.getColumnIndex("djms")));
			setUp.setTzms(cursor.getString(cursor.getColumnIndex("tzms")));
			setUp.setDwrwq(cursor.getString(cursor.getColumnIndex("dwrwq")));
			setUp.setXzbz(cursor.getString(cursor.getColumnIndex("xzbz")));
			setUp.setXsbz(cursor.getString(cursor.getColumnIndex("xsbz")));
			setUp.setQhrwdw(cursor.getString(cursor.getColumnIndex("qhrwdw")));
			setUp.setXspz(cursor.getString(cursor.getColumnIndex("xspz")));
			setUp.setSjkgx(cursor.getString(cursor.getColumnIndex("sjkgx")));
			setUp.setSjksj(cursor.getString(cursor.getColumnIndex("sjksj")));
			
			list.add(setUp);
		}

		db.close();
		return list;

	}
	public void update( ContentValues values, String whereClause, String[] whereArgs){
		SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
		db.update(SQLiteHelper.MYDATA_SETUP, values, whereClause, whereArgs);
		db.close();
	}
}
