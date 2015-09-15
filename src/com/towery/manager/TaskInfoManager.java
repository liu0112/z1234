package com.towery.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.towery.beans.TaskInfo;
import com.towery.beans.userinfo;
import com.towery.database.SDDataBaseHandler;
import com.towery.database.SdCardDBHelper;
import com.towery.utils.Utils;

public class TaskInfoManager {
	public Context context;
	public SdCardDBHelper dbHelper;
	private SDDataBaseHandler sDDataBaseHandler;

	public TaskInfoManager(Context context) {
		sDDataBaseHandler = new SDDataBaseHandler(context);
	}
	public void insert(List<TaskInfo> data) {
		List<ContentValues> list = new ArrayList<ContentValues>();
		for (TaskInfo taskInfo : data) {
			ContentValues values = new ContentValues();
			values.put("taskid", taskInfo.getTaskid());
			values.put("userid", taskInfo.getUserid());
			values.put("taskdesc", taskInfo.getTaskdesc());
			values.put("tasktype", taskInfo.getTasktype());
			values.put("taskstatus", taskInfo.getTaskstatus());
			values.put("taskfinish", taskInfo.getTaskfinish());
			values.put("taskname", taskInfo.getTaskname());
		}
		sDDataBaseHandler.insert2(SdCardDBHelper.DATABASE_TASKINFO, list);
	}
	public List<TaskInfo> query() {
		Cursor cursor = sDDataBaseHandler
				.query(SdCardDBHelper.DATABASE_TASKINFO,null,null,null,null,null,null,null);
		ArrayList<TaskInfo> list = new ArrayList<TaskInfo>();
		if (cursor == null) {

			return null;
		}

		while (cursor.moveToNext()) {
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setTaskid(Utils.conversion(cursor.getBlob(cursor.getColumnIndex("taskid"))));
			taskInfo.setUserid(Utils.conversion(cursor.getBlob(cursor.getColumnIndex("userid"))));
			taskInfo.setTaskdesc(Utils.conversion(cursor.getBlob(cursor.getColumnIndex("taskdesc"))));
			taskInfo.setTasktype(Utils.conversion(cursor.getBlob(cursor.getColumnIndex("tasktype"))));
			taskInfo.setTaskstatus(Utils.conversion(cursor.getBlob(cursor.getColumnIndex("taskstatus"))));
			taskInfo.setTaskfinish(Utils.conversion(cursor.getBlob(cursor.getColumnIndex("taskfinish"))));
			taskInfo.setTaskname(Utils.conversion(cursor.getBlob(cursor.getColumnIndex("taskname"))));
			list.add(taskInfo);
		}
		cursor.close();
		return list;

	}
	public void update(ContentValues values, String whereClause,
			String[] whereArgs) {
		sDDataBaseHandler.update(SdCardDBHelper.DATABASE_TASKINFO, values,
				whereClause, whereArgs);
	}

}
