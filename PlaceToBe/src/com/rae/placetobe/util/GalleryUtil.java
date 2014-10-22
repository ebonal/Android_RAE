package com.rae.placetobe.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class GalleryUtil
{
	static public void addPic(Context context, String  mCurrentPhotoPath)
	{
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(mCurrentPhotoPath);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    context.sendBroadcast(mediaScanIntent);
	}
}
