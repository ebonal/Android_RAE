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
	
	static public void startImageCaptureActivity(Activity activity)
	{
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(activity.getPackageManager()) == null) return ;
	   
        try
        {
            // Creates the File where the photo should go	    
        	File mCurrentPhotoFile = ImageUtil.createImageFile();
            if(mCurrentPhotoFile == null) {
                SharedPreferencesUtil.backupFilePath(activity, null);
            	return ;
            }
            SharedPreferencesUtil.backupFilePath(activity, mCurrentPhotoFile.getAbsolutePath());
        	        	
        	Uri uri = Uri.fromFile(mCurrentPhotoFile) ;
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
        catch (Exception ex) {
        	Log.e(TAG, "Error on startImageCaptureActivity", ex) ;
        }
 	}
	
	static public void startVideoCaptureActivity(Activity activity)
	{
	    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	    if (takeVideoIntent.resolveActivity(activity.getPackageManager()) != null) {
	    	activity.startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
	    }		
	}
}
