package com.towery.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.towery.beans.MediaData;
import com.towery.beans.QualifyData;
import com.towery.beans.TaskInfo;
import com.towery.database.SDDataBaseHandler;
import com.towery.database.SdCardDBHelper;
import com.towery.utils.Utils;

public class MediadataManager {
	public Context context;
	public SdCardDBHelper dbHelper;
	private SDDataBaseHandler sDDataBaseHandler;

	public MediadataManager(Context context) {
		sDDataBaseHandler = new SDDataBaseHandler(context);
	}

	public void insert(List<MediaData> data) {
		List<ContentValues> list = new ArrayList<ContentValues>();
		for (MediaData mediaData : data) {
			ContentValues values = new ContentValues();
			values.put("taskid", mediaData.getTaskid());
			values.put("questionid", mediaData.getQuestionid());
			values.put("filename", mediaData.getFilename());
			values.put("filePath", mediaData.getFilepath());
			values.put("taskname", mediaData.getTaskname());
			list.add(values);
		}
		sDDataBaseHandler.insert2(SdCardDBHelper.DATABASE_MEDIADATA, list);
	}

	public List<MediaData> query() {
		Cursor cursor = sDDataBaseHandler.query(
				SdCardDBHelper.DATABASE_MEDIADATA, null, null, null, null,
				null, null, null);
		ArrayList<MediaData> list = new ArrayList<MediaData>();
		if (cursor == null) {
			return null;
		}

		while (cursor.moveToNext()) {
			MediaData mediaData = new MediaData();
			mediaData.setTaskid(Utils.conversion(cursor, "taskid"));
			mediaData.setTaskid(Utils.conversion(cursor, "taskid"));
			mediaData.setQuestionid(Utils.conversion(cursor, "questionid"));
			mediaData.setFilename(Utils.conversion(cursor, "filename"));
			mediaData.setFilepath(Utils.conversion(cursor, "filepath"));
			mediaData.setTaskname(Utils.conversion(cursor, "taskname"));
			list.add(mediaData);
		}
		cursor.close();
		return list;

	}

	public List<MediaData> query(String line, String string) {
		Cursor cursor = sDDataBaseHandler.query(
				SdCardDBHelper.DATABASE_MEDIADATA, null, line + "=?",
				new String[] { string }, null, null, null, null);
		ArrayList<MediaData> list = new ArrayList<MediaData>();
		if (cursor == null) {
			return null;
		}

		while (cursor.moveToNext()) {
			MediaData mediaData = new MediaData();
			mediaData.setTaskid(Utils.conversion(cursor, "taskid"));
			mediaData.setTaskid(Utils.conversion(cursor, "taskid"));
			mediaData.setQuestionid(Utils.conversion(cursor, "questionid"));
			mediaData.setFilename(Utils.conversion(cursor, "filename"));
			mediaData.setFilepath(Utils.conversion(cursor, "filepath"));
			mediaData.setTaskname(Utils.conversion(cursor, "taskname"));
			list.add(mediaData);
		}
		cursor.close();
		return list;

	}
	public void delete(String whereClause, String[] whereArgs){
		sDDataBaseHandler.delete(SdCardDBHelper.DATABASE_MEDIADATA, whereClause+"=?", whereArgs);
		
	}
	// public String queryMax(String s) {
	// Cursor cursor = sDDataBaseHandler.queryMax(
	// "select max(t.questionid) from mediadata t where t.taskid = ?",
	// new String[] { s });
	// if (cursor == null) {
	// return null;
	// }
	// while (cursor.moveToNext()) {
	// if (cursor.getString(0) == null) {
	// return null;
	// } else {
	// return Utils.conversion(cursor.getBlob(0));
	// }
	//
	// }
	// return null;
	//
	//
	// }
}
