package com.rae.placetobe.debug;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class UsersDebugActivity extends Activity implements CursorHolder, LoaderHolder
{
	private static final String TAG = UsersDebugActivity.class.getSimpleName();
	
	private CursorAdapter cursorAdapter ;
	private LoaderCallbacks<Cursor> callbacks ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users_debug);
	
		ListView listView = (ListView)findViewById(R.id.listViewUser) ;
    	cursorAdapter = new UsersDebugAdapter(this) ;
        listView.setAdapter(cursorAdapter) ;
        
    	callbacks = new UsersDebugCallbacks(this, this) ;		

		// You typically initialize a Loader within the activity's onCreate() method, or within the fragment's onActivityCreated() method.
		LoaderManager loaderManager = getLoaderManager() ;
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_USERS, null /*args*/, callbacks); 

	}
	
	// Called by Callbacks to update UI
	@Override
	final public void onNewCursor(Cursor cursor) {
		cursorAdapter.swapCursor(cursor) ;
	}

	@Override
	final public void restartLoader(int loaderId, Bundle args)
	{
		Log.d(TAG, "restartLoader()") ;
		getLoaderManager().restartLoader(loaderId, args, callbacks) ;
    }
}
