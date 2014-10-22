package com.rae.placetobe.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public class GalleryUtil
{
	/**
	 * invoke the system's media scanner to add your photo to the Media Provider's database,
	 * making it available in the Android Gallery application and to other apps.
	 */
	static public void addPic(Context context, String  photoPath)
	{
	    Uri contentUri = Uri.fromFile(new File(photoPath));
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    mediaScanIntent.setData(contentUri);
	    context.sendBroadcast(mediaScanIntent);
	}
}
