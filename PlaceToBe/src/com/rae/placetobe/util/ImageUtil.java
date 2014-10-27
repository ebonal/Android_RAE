package com.rae.placetobe.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class ImageUtil
{
	private static final String TAG = ImageUtil.class.getSimpleName();
	
	@SuppressLint("SimpleDateFormat")
	static public final SimpleDateFormat FILE_FORMAT =  new SimpleDateFormat("yyyyMMdd_HHmmss") ;
	
    static final File STORAGE_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

	static public File createImageFile()
	throws IOException 
	{
		String imageFileName = new StringBuilder("JPEG_").append(FILE_FORMAT.format(new Date())).append("_").toString() ;
	   
		return File.createTempFile(
	        imageFileName,  /* prefix */
	        ".jpg",         /* suffix */
	        STORAGE_DIR     /* directory */
	    );
	}

	/**
	 * To tell the decoder to subsample the image, loading a smaller version into memory,
     * set inSampleSize to true in your BitmapFactory.Options object.
     * 
     * For example, an image with resolution 2048x1536 that is decoded with an inSampleSize of 4 produces a bitmap of approximately 512x384.
     * Loading this into memory uses 0.75MB rather than 12MB for the full image (assuming a bitmap configuration of ARGB_8888).
     * Here’s a method to calculate a sample size value that is a power of two based on a target width and height :
     */
	static private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
	{
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width  = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth) 
	    {	
	        final int halfHeight = height / 2;
	        final int halfWidth  = width / 2;
	
	        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
	        // height and width larger than the requested height and width.
	        while ((halfHeight / inSampleSize) > reqHeight
	                && (halfWidth / inSampleSize) > reqWidth) {
	            inSampleSize *= 2;
	        }
	    }
	
	    return inSampleSize;
	}

	static public Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight)
	{ 
	   	Log.d(TAG, "reqWidth  : "+reqWidth + " reqHeight  : "+reqHeight) ;
		
		// First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    
	    BitmapFactory.decodeFile(pathName, options) ;	    
	    
	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    
    	Log.d(TAG, "inSampleSize  : " + options.inSampleSize) ;
    		    
	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    
	    return  BitmapFactory.decodeFile(pathName, options) ;	    
	}


}
