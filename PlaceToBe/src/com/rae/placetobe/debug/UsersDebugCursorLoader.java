package com.rae.placetobe.debug;

import android.content.Context;
import android.content.CursorLoader;
import android.os.Bundle;

import com.rae.placetobe.data.PlaceToBeContentProvider;
import com.rae.placetobe.data.Users;

// Modify ths class to convert parameters from bundle 
public class UsersDebugCursorLoader extends CursorLoader implements Users
{
	public UsersDebugCursorLoader(Context context, Bundle args) {
		super(context, PlaceToBeContentProvider.USERS_URI, null, null, null, null);
	}
}
