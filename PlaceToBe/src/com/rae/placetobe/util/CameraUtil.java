package com.rae.placetobe.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

public class CameraUtil
{
	static private final String TAG = CameraUtil.class.getSimpleName();
	
	static public final int REQUEST_TAKE_PHOTO    = 10 ;
	static public final int REQUEST_VIDEO_CAPTURE = 11 ;
	
	static public String startImageCaptureActivity(Activity caller)
	{
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(caller.getPackageManager()) == null) return null ;
	   
        // Create the File where the photo should go
	    String mCurrentPhotoPath = null ;
	    
        try
        {
        	File mCurrentPhotoFile = ImageUtil.createImageFile();
            if(mCurrentPhotoFile == null) return null ;
            
            mCurrentPhotoPath = mCurrentPhotoFile.getAbsolutePath() ;
            Log.d(TAG, "mCurrentPhotoPath : " + mCurrentPhotoPath) ;
        	        	
        	Uri uri = Uri.fromFile(mCurrentPhotoFile) ;
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            caller.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
        catch (Exception ex) {
        	Log.e(TAG, "Error on startCaptureActivity", ex) ;
        }
        
        return mCurrentPhotoPath ;
 	}
	
	static public void startVideoCaptureActivity(Activity caller)
	{
	    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	    if (takeVideoIntent.resolveActivity(caller.getPackageManager()) != null) {
	        caller.startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
	    }		
	}
}
