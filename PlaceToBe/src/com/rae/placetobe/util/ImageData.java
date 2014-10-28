package com.rae.placetobe.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;


public class ImageData
{
	private static final String TAG = ImageData.class.getSimpleName();
	
	static final private String KEY_PATH    = "PATH" ;
	static final private String KEY_COMMENT = "COMMENT" ;
	static final private String KEY_DATE    = "DATE" ;

	final private Integer id  ;
	final private String filePath  ;
	final private String comment   ;
	final private long   timestamp ;

	public Integer getId() {
		return id;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getComment() {
		return comment;
	}
	public long getTimestamp() {
		return timestamp;
	}
	
	private ImageData(Integer id, String filePath, String comment, long timestamp)
	{
		this.id = id ;
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

		return new ImageData(Integer.valueOf(idx), filePath, comment, timestamp) ;
	}

	// TODO : NOT USED
	static private Integer getIdForFilePath(SharedPreferences sharedPref, String filePath)
	{
		Map<String, ? > all = sharedPref.getAll() ;

		String key   ;		
		for(Map.Entry<String, ?> entry : all.entrySet()) 
		{
			key = entry.getKey() ;
			if(key==null || key.startsWith(KEY_PATH)) continue ;
			if(filePath.equals(key))  {
				String lastDigit = key.substring(key.length()-1) ;
				return Integer.valueOf(lastDigit) ;
			}
		}

		return null; // No data found
	}
	
	static public ImageData getImageData(SharedPreferences sharedPref, Integer searchId)
	{
		String filePath = sharedPref.getString(KEY_PATH+"."+searchId, "") ;
		String comment  = sharedPref.getString(KEY_COMMENT+"."+searchId, "") ;
		String sDate    = sharedPref.getString(KEY_DATE+"."+searchId, "") ;
		return new ImageData(searchId, filePath, comment, Long.parseLong(sDate)) ;
	}
	
	
	@SuppressLint("UseSparseArrays")
	static public Collection<ImageData> getAllImageDatas(SharedPreferences sharedPref)
	{
		Map<Integer, ImageData> datas = new HashMap<Integer, ImageData>() ;
		ImageData data ;
		
		Map<String, ? > all = sharedPref.getAll() ;

		String key   ;		
		Integer id ;
		for(Map.Entry<String, ?> entry : all.entrySet()) 
		{
			key = entry.getKey() ;
			Log.d(TAG, "KEY " + key) ;
			if(key==null || key.isEmpty()) continue ;
			if(!(key.startsWith(KEY_COMMENT) || key.startsWith(KEY_DATE) || key.startsWith(KEY_PATH)))
				continue ; // Not a image shared preference
				
			String lastDigit = key.substring(key.length()-1) ;
			id = Integer.valueOf(lastDigit) ;

			data = datas.get(id) ;
			if(data!=null) continue ; // Already in 
			
			data = getImageData(sharedPref, id) ;
			datas.put(id, data) ;
		}
		
		return datas.values() ;
	}
	
	static public void dump(SharedPreferences sharedPref)
	{
    	Log.d(TAG,"dump :" + sharedPref) ;
    	
		Map<String,?> keys = sharedPref.getAll();
		for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d("map values",entry.getKey() + ": " + entry.getValue().toString());            
		}
		/*
		Collection<ImageData> allDatas = getAllImageDatas(sharedPref) ;
		for(ImageData data : allDatas) 
		{
			Log.d(TAG, "ID " + data.getId()) ;
			Log.d(TAG, "PATH " + data.getFilePath()) ;
			Log.d(TAG, "COMMENT " + data.getComment()) ;
			Log.d(TAG, "TIMESTAMP" + data.getTimestamp()) ;
			Log.d(TAG, "") ;
		}
		*/
	}

	// TODO : NOT USED
	static public ImageData getDataForFilePath(SharedPreferences sharedPref, String filePath)
	{
		Integer idx = getIdForFilePath(sharedPref, filePath) ;
		if(idx==null) return null ; // Not found		
		String comment = sharedPref.getString(KEY_COMMENT+"."+idx, "") ;
		String sDate   = sharedPref.getString(KEY_DATE+"."+idx, "") ;
		return new ImageData(idx, filePath, comment, Long.parseLong(sDate)) ;
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
