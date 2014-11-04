package com.rae.placetobe.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeSQLHelper
{
    final static private SimpleDateFormat iso8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()); // default in sqlite
    
    static public String formatSQLDate(Calendar cal) {
    	if(cal==null || cal.equals("")) return null ; // safety check
    	return iso8601.format(cal.getTime()) ;
    }
 
    static public Date parseSQLDate(String sqliteDate) 
    throws ParseException {
    	return iso8601.parse(sqliteDate); 
    }
    
    static public Date parseSQLDateNoEx(String sqliteDate) 
    {
    	try {
    		return iso8601.parse(sqliteDate); 
    	} catch (Exception e) {}
   		return null ;
    }
}
