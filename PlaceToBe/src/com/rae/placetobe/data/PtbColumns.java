package com.rae.placetobe.data;

import android.provider.BaseColumns;

public interface PtbColumns extends BaseColumns
{
    String TYPE_TEXT    = " TEXT";
	String TYPE_INTEGER = " INTEGER";
	
	String COMMA_SEP = ",";
	
	String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS " ;
}
