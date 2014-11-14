package com.rae.placetobe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.account.UsersCallbacks;
import com.rae.placetobe.data.Users;
import com.rae.placetobe.data.UsersDao;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;
import com.rae.placetobe.util.ApiManager;
import com.rae.placetobe.util.GitHubMember;

public class ProfileActivity extends Activity implements CursorHolder, LoaderHolder
{
	
	private static final String TAG = ProfileActivity.class.getSimpleName();
	
	@InjectView(R.id.progressBar) ProgressBar progressBar;
	@InjectView(R.id.userLayout) LinearLayout userLayout;
	@InjectView(R.id.imageViewAvatar) ImageView imageViewAvatar;
	@InjectView(R.id.textViewLogin) TextView textViewLogin;
	@InjectView(R.id.textViewEmail) TextView textViewEmail;
	@InjectView(R.id.textViewName) TextView textViewName;
	@InjectView(R.id.buttonFollow) Button buttonFollow;
	@InjectView(R.id.buttonFollowing) Button buttonFollowing;
	@InjectView(R.id.buttonFollowers) Button buttonFollowers;
	
	private LoaderCallbacks<Cursor> callbacks;
	
	private HashMap<String, String> user = new  HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		ButterKnife.inject(this);
		
		Intent intent = getIntent();
		String login = intent.getStringExtra("login");
		
		//
		callbacks = new UsersCallbacks(this, this) ;
		// You typically initialize a Loader within the activity's onCreate() method, or within the fragment's onActivityCreated() method.
		LoaderManager loaderManager = getLoaderManager() ;
		// 
		Bundle args = new Bundle();
		// set the filter for receive only followed users => true
		args.putString(Users.COLUMN_NAME, login);
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_USERS, args, callbacks);
		
		
    	// Get the githubmemeber user info
    	ApiManager.getGitHubMember(login)
	    	.subscribeOn(Schedulers.io())
	    	.observeOn(AndroidSchedulers.mainThread())
	    	.subscribe(new Action1<GitHubMember>() {
				@Override
				public void call(GitHubMember gitHubMember) {
					Log.d(TAG, "Login : " + user.get(Users.COLUMN_NAME)) ;
					String txtBtnFollow = "Follow";
					if(user.get(Users.COLUMN_FOLLOWED) == "1"){
						txtBtnFollow = "Unfollow";
					}
					
					// Setting my view
					textViewLogin.setText(gitHubMember.login);
					textViewEmail.setText(gitHubMember.email);
					textViewName.setText(gitHubMember.name);
					buttonFollow.setText(txtBtnFollow);
					buttonFollowing.setText(gitHubMember.following + " Following");
					buttonFollowers.setText(gitHubMember.followers + " Followers");
					userLayout.setVisibility(View.VISIBLE);
					
					// Async observable to display avatar
					Observable.just(gitHubMember.avatar_url)
				    	.map(new Func1<String, Bitmap>(){
				    		@Override
				    		public Bitmap call(String src){
				    			try {
				    		        URL url = new URL(src);
				    		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				    		        connection.setDoInput(true);
				    		        connection.connect();
				    		        InputStream input = connection.getInputStream();
				    		        Bitmap myBitmap = BitmapFactory.decodeStream(input);
				    		        return myBitmap;
				    		    } catch (IOException e) {
				    		        // Log exception
				    		        return null;
				    		    }
				    		}
				    	})
				    	.subscribeOn(Schedulers.newThread())
				    	.observeOn(AndroidSchedulers.mainThread())
				    	.subscribe(new Action1<Bitmap>() {
							@Override
							public void call(Bitmap bitmap) {
								imageViewAvatar.setImageBitmap(bitmap);
								imageViewAvatar.setVisibility(View.VISIBLE);
								progressBar.setVisibility(View.GONE);
								
							}
						});
					
					
				}
			});
	}
	
	@Override
	public void restartLoader(int loaderId, Bundle args)
	{
		Log.d(TAG, "restartLoader()") ;
		getLoaderManager().restartLoader(loaderId, args, callbacks) ;
	}

	@Override
	public void onNewCursor(Cursor cursor)
	{
		if(cursor==null) {
			Log.d(TAG, "cursor is NULL()") ;
			return; 
		}
		
		user.clear();
		cursor.moveToFirst(); // Car peut etre deja iteré par d'autre controller
		while(!cursor.isAfterLast()) 
		{
			user.put(Users.COLUMN_USER_ID, cursor.getString(UsersDao.IDX_USER_ID));
			user.put(Users.COLUMN_NAME, cursor.getString(UsersDao.IDX_NAME));
			user.put(Users.COLUMN_EMAIL, cursor.getString(UsersDao.IDX_EMAIL));
			user.put(Users.COLUMN_FOLLOWED, cursor.getString(UsersDao.IDX_FOLLOWED));
			user.put(Users.COLUMN_FOLLOWERS, cursor.getString(UsersDao.IDX_FOLLOWERS));
			cursor.moveToNext();
		}
	}

}
