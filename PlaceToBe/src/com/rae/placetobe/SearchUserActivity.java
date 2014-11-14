package com.rae.placetobe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.data.UsersDao;
import com.rae.placetobe.debug.UsersDebugAdapter;
import com.rae.placetobe.debug.UsersDebugCallbacks;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class SearchUserActivity extends Activity implements CursorHolder, LoaderHolder
{
	private static final String TAG = SearchUserActivity.class.getSimpleName();
	
	@InjectView(R.id.autoCompleteTextView) AutoCompleteTextView userSearchInput;
	@InjectView(R.id.listViewUser) ListView listView;
	
	// 
	List<HashMap<String, String>> gitHubMembersList = new ArrayList<HashMap<String, String>>();
	
	private CursorAdapter cursorAdapter ;
	private LoaderCallbacks<Cursor> callbacks ;
	
//	// Login List of HB user
//	private static final String[] GITHUB_MEMBERS = new String[]{
//		"AnthonyFontaine", "RobertBakic", "ebonal", "motof", "MiloIgor", "rmarcou", "moea-chan", "uanatol", "tpuch24", "Darthevel", "Argeel", "MichaelAdjedj"
//	};
//	// List for stock gitHub Members
//	private List<GitHubMember> gitHubMembers;
	private List<String> simpleGitHubMembers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user);
		ButterKnife.inject(this);
		
    	cursorAdapter = new UsersDebugAdapter(this) ;
        listView.setAdapter(cursorAdapter) ;
        listView.setOnItemClickListener(new listViewClickListener());
        
    	callbacks = new UsersDebugCallbacks(this, this) ;		

		// You typically initialize a Loader within the activity's onCreate() method, or within the fragment's onActivityCreated() method.
		LoaderManager loaderManager = getLoaderManager() ;
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_USERS, null, callbacks);
		
//		// Init my list of gitHub Members
//		gitHubMembers = new ArrayList<GitHubMember>();
//		simpleGitHubMembers = new ArrayList<String>();
//		
//		// Observable on HB user list
//		// Get the github account link to my user
//		Observable<GitHubMember> o1 = Observable.from(GITHUB_MEMBERS)
//			.flatMap(new Func1<String, Observable<GitHubMember>>(){
//	    		@Override
//	    		public Observable<GitHubMember> call(String s){
//					return ApiManager.getGitHubMember(s);
//	    		}
//	    	});
//		
//		// Observable on Retrofit github members list
//		Observable<GitHubMember> o2 = ApiManager.getGitHubMembersList()
//			.flatMap(new Func1<List<GitHubMember>, Observable<GitHubMember>>() {
//		        @Override
//		        public Observable<GitHubMember> call(List<GitHubMember> gitHubMembers) {
//		            return Observable.from(gitHubMembers);
//		        }
//		    });
//		
//		// Observable the two observable
//		// Put the github member into a list of github member
//		// Set the adapter and click listener UI
//		Observable.merge(o1,o2)
//			.subscribeOn(Schedulers.io())
//			.observeOn(AndroidSchedulers.mainThread())
//			.subscribe(new Subscriber<GitHubMember>() {
//		        @Override
//		        public void onNext(GitHubMember gitHubMember) { 
//		        	Log.d(TAG, "GITHUB_MEMBERS : " + gitHubMember.login);
//	        	 	gitHubMembers.add(gitHubMember);
//	        	 	simpleGitHubMembers.add(gitHubMember.login);
//	        	 	// gitHubMember.addGitHubMemberDB(getBaseContext());
//		        }
//		
//		        @Override
//		        public void onCompleted() { 
//		    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
//	                android.R.layout.simple_dropdown_item_1line, simpleGitHubMembers);
//			        userSearchInput.setAdapter(adapter);
//			        userSearchInput.setOnItemClickListener(new autoCompleteClickListener()); 
//		        }
//		
//		        @Override
//		        public void onError(Throwable e) { Log.e(TAG, "Ouch!"); }
//		    });  
	}
	
	@Override
	final public void onNewCursor(Cursor cursor) {
		if(cursor==null) {
			Log.d(TAG, "cursor is NULL()") ;
			return; 
		}
		
		cursorAdapter.swapCursor(cursor) ;
		
		simpleGitHubMembers = new ArrayList<String>();
		cursor.moveToFirst(); // Car peut etre deja iteré par d'autre controller
		while(!cursor.isAfterLast()) 
		{
			simpleGitHubMembers.add(cursor.getString(UsersDao.IDX_NAME));			
			cursor.moveToNext();
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getBaseContext(),android.R.layout.simple_dropdown_item_1line, simpleGitHubMembers);
        userSearchInput.setAdapter(adapter);
        userSearchInput.setOnItemClickListener(new userClickListener()); 
	}

	@Override
	final public void restartLoader(int loaderId, Bundle args)
	{
		Log.d(TAG, "restartLoader()") ;
		getLoaderManager().restartLoader(loaderId, args, callbacks) ;
    }
	
	public void launchProfileActivity(String user) {
		Intent intent = new Intent(this, ProfileActivity.class);
    	intent.putExtra("user", user);
	   	startActivity(intent);
   }
	
	private class listViewClickListener implements ListView.OnItemClickListener
	{
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {	
	    	// Get the item selected
	    	String user = simpleGitHubMembers.get(position);
	    	
//	    	Toast.makeText(getApplicationContext(), user, Toast.LENGTH_SHORT).show();
	    	
	    	launchProfileActivity(user);
	    	
	    }
	}
	
	private class userClickListener implements ListView.OnItemClickListener
	{
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {	
	    	// Get the item selected
	    	String user = (String) parent.getItemAtPosition(position);
	    	
//	    	Toast.makeText(getApplicationContext(), user, Toast.LENGTH_SHORT).show();
	    	
	    	launchProfileActivity(user);
	    	
//	    	// Get the githubmemeber user info
//	    	ApiManager.getGitHubMember(selected)
//		    	.subscribeOn(Schedulers.io())
//		    	.observeOn(AndroidSchedulers.mainThread())
//		    	.subscribe(new Action1<GitHubMember>() {
//					@Override
//					public void call(GitHubMember gitHubMember) {
//						textViewLogin.setText(gitHubMember.login);
//						textViewEmail.setText(gitHubMember.email);
//						textViewName.setText(gitHubMember.name);
//						
//						// Async observable to display avatar
//						Observable.just(gitHubMember.avatar_url)
//					    	.map(new Func1<String, Bitmap>(){
//					    		@Override
//					    		public Bitmap call(String src){
//					    			try {
//					    		        URL url = new URL(src);
//					    		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//					    		        connection.setDoInput(true);
//					    		        connection.connect();
//					    		        InputStream input = connection.getInputStream();
//					    		        Bitmap myBitmap = BitmapFactory.decodeStream(input);
//					    		        return myBitmap;
//					    		    } catch (IOException e) {
//					    		        // Log exception
//					    		        return null;
//					    		    }
//					    		}
//					    	})
//					    	.subscribeOn(Schedulers.newThread())
//					    	.observeOn(AndroidSchedulers.mainThread())
//					    	.subscribe(new Action1<Bitmap>() {
//								@Override
//								public void call(Bitmap bitmap) {
//									imageViewAvatar.setImageBitmap(bitmap);
//								}
//							});
//					}
//				});
	    	
	    }
	}
	
}
