package com.rae.placetobe.history;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.GridView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.debug.ImagesDebugCallbacks;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class HistoryActivity extends AbstractHistoryActivity implements CursorHolder, LoaderHolder
{
	private static final String TAG = HistoryActivity.class.getSimpleName();

	private CursorAdapter cursorAdapter ;
	private LoaderCallbacks<Cursor> callbacks ;

	@InjectView(R.id.gridview) GridView gridview;
	@Override
	protected GridView getGridView() {
		return gridview;
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "onCreate()") ;
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
}
