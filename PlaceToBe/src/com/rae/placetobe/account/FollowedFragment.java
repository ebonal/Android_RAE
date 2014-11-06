package com.rae.placetobe.account;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.data.Users;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class FollowedFragment extends Fragment implements CursorHolder, LoaderHolder
{
	@InjectView(R.id.listViewFollowed)  ListView listViewFollowed;
	@InjectView(R.id.imageButtonAdd) ImageButton  btAdd;
	
	private CursorAdapter cursorAdapter;
	private LoaderCallbacks<Cursor> callbacks;
	
	
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
		LoaderManager loaderManager = getActivity().getLoaderManager();
		
		Bundle argsF = new Bundle();
		// set the filter for receive only followed users => true
		argsF.putBoolean(Users.USERS_ARG_FOLLOW, Boolean.TRUE);
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_USERS, argsF, callbacks);
		
		
	    btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), AddFollowedActivity.class);
        	   	startActivity(intent);
            }
        });
		
		return rootView;
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
