package com.rae.placetobe.util;

import java.util.Map;

import android.content.SharedPreferences;
import android.util.Log;


public class ImageData
{
	private static final String TAG = ImageData.class.getSimpleName();
	
	static final private String KEY_PATH    = "PATH" ;
	static final private String KEY_COMMENT = "COMMENT" ;
	static final private String KEY_DATE    = "DATE" ;

	private String filePath  ;
	private String comment   ;
	private long   timestamp ;

	public String getFilePath() {
		return filePath;
	}
	public String getComment() {
		return comment;
	}
	public long getTimestamp() {
		return timestamp;
	}
	
	private ImageData(String filePath, String comment, long timestamp)
	{
		this.filePath = filePath ;
		this.comment  = comment ; 
		this.timestamp = timestamp ;
	}
	
	/*
	 * Stockage des infos photos dans le SharedPreferences 
	 * 
	 *  PATH.1    =  Photo 2 path 
	 *  COMMENT.1 =  Photo 2 commment
	 *  DATE.1    =  Photo 2 date  
	 * 
	 */

	static public ImageData addPhoto(SharedPreferences sharedPref, String filePath, String comment)
	{
		int idx = getNextID(sharedPref) ;
    	Log.d(TAG,"getNextID : " + idx) ;
    	
		long timestamp = System.currentTimeMillis() ;
		
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(KEY_PATH+"."+idx, filePath);
		editor.putString(KEY_COMMENT+"."+idx, comment);
		editor.putString(KEY_DATE+"."+idx, String.valueOf(timestamp));		
		editor.commit();
		
		return new ImageData(filePath, comment, timestamp) ;
	}

	static private Integer getIdForFilePath(SharedPreferences sharedPref, String filePath)
	{
		Map<String, ? > all = sharedPref.getAll() ;

		String key   ;		
		for(Map.Entry<String, ?> entry : all.entrySet()) 
		{
			key = entry.getKey() ;
			if(key==null || key.startsWith(KEY_PATH)) continue ;
			if(filePath.equals(key))  {
				String lasDigit = key.substring(0, key.length() - 1) ;
				return Integer.valueOf(lasDigit) ;
			}
		}

		return null; // No data found
	}

	static public ImageData getDataForFilePath(SharedPreferences sharedPref, String filePath)
	{
		Integer idx = getIdForFilePath(sharedPref, filePath) ;
		if(idx==null) return null ; // Not found		
		String comment = sharedPref.getString(KEY_COMMENT+"."+idx, "") ;
		String sDate   = sharedPref.getString(KEY_DATE+"."+idx, "") ;
		return new ImageData(filePath, comment, Long.parseLong(sDate)) ;
	}

	/**
	 * Returns the next ID that will be used to store the image data.
	 */
	static public int getNextID(SharedPreferences sharedPref) 
	{
		Map<String, ? > all = sharedPref.getAll() ;

		int i=0;
		int  idxMinStamp =0 ;
		long  minTimeStamp = Long.MAX_VALUE ;
		
		String key   ;		
		for(Map.Entry<String, ?> entry : all.entrySet()) 
		{
			key = entry.getKey() ;
			if(!key.startsWith(KEY_DATE)) continue ;
			
			long timestamp = Long.parseLong(String.valueOf(entry.getValue())) ;
			if(timestamp<minTimeStamp) {
				minTimeStamp = timestamp ;
				idxMinStamp = i ;
			}
		
			i++ ;
		}
		
		if(i<9) return i ;
		return idxMinStamp ;
	}
}
