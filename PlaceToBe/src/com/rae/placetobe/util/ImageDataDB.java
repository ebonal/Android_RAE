package com.rae.placetobe.util;

import android.content.ContentValues;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.rae.placetobe.data.DBProxy;
import com.rae.placetobe.data.Images;
import com.rae.placetobe.data.PlaceToBeContentProvider;

/**
 * Manage a list of 10 pictures and their associated data.
 * 
 */
public class ImageDataDB
{
	private static final String TAG = ImageDataDB.class.getSimpleName();
	
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
	private ImageDataDB(int id, String filePath, String comment, long timestamp)
	{
		this.id        = id ;
		this.filePath  = filePath ;
		this.comment   = comment ; 
		this.timestamp = timestamp ;
	}
	
	/**
	 * Add a photo to the list
	 */
	static public ImageDataDB addPhoto(Context context, String filePath, String comment, Location location)
	{
		long timestamp = System.currentTimeMillis() ;

		ContentValues values = new ContentValues();
  	    values.put(Images.COLUMN_PATH, filePath);
  	    values.put(Images.COLUMN_COMMENT, comment);
  	    values.put(Images.COLUMN_DATE   , String.valueOf(timestamp));

  	    if(location!=null) {
  	    	values.put(Images.COLUMN_LAT, location.getLatitude());
  	    	values.put(Images.COLUMN_LNG, location.getLongitude());  	 	  	    	
  	    }
  	    
  	    
  	    Uri uri = DBProxy.insert(context, PlaceToBeContentProvider.IMAGES_URI, values) ;

  	    Log.d(TAG, "URI : " + uri) ;
  	    
  	    return new ImageDataDB(1, filePath, comment, timestamp) ;
	}
}
