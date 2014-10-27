package com.rae.placetobe.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppUtil
{
	public final static String PREFERENCE_FILE_NAME = "ImageData" ;
	
	static public SharedPreferences getApplicationPreferences(Context context)  {
		return context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)  ;
	}
}
