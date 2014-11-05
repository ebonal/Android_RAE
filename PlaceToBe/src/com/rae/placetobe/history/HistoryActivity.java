package com.rae.placetobe.history;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.debug.ImagesDebugCallbacks;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class HistoryActivity extends AbstractDrawerActivity  implements CursorHolder, LoaderHolder
{
	@InjectView(R.id.gridview) GridView gridview;
	
	private static final String TAG = HistoryActivity.class.getSimpleName();
	
	private CursorAdapter cursorAdapter ;
	private LoaderCallbacks<Cursor> callbacks ;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_history;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); // Will inflate the layout using getContentViewId()
		
		ButterKnife.inject(this);

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
