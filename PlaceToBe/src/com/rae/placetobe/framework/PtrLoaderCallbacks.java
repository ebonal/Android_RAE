package com.rae.placetobe.framework;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.util.Log;

abstract public class PtrLoaderCallbacks implements LoaderCallbacks<Cursor> 
{
	private static final String TAG = PtrLoaderCallbacks.class.getSimpleName();

	final private CursorHolder cursorHolder ;
	
	public PtrLoaderCallbacks(CursorHolder cursorHolder) {
		this.cursorHolder = cursorHolder ;
	}


    /*
 	Called automatically when a Loader has finished its load.
 	This method is typically where the client will update the application's UI with the loaded data.
 	The client should assume that new data will be returned to this method each time new data is made available.
 	Remember that it is the Loader's job to monitor the data source and to perform the actual asynchronous loads.
 	The LoaderManager's job is to simply receive these loads once they have completed,
 	and to then pass the result to the callback object's onLoadFinished method for the client (i.e. the Activity/Fragment) to use.
 	*/
	@Override
	final public void onLoadFinished(Loader<Cursor> loader, Cursor cursor)
	{
		Log.d(TAG, "onLoadFinished()") ;
        // The asynchronous load is complete and the data
        // is now available for use. Only now can we associate
        // the queried Cursor with the SimpleCursorAdapter.
		cursorHolder.onNewCursor(cursor) ;
	}
	
	
	/* Called when the a Loader's data is about to be reset.
	 * This method gives you the opportunity to remove any references to old data that may no longer be available.
	 */
	@Override
	final public void onLoaderReset(Loader<Cursor> loader)
	{
		Log.d(TAG, "onLoaderReset()") ;
		// For whatever reason, the Loader's data is now unavailable.
	    // Remove any references to the old data by replacing it with
	    // a null Cursor.
		cursorHolder.onNewCursor(null);
	}
}
