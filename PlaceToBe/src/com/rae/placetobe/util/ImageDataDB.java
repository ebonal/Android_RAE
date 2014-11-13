package com.rae.placetobe.util;

import android.content.ContentValues;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.rae.placetobe.data.DBProxy;
import com.rae.placetobe.data.Images;
import com.rae.placetobe.data.PlaceToBeContentProvider;
import com.rae.placetobe.model.ImageData;

/**
 * Manage a list of 10 pictures and their associated data.
 * 
 */
public class ImageDataDB 
{
	private static final String TAG = ImageDataDB.class.getSimpleName();

	/**
	 * Add a photo to the list
	 */
	static public ImageData addPhoto(Context context, String filePath, String comment, Location location)
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
  	    
  	    // TODO : get primary key
  	    
  	    LatLng latlng = null ;
  	    if(location!=null) latlng = new LatLng(location.getLatitude(), location.getLongitude()) ;
  	    return new ImageData(1, filePath, comment, timestamp, latlng) ;
	}
}
