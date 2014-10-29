package com.rae.placetobe.util;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseArray;

/**
 * Manage a list of 10 pictures and their associated data.
 * 
 * There is 3 shared preferences used for each picture.
 * For exemple for the second picture (index is zero based):
 * 
 *   PATH.1    = /<filePath>/image.jpg
 *   COMMENT.1 = User's comment
 *   DATE.1    = The timestamp
 *
 */
public class ImageData
{
//	private static final String TAG = ImageData.class.getSimpleName();
	
	static final private String KEY_PATH    = "PATH" ;
	static final private String KEY_COMMENT = "COMMENT" ;
	static final private String KEY_DATE    = "DATE" ;

	final private int    id  ;
	final private String filePath  ;
	final private String comment   ;
	final private long   timestamp ;

	public int getId() {
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
	
	// private constructor ensure the use of the #addPhoto factory method.
	private ImageData(int id, String filePath, String comment, long timestamp)
	{
		this.id        = id ;
		this.filePath  = filePath ;
		this.comment   = comment ; 
		this.timestamp = timestamp ;
	}
	
	/**
	 * Add a photo to the list
	 */
	static public ImageData addPhoto(Context context, String filePath, String comment)
	{
		SharedPreferences sharedPref = SharedPreferencesUtil.getImageDataPreferences(context) ;
		int idx = getNextID(sharedPref) ;
    	
		long timestamp = System.currentTimeMillis() ;
		
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(KEY_PATH   +"."+idx, filePath);
		editor.putString(KEY_COMMENT+"."+idx, comment);
		editor.putString(KEY_DATE   +"."+idx, String.valueOf(timestamp));		
		editor.commit();

		return new ImageData(Integer.valueOf(idx), filePath, comment, timestamp) ;
	}

	/**
	 * Returns the data for a specified image
	 */
	static public ImageData getImageData(SharedPreferences sharedPref, int searchId)
	{
		String filePath = sharedPref.getString(KEY_PATH   +"."+searchId, "") ;
		String comment  = sharedPref.getString(KEY_COMMENT+"."+searchId, "") ;
		String sDate    = sharedPref.getString(KEY_DATE   +"."+searchId, "") ;
		return new ImageData(searchId, filePath, comment, Long.parseLong(sDate)) ;
	}
		
	/**
	 * Returns the list of image's data
	 */
	static public SparseArray<ImageData> getAllImageDatas(Context context)
	{
		SharedPreferences sharedPref = SharedPreferencesUtil.getImageDataPreferences(context) ;
		SparseArray<ImageData> datas = new SparseArray<ImageData>(10) ;
		
		ImageData data ;		
		String key ;		
		int id ;
		
		for(Map.Entry<String, ?> entry : sharedPref.getAll().entrySet()) 
		{
			key = entry.getKey() ;
			if(key==null || key.isEmpty()) continue ;
			if(!(key.startsWith(KEY_COMMENT) || key.startsWith(KEY_DATE) || key.startsWith(KEY_PATH)))
				continue ; // Not a image shared preference
				
			String lastDigit = key.substring(key.length()-1) ;
			id = Integer.parseInt(lastDigit) ;

			data = datas.get(id) ;
			if(data!=null) continue ; // Already in 
			
			data = getImageData(sharedPref, id) ;
			datas.put(id, data) ;
		}
		
		return datas; 
	}
	
	/**
	 * Returns the next ID that will be used to store the image data.
	 */
	static private int getNextID(SharedPreferences sharedPref) 
	{
		int   i = 0;
		int   idxMinStamp = 0 ;
		long  minTimeStamp = Long.MAX_VALUE ;
		
		String key   ;		
		for(Map.Entry<String, ?> entry :  sharedPref.getAll().entrySet()) 
		{
			key = entry.getKey() ;
			if(key==null || !key.startsWith(KEY_DATE)) continue ; // Checking all timestamp
			
			long timestamp = Long.parseLong(String.valueOf(entry.getValue())) ;
			if(timestamp<minTimeStamp) { // check if the image is the oldest
				minTimeStamp = timestamp ;
				idxMinStamp  = i ; 
			}
		
			i++ ;
		}
		
		if(i<9) return i ; // Returns i if less than 10 entries
		return idxMinStamp ; // Returns the ID of the oldest photo
	}
}
