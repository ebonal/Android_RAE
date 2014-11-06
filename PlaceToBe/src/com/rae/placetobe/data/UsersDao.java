package com.rae.placetobe.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UsersDao implements Users
{
	private static final String TAG = UsersDao.class.getSimpleName();
	
	// The indices for the projection array above.
	
	public static final int IDX__ID     = 0;
	public static final int IDX_NAME    = 1;
	public static final int IDX_EMAIL   = 2;
	public static final int IDX_USER_ID = 3;
	
		
	public static final String[] PROJECTION =  {
	    COLUMN_ID,     // 0
		COLUMN_NAME,   // 1
	    COLUMN_EMAIL,  // 2
	    COLUMN_USER_ID // 3
	};

	
	public static Cursor findAll(Context context, String[] selectionArgs)  {
		Log.d(TAG, "findAll()") ;
		SQLiteDatabase db = PlaceToBeDBHelper.getInstance(context).getReadableDatabase() ;
		return db.query(TABLE_NAME, PROJECTION, null, null, null, null, COLUMN_NAME);
	}
	
	public static Cursor findFollow(Context context, String[] selectionArgs)  {
		Log.d(TAG, "findFollow()");
		String selection;
		
		if (selectionArgs[0].equals(Users.USERS_ARG_VALUE_FOLLOWED))
			selection = COLUMN_FOLLOWED + " = 1";
		else 
			selection = COLUMN_FOLLOWERS + " = 1";
		
		SQLiteDatabase db = PlaceToBeDBHelper.getInstance(context).getReadableDatabase() ;
		return db.query(TABLE_NAME, PROJECTION, selection, null, null, null, COLUMN_NAME);
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
