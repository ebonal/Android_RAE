package com.rae.placetobe.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.Log;

// Concertion using android default date/time format
public class TimeHelper
{
	private static final String TAG = TimeHelper.class.getSimpleName() ;
	
	static private DateFormat dateFormat = null ;
	static private DateFormat getDateFormat(final Context context) {
		//	dateFormat = android.text.format.DateFormat.getDateFormat(context); //short
		if(dateFormat==null) dateFormat = android.text.format.DateFormat.getMediumDateFormat(context);
		return dateFormat ;
	}

	static private DateFormat timeFormat = null ;
	static private DateFormat getTimeFormat(final Context context) {
		if(timeFormat==null) timeFormat = android.text.format.DateFormat.getTimeFormat(context);
		return timeFormat ;
	}
	
    static public String formatDate(final Context context, String sqliteDate) 
	{
    	if(sqliteDate==null || sqliteDate.equals("")) return null ; // safety check
    	try {
		    return formatDate(context, TimeSQLHelper.parseSQLDate(sqliteDate)) ;
    	}
    	catch(ParseException pe) {
    		return "Invalid date format" ;
    	}
	}	

	static public String formatDate(final Context context, final Date date)  {
		return getDateFormat(context).format(date) ;
	}	

	// androDate : Non null, date string in android default format
	static public Calendar parseDate(final Context context, final String androDate)
	{
		try 
		{
			Date date = getDateFormat(context).parse(androDate) ;
			Calendar c = Calendar.getInstance() ;
			c.setTime(date) ;
			return c ;
		}
		catch(ParseException pe) {
			Log.e(TAG, "Invalid date format") ;
			return null ;
		}
	}
		
	static public String formatTime(final Context context, String sqliteDate) 
	{
    	if(sqliteDate==null || sqliteDate.equals("")) return null ; // safety check
    	try  {
			Calendar c = Calendar.getInstance();
			c.setTime(TimeSQLHelper.parseSQLDate(sqliteDate)) ;
		    return formatTime(context, c.get(Calendar.HOUR), c.get(Calendar.MINUTE)) ;
    	}
    	catch(ParseException pe) {
    		return "Invalid time format" ;
    	}
	}
	
	static public String formatTime(final Context context, final int hourOfDay, final int minute)  {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, hourOfDay) ;
		c.set(Calendar.MINUTE, minute) ;
		c.set(Calendar.SECOND, 0) ;
		return getTimeFormat(context).format(c.getTime()) ;
	}	
	
	// androDate : Non null, time string in android default format
	static public Calendar parseTime(final Context context, final String androTime)
	{
		try 
		{
			Date date =  getTimeFormat(context).parse(androTime) ;
			Calendar c = Calendar.getInstance() ;
			c.setTime(date) ;
			return c ;
		}
		catch(ParseException pe) {
			Log.e(TAG, "Invalid time format") ;
			return null ;
		}
	}
}
