package com.rae.placetobe.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rae.placetobe.sqlite.PlaceToBeContract.Users;

public final class PlaceToBeHelper extends SQLiteOpenHelper
{
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "PlaceToBe";
	
	private static final String SQL_CREATE_USERS = "CREATE TABLE "
			+ Users.TABLE_NAME + " (" + Users._ID + " INTEGER PRIMARY KEY,"
			+ Users.COLUMN_NAME_USER_ID + " TEXT," 
			+ Users.COLUMN_NAME_NAME + " TEXT," 
			+ Users.COLUMN_NAME_EMAIL + " TEXT," 
			+ Users.COLUMN_NAME_FOLLOWED + " INTEGER," 
			+ Users.COLUMN_NAME_FOLLOWERS + " INTEGER" 
			+ " )";
	
	private static String deleteTable = "DROP TABLE IF EXISTS "+ Users.TABLE_NAME + ";";

	public PlaceToBeHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(SQL_CREATE_USERS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(deleteTable);
		onCreate(db);

	}
		
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onUpgrade(db, oldVersion, newVersion);
	}

}