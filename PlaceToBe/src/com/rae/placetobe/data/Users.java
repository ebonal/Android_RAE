package com.rae.placetobe.data;


public interface Users extends PtbColumns
{
    String TABLE_NAME = "users";

    String COLUMN_ID      = _ID ;
    String COLUMN_USER_ID = "userid";
    String COLUMN_NAME    = "name";
    String COLUMN_EMAIL   = "email";

	String  CREATE_TABLE_STATEMENT =
		    "CREATE TABLE "+ TABLE_NAME + " (" +
		    COLUMN_ID      + TYPE_INTEGER + " PRIMARY KEY," +
		    COLUMN_USER_ID + TYPE_INTEGER + COMMA_SEP +
		    COLUMN_EMAIL   + TYPE_TEXT + COMMA_SEP +
		    COLUMN_NAME    + TYPE_TEXT +  " )";
}
