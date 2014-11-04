package com.rae.placetobe.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsersDao implements Users
{
	private static final String TAG = UsersDao.class.getSimpleName();
	
	public static final String[] PROJECTION =  {
	    COLUMN_NAME,   // 0
	    COLUMN_EMAIL,  // 1
	    COLUMN_USER_ID // 2
	};

	
	public static Cursor findAll(Context context, String[] selectionArgs)  {
		Log.d(TAG, "findAll()") ;
		SQLiteDatabase db = PlaceToBeDBHelper.getInstance(context).getReadableDatabase() ;
		return db.query(TABLE_NAME, PROJECTION, null, null, null, null, COLUMN_NAME);
	}

	public static long insert(Context context, ContentValues values) {
		SQLiteDatabase db = PlaceToBeDBHelper.getInstance(context).getWritableDatabase() ;		
		values.remove(_ID) ; // Safer
		return db.insert(TABLE_NAME, null, values);
	}

	public static int update(Context context, String id, ContentValues values) {
		SQLiteDatabase db = PlaceToBeDBHelper.getInstance(context).getWritableDatabase() ;	
		values.remove(_ID) ; // Safer
		return db.update(TABLE_NAME, values, _ID+"="+id, null);
	}

	public static int delete(Context context, String ids) {
		SQLiteDatabase db = PlaceToBeDBHelper.getInstance(context).getWritableDatabase() ;
		String whereClause = _ID + " IN ("+ids+")" ;
		return db.delete(TABLE_NAME, whereClause, null);
	}
}
