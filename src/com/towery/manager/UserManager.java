package com.towery.manager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.towery.beans.userinfo;
import com.towery.database.SDDataBaseHandler;
import com.towery.database.SdCardDBHelper;

public class UserManager {
	public Context context;
	public SdCardDBHelper dbHelper;
	private SDDataBaseHandler sDDataBaseHandler;
	public UserManager(Context context) {
		sDDataBaseHandler = new SDDataBaseHandler(context);
	}
	
	public void insert(List<userinfo> data) {
		List<ContentValues> list = new ArrayList<ContentValues>();
		for (userinfo userinfo:data) {
			ContentValues values = new ContentValues();
			values.put("RecNo", userinfo.getRecNo());
			values.put("userid", userinfo.getUserid());
			values.put("userpswd", userinfo.getUserpswd());
			values.put("username", userinfo.getUsername());	
		}
		sDDataBaseHandler.insert2(SdCardDBHelper.DATABASE_USERINFO, list);		
	}
}