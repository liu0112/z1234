package com.towery.utils;

import java.io.UnsupportedEncodingException;

import android.R.string;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

public class Utils {

	public static String charset = "gbk";

	public static String conversion(byte[] blob) {

		try {
			return new String(blob, charset).trim();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String conversion(Cursor cursor, String string) {

		if (cursor.getType(cursor.getColumnIndex(string)) == Cursor.FIELD_TYPE_NULL) {
			return null;
		} else if (cursor.getType(cursor.getColumnIndex(string)) == Cursor.FIELD_TYPE_BLOB) {
			return conversion(cursor.getBlob(cursor.getColumnIndex(string)));
		} else if (cursor.getType(cursor.getColumnIndex(string)) == Cursor.FIELD_TYPE_STRING) {
			return conversion(cursor.getBlob(cursor.getColumnIndex(string)));
		}else {
			return cursor.getString(cursor.getColumnIndex(string));
		}

		// try {
		// if (cursor.getString(cursor.getColumnIndex(string)) == null) {
		// return null;
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		// try {
		// return conversion(cursor.getBlob(cursor.getColumnIndex(string)));
		// } catch (SQLiteException e) {
		// return cursor.getString(cursor.getColumnIndex(string));
		// }
	}
	public static byte[] putbyte(String string){
		
		try {
			if(string==null)
				return null;
			return  string.getBytes("gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return null;
		}
	
		
	}
}
