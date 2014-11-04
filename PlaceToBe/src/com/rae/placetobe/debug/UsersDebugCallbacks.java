package com.rae.placetobe.debug;

import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.PtrLoaderCallbacks;

public class UsersDebugCallbacks extends PtrLoaderCallbacks 
{
	final private Context context ;
	
	public UsersDebugCallbacks(Context context, CursorHolder controler)  {
		super(controler) ;
		this.context  = context ;
	}
	
	@Override
	final public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	    return new UsersDebugCursorLoader(context, args) ;    	   	
	}
}
