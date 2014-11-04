package com.rae.placetobe.debug;

import android.content.Context;
import android.content.CursorLoader;
import android.os.Bundle;

import com.rae.placetobe.data.PlaceToBeContentProvider;
import com.rae.placetobe.data.Users;

// Modify ths class to convert parameters from bundle 
public class ImagesDebugCursorLoader extends CursorLoader implements Users
{
	public ImagesDebugCursorLoader(Context context, Bundle args) {
		super(context, PlaceToBeContentProvider.IMAGES_URI, null, null, null, null);
	}
}
