package com.rae.placetobe.data;


public interface Images extends PtbColumns
{
    String TABLE_NAME = "imagedata";

    String COLUMN_ID      = _ID ;
    String COLUMN_PATH    = "PATH";
    String COLUMN_COMMENT = "COMMENT";
    String COLUMN_DATE    = "DATE";
    
	String  CREATE_TABLE_STATEMENT =
		    "CREATE TABLE " + TABLE_NAME + " (" +
		    _ID             + TYPE_INTEGER + " PRIMARY KEY," +
		    COLUMN_PATH     + TYPE_INTEGER + COMMA_SEP +
		    COLUMN_COMMENT  + TYPE_TEXT    + COMMA_SEP +
		    COLUMN_DATE     + TYPE_TEXT    +  " )";

}
