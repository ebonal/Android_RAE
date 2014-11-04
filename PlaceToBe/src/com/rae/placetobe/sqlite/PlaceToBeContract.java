package com.rae.placetobe.sqlite;

import android.provider.BaseColumns;


public final class PlaceToBeContract
{
	// empty constructor for not instantiating the contract class
	private PlaceToBeContract() {}
	
	public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USER_ID = "userid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_FOLLOWED = "followed";
        public static final String COLUMN_NAME_FOLLOWERS = "followers";
    }

}
