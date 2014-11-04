package com.rae.placetobe.history;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.GridView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.debug.ImagesDebugCallbacks;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class HistoryActivity extends AbstractDrawerActivity  implements CursorHolder, LoaderHolder
{
	private static final String TAG = HistoryActivity.class.getSimpleName();
	
	private CursorAdapter cursorAdapter ;
	private LoaderCallbacks<Cursor> callbacks ;
	
	private GridView gridview;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_history;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); // Will inflate the layout using getContentViewId()
		
		// get my gridview in my layout
		gridview = (GridView) findViewById(R.id.gridview);
		// set custom adapter to my gridview
//		gridview.setAdapter(new GridViewAdapter(this,R.layout.history_gridview_item));
		cursorAdapter = new HistoryAdapter(this) ;
		gridview.setAdapter(cursorAdapter);
		
    	callbacks = new ImagesDebugCallbacks(this, this) ;		

		// You typically initialize a Loader within the activity's onCreate() method, or within the fragment's onActivityCreated() method.
		LoaderManager loaderManager = getLoaderManager() ;
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_IMAGES, null /*args*/, callbacks); 
	}
	
	
	// Called by Callbacks to update UI
	@Override
	final public void onNewCursor(Cursor cursor) {
		cursorAdapter.swapCursor(cursor) ;
	}

	@Override
	final public void restartLoader(int loaderId, Bundle args) {
		Log.d(TAG, "restartLoader()") ;
		getLoaderManager().restartLoader(loaderId, args, callbacks) ;
    }

	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen and change the num of columns 
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	gridview.setNumColumns(3);
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	gridview.setNumColumns(2);
	    }
	}
	
}
