package com.rae.placetobe.account;

import rx.Observable;
import rx.android.events.OnTextChangeEvent;
import rx.android.observables.ViewObservable;
import rx.functions.Action1;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.data.DBProxy;
import com.rae.placetobe.data.PlaceToBeContentProvider;
import com.rae.placetobe.data.Users;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class AddFollowedActivity extends Activity implements CursorHolder, LoaderHolder
{
	@InjectView(R.id.listViewResult)  	ListView listViewResult;
	@InjectView(R.id.editTextSearch)	EditText editTextSearch;
	
	private CursorAdapter cursorAdapter;
	private LoaderCallbacks<Cursor> callbacks;
	private Bundle args = new Bundle();
	private LoaderManager loaderManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_followed);
		ButterKnife.inject(this, this);		
		
		cursorAdapter = new UsersAdapter(this);
		listViewResult.setAdapter(cursorAdapter);
        
    	callbacks = new UsersCallbacks(getApplicationContext(), this);
    	loaderManager = this.getLoaderManager();
    	loaderManager.initLoader(AbstractDrawerActivity.LOADER_USERS, args, callbacks);
    	
		Action1<OnTextChangeEvent> searchUsersInDB = new Action1<OnTextChangeEvent>()
		{
			@Override
			public void call(OnTextChangeEvent s)
			{
				args.putString(Users.USERS_ARG_SEARCH, s.text.toString());
				loaderManager.restartLoader(AbstractDrawerActivity.LOADER_USERS, args, callbacks);
			}
		};

		// Observable on EditText search
		Observable<OnTextChangeEvent> o = ViewObservable.text(editTextSearch, false);
		o.subscribe(searchUsersInDB);
		


		listViewResult.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	
	        	//DBProxy.update(getApplicationContext(), PlaceToBeContentProvider.FOLLOWED_URI, id, values);
	        	
	        	Toast.makeText(getBaseContext(),"pos "+position+" - id "+id,Toast.LENGTH_SHORT).show();
	        }
	    });


		
	}
	
	@Override
	public void restartLoader(int loaderId, Bundle args)
	{
		this.getLoaderManager().restartLoader(loaderId, args, callbacks) ;
	}

	@Override
	public void onNewCursor(Cursor cursor)
	{
		cursorAdapter.swapCursor(cursor);
		listViewResult.refreshDrawableState();
	}
}
