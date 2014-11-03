package com.rae.placetobe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlaceToBeDBHelper extends SQLiteOpenHelper
{
    // Bump this for each change in the schema
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PlaceTobe";

    private static final String TYPE_TEXT    = " TEXT";
	private static final String TYPE_INTEGER = " INTEGER";
	
	private static final String COMMA_SEP = ",";
	
    public PlaceToBeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	private static final String SQL_CREATE_USERS_TABLE =
		    "CREATE TABLE " + Users.TABLE_NAME + " (" +
		    Users._ID + " INTEGER PRIMARY KEY," +
		    Users.COLUMN_NAME_USER_ID + TYPE_INTEGER + COMMA_SEP +
		    Users.COLUMN_NAME_EMAIL   + TYPE_TEXT + COMMA_SEP +
		    Users.COLUMN_NAME_NAME    + TYPE_TEXT + COMMA_SEP +
		    " )";


	public void onCreate(SQLiteDatabase db) {
      db.execSQL(SQL_CREATE_USERS_TABLE);
    }

	private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_NAME;

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database upgrade policy is to discard the data
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }
}
