package com.rae.placetobe.data;

public class UserDDL
{
	private static final String SQL_CREATE_USERS =
		    "CREATE TABLE " + Users.TABLE_NAME + " (" +
		    Users._ID + " INTEGER PRIMARY KEY," +
		    Users.COLUMN_NAME_USER_ID + " TEXT," +
		    Users.COLUMN_NAME_NAME + " TEXT, " +
		    // Any other options for the CREATE command
		    " )";
}
