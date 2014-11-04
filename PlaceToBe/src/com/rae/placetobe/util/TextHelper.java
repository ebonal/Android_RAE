package com.rae.placetobe.util;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.TextView;

public class TextHelper
{
	static public Calendar parseDate(final Context context, final TextView tvDate) {
		String s = TextHelper.asNString(tvDate) ;
		if(s==null) return null ;		
		return TimeHelper.parseDate(context, s) ;
	}
	
	static public Calendar parseTime(final Context context, final TextView tvTime) {
		String s = TextHelper.asNString(tvTime) ;
		if(s==null) return null ;
		return TimeHelper.parseTime(context, s) ;
	}

	static public void putString(final TextView text, final Cursor cursor,  final int columnIndex) {
		text.setText(cursor.getString(columnIndex)) ; // Null replaced by "" in textview implementation
	}

	static public void putInteger(final TextView text, final Cursor cursor,  final int columnIndex) {
		text.setText(String.valueOf(cursor.getInt(columnIndex))) ; 
	}

	
	
	// La date est dans la base sous la forme d'une string
	/*
	static public void putDateTime(final Context context, final TextView text, final Cursor cursor,  final int columnIdx) {
		String s = cursor.getString(columnIdx) ;
		if(s!=null) text.setText(TimeFormatHelper.formatDateTime(context, s));
	}
*/

	static public void putString(final TextView text, final ContentValues values,  final String key) {
		String s = values.getAsString(key) ;
		if(s!=null) text.setText(s) ;
	}

	static public void putInteger(final TextView text, final ContentValues values,  final String key) {
		Integer i = values.getAsInteger(key) ;
		if(i!=null) text.setText(String.valueOf(i)) ;
		else		text.setText(null) ;
	}
	
	static public void putDate(final Context context, final TextView text, final ContentValues values, final String key) {
		String s = values.getAsString(key) ;
		s = TimeHelper.formatDate(context, s) ;
		if(s!=null) text.setText(s) ;
		else		text.setText(null) ;
	}

	static public void putTime(final Context context, final TextView text, final ContentValues values,  final String key) {
		String s = values.getAsString(key) ;
		s = TimeHelper.formatTime(context, s) ;
		if(s!=null) text.setText(s) ;
		else		text.setText(null) ;
	}
	
	
	// Converts to String or null if empty
	static public String asNString(final TextView text) {
		String s = text.getText().toString().trim() ;
		if(s.isEmpty()) return null ;
		return s ;
	}

	static public Double asNDouble(final TextView text) {
		String s = text.getText().toString().trim() ;
		if(s.isEmpty()) return null ;
		return Double.parseDouble(s) ; // + autoboxing
	}

	static public Integer asNInteger(final TextView text) {
		String s = text.getText().toString().trim() ;
		if(s.isEmpty()) return null ;
		return Integer.parseInt(s) ; // + autoboxing
	}
	
	static public boolean isEmpty(final TextView text) {
		String s = text.getText().toString().trim() ;
		return s.isEmpty() ;
	}
}
