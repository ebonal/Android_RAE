package com.rae.placetobe.account;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.rae.placetobe.data.PlaceToBeContentProvider;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.PtrLoaderCallbacks;

public class UsersCallbacks extends PtrLoaderCallbacks 
{
	final private Context context ;
	
	public UsersCallbacks(Context context, CursorHolder controler)  {
		super(controler) ;
		this.context  = context ;
	}
	
	@Override
	final public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	    Uri uri;
	    if (args.isEmpty())
	    	uri = PlaceToBeContentProvider.USERS_URI;
	    else 
	    	uri = PlaceToBeContentProvider.FOLLOWED_URI;

		return new UsersCursorLoader(context, args, uri) ;    	   	
	}
}
