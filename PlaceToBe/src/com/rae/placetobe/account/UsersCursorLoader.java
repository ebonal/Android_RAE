package com.rae.placetobe.account;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;
import android.os.Bundle;

import com.rae.placetobe.data.Users;

// Modify the class to convert parameters from bundle 
public class UsersCursorLoader extends CursorLoader implements Users
{
	public UsersCursorLoader(Context context, Bundle args, Uri uri)
	{
		super(context, uri, null, null, bundleToArray(args), null);
	}

	public static String[] bundleToArray(Bundle bundle)
	{
		String[] array = new String[1];

		if (bundle.containsKey(Users.USERS_ARG_FOLLOW))
		{
			if (bundle.getBoolean(Users.USERS_ARG_FOLLOW))
			{
				array[0] = Users.USERS_ARG_VALUE_FOLLOWED; 
			} else {
				array[0] = Users.USERS_ARG_VALUE_FOLLOWERS; 
			}
		} else {
			if (bundle.containsKey(Users.USERS_ARG_SEARCH))
			{
				array[0] = bundle.getString(Users.USERS_ARG_SEARCH);
			} else {
				// Default
				return null;
			}
		}

		return array;
	}
}
