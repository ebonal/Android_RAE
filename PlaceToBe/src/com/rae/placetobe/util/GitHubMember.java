package com.rae.placetobe.util;

import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.rae.placetobe.data.DBProxy;
import com.rae.placetobe.data.PlaceToBeContentProvider;
import com.rae.placetobe.data.Users;

public class GitHubMember {
	
	private static final String TAG = GitHubMember.class.getSimpleName();
	
	public int id;
    public String login;
    public int followers;
    public int following;
    public String avatar_url;
    public String name;
    public String email;
    public String bio;
    public String created_at;
    public String updated_at;
    public String message = "";

    @Override
    public String toString() {
        return String.format(Locale.US, "%s: %d followers", login, followers);
    }
    
    public Boolean isMember() {
    	Boolean isMember = true;
    	if ( message == "Not Found") isMember = false;
        return isMember;
    }
    
    public void addGitHubMemberDB (Context context)
	{
		long timestamp = System.currentTimeMillis() ;

		ContentValues values = new ContentValues();
  	    values.put(Users.COLUMN_USER_ID, id);
  	    values.put(Users.COLUMN_NAME, login);
  	    values.put(Users.COLUMN_EMAIL, login);
  	    values.put(Users.COLUMN_FOLLOWED, login);
  	    values.put(Users.COLUMN_FOLLOWERS, login);

  	    Uri uri = DBProxy.insert(context, PlaceToBeContentProvider.USERS_URI, values) ;

  	    Log.d(TAG, "URI : " + uri) ;
  	    
	}
}