package com.towery.manager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.towery.beans.CollectData;
import com.towery.beans.MediaData;
import com.towery.beans.userinfo;
import com.towery.database.SDDataBaseHandler;
import com.towery.database.SdCardDBHelper;
import com.towery.utils.Utils;

public class CollectDataManager {
	public Context context;
	public SdCardDBHelper dbHelper;
	private SDDataBaseHandler sDDataBaseHandler;

	public CollectDataManager(Context context) {
		sDDataBaseHandler = new SDDataBaseHandler(context);
	}

	public void insert(List<CollectData> data) {
		List<ContentValues> list = new ArrayList<ContentValues>();
		for (CollectData collectdata : data) {
			ContentValues values = new ContentValues();
			values.put("taskid", collectdata.getTaskid());
//			String ques = collectdata.getQuestionid();
//			byte[] a = Utils.putbyte(ques);
			 values.put("questionid", collectdata.getQuestionid());
//			values.put("questionid", a);
			values.put("content", Utils.putbyte(collectdata.getContent()));
			values.put("answer", Utils.putbyte(collectdata.getAnswer()));
			values.put("x", Utils.putbyte(collectdata.getX()));
			values.put("y", Utils.putbyte(collectdata.getY()));
			values.put("GPSx", Utils.putbyte(collectdata.getGPSx()));
			values.put("GPSy", Utils.putbyte(collectdata.getGPSy()));
			values.put("GPStime", collectdata.getGPStime());
			values.put("remark", Utils.putbyte(collectdata.getRemark()));
			values.put("syscollectuuid",
					Utils.putbyte(collectdata.getSyscollectuuid()));
			list.add(values);
		}
		sDDataBaseHandler.insert2(SdCardDBHelper.DATABASE_COLLECTDATA, list);
	}

	public List<CollectData> query(String[] str, String line, String string ,String orderBy) {
		Cursor cursor = sDDataBaseHandler.query(
				SdCardDBHelper.DATABASE_COLLECTDATA, str, line + "=?",
				new String[] { string }, null, null, orderBy, null);
		ArrayList<CollectData> list = new ArrayList<CollectData>();
		if (cursor == null) {

			return null;
		}
		while (cursor.moveToNext()) {
			CollectData collectdata = new CollectData();
			collectdata.setTaskid(Utils.conversion(cursor, "taskid"));
			collectdata.setQuestionid(Utils.conversion(cursor, "questionid"));
			collectdata.setContent(Utils.conversion(cursor, "content"));
			collectdata.setAnswer(Utils.conversion(cursor, "answer"));
			collectdata.setX(Utils.conversion(cursor, "x"));
			collectdata.setY(Utils.conversion(cursor, "y"));
			collectdata.setGPSx(Utils.conversion(cursor, "GPSx"));
			collectdata.setGPSy(Utils.conversion(cursor, "GPSy"));
			collectdata.setGPStime(Utils.conversion(cursor, "GPStime"));
			collectdata.setRemark(Utils.conversion(cursor, "remark"));
			collectdata.setSyscollectuuid(Utils.conversion(cursor,
					"syscollectuuid"));
			list.add(collectdata);
		}
		cursor.close();
		return list;

	}
	public List<CollectData> query() {
		Cursor cursor = sDDataBaseHandler.query(
				SdCardDBHelper.DATABASE_COLLECTDATA, null, null,
				null, null, null, "questionid DESC", null);
		ArrayList<CollectData> list = new ArrayList<CollectData>();
		if (cursor == null) {

			return null;
		}
		while (cursor.moveToNext()) {
			CollectData collectdata = new CollectData();
			collectdata.setTaskid(Utils.conversion(cursor, "taskid"));
			collectdata.setQuestionid(Utils.conversion(cursor, "questionid"));
			collectdata.setContent(Utils.conversion(cursor, "content"));
			collectdata.setAnswer(Utils.conversion(cursor, "answer"));
			collectdata.setX(Utils.conversion(cursor, "x"));
			collectdata.setY(Utils.conversion(cursor, "y"));
			collectdata.setGPSx(Utils.conversion(cursor, "GPSx"));
			collectdata.setGPSy(Utils.conversion(cursor, "GPSy"));
			collectdata.setGPStime(Utils.conversion(cursor, "GPStime"));
			collectdata.setRemark(Utils.conversion(cursor, "remark"));
			collectdata.setSyscollectuuid(Utils.conversion(cursor,
					"syscollectuuid"));
			list.add(collectdata);
		}
        cursor.close();
		return list;

	}

	public void update(ContentValues values, String whereClause,
			String[] whereArgs) {
		sDDataBaseHandler.update(SdCardDBHelper.DATABASE_COLLECTDATA, values,
				whereClause, whereArgs);
	}

	public void delete(String whereClause, String[] whereArgs) {
		sDDataBaseHandler.delete(SdCardDBHelper.DATABASE_COLLECTDATA,
				whereClause + "=?", whereArgs);
	}

}
