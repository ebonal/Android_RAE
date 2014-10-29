package com.rae.placetobe.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesUtil
{
	static private final String TAG = SharedPreferencesUtil.class.getSimpleName();
	
	public final static String PREFERENCE_FILE_NAME = "ImageData" ;
	
	/**
	 * Retrieves the Shared Preferences where the image data are saved
	 */
	static public SharedPreferences getImageDataPreferences(Context context)  {
		return context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)  ;
	}
	
	/**
	 * Utility method to see the content of shared preferences
	 */
	static public void dump(SharedPreferences sharedPreferences)
	{
        Log.d(TAG, "<< START DUMP >>");            
		Map<String,?> keys = sharedPreferences.getAll();
		for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d(TAG, entry.getKey() + " : " + String.valueOf(entry.getValue()));            
		}
        Log.d(TAG, "<< END DUMP >>");            
	}
}
