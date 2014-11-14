package com.rae.placetobe.account;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.data.DBProxy;
import com.rae.placetobe.data.PlaceToBeContentProvider;
import com.rae.placetobe.data.Users;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class FollowedFragment extends Fragment implements CursorHolder, LoaderHolder
{
	@InjectView(R.id.listViewFollowed)  ListView listViewFollowed;
	@InjectView(R.id.imageButtonAdd) ImageButton  btAdd;
	
	private CursorAdapter cursorAdapter;
	private LoaderCallbacks<Cursor> callbacks;
	private LoaderManager loaderManager;	
	private Bundle argsF = new Bundle();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_followed, container,
				false);
		ButterKnife.inject(this, rootView);
		
		cursorAdapter = new UsersAdapter(getActivity());
		listViewFollowed.setAdapter(cursorAdapter);
        
    	callbacks = new UsersCallbacks(getActivity(), this);		
    	
		// initialize a Loader
    	loaderManager = getActivity().getLoaderManager();
		
		// set the filter for receive only followed users => true
		argsF.putBoolean(Users.USERS_ARG_FOLLOW, Boolean.TRUE);
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_FOLLOWED, argsF, callbacks);
		
		
	    btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), AddFollowedActivity.class);
            	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        	   	startActivity(intent);
            }
        });
		
	    listViewFollowed.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id)
			{
				// show alert for user's confirmation 
				new AlertDialog.Builder(getActivity())
	            .setMessage("Are you sure you want to delete this followed ?")
	            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) { 
	                    // remove this user of the followed's list (update)
	                	ContentValues values = new ContentValues();
	    	        	values.put(Users.COLUMN_FOLLOWED, 0);
	    	        	DBProxy.update(getActivity(), PlaceToBeContentProvider.USERS_URI, (int) id, values);
	    	        	// display refresh
	    	        	loaderManager.restartLoader(AbstractDrawerActivity.LOADER_FOLLOWED, argsF, callbacks);
	    	        	cursorAdapter.notifyDataSetChanged();
	                }
	             })
	            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) { 
	                    // do nothing
	                }
	             })
	            .setIcon(android.R.drawable.ic_dialog_alert)
				.show();
	        	return true;
			}
		});

		return rootView;
	}

	// display refresh
	@Override
	public void onResume()
	{
		super.onResume();
		loaderManager.restartLoader(AbstractDrawerActivity.LOADER_FOLLOWED, argsF, callbacks);
    	cursorAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void restartLoader(int loaderId, Bundle args)
	{
		getActivity().getLoaderManager().restartLoader(loaderId, args, callbacks) ;
	}

	@Override
	public void onNewCursor(Cursor cursor)
	{
		cursorAdapter.swapCursor(cursor) ;
	}
	
}
