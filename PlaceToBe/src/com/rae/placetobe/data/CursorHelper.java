package com.rae.placetobe.data;

import android.database.Cursor;

public class CursorHelper
{
	// Sinon l'autocast converti la valeur null en 0 !!
	static public Double getNDouble(Cursor cursor, int columnIdx) {
		if (cursor.isNull(columnIdx)) return null ;
		return cursor.getDouble(columnIdx);
	}
	
	static public Integer getNInteger(Cursor cursor, int columnIdx) {
		if (cursor.isNull(columnIdx)) return null ;
		return cursor.getInt(columnIdx);
	}

	static public String getNString(Cursor cursor, int columnIdx) {
		if (cursor.isNull(columnIdx)) return null ;
		String s = cursor.getString(columnIdx).trim();
		if(s.isEmpty()) return null ;
		return s ;
	}
}
