package com.rae.placetobe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.util.ApiManager;
import com.rae.placetobe.util.GitHubMember;

public class SearchUserActivity extends Activity {
	
	private static final String TAG = SearchUserActivity.class.getSimpleName();
	
	@InjectView(R.id.autoCompleteTextView) AutoCompleteTextView userSearchInput;
	@InjectView(R.id.imageViewAvatar) ImageView imageViewAvatar;
	@InjectView(R.id.textViewLogin) TextView textViewLogin;
	@InjectView(R.id.textViewEmail) TextView textViewEmail;
	@InjectView(R.id.textViewName) TextView textViewName;
	
	// Login List of HB user
	private static final String[] GITHUB_MEMBERS = new String[]{
		"AnthonyFontaine", "RobertBakic", "ebonal", "motof", "MiloIgor", "rmarcou", "moea-chan", "uanatol", "tpuch24", "Darthevel", "Argeel", "MichaelAdjedj"
	};
	// List for stock gitHub Members
	private List<GitHubMember> gitHubMembers;
	private List<String> simpleGitHubMembers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user);
		ButterKnife.inject(this);
		
		// Init my list of gitHub Members
		gitHubMembers = new ArrayList<GitHubMember>();
		simpleGitHubMembers = new ArrayList<String>();
		
		// Observable on HB user list
		// Get the github account link to my user
		Observable<GitHubMember> o1 = Observable.from(GITHUB_MEMBERS)
			.flatMap(new Func1<String, Observable<GitHubMember>>(){
	    		@Override
	    		public Observable<GitHubMember> call(String s){
					return ApiManager.getGitHubMember(s);
	    		}
	    	});
		
		// Observable on Retrofit github members list
		Observable<GitHubMember> o2 = ApiManager.getGitHubMembersList()
			.flatMap(new Func1<List<GitHubMember>, Observable<GitHubMember>>() {
		        @Override
		        public Observable<GitHubMember> call(List<GitHubMember> gitHubMembers) {
		            return Observable.from(gitHubMembers);
		        }
		    });
		
		// Observable the two observable
		// Put the github member into a list of github member
		// Set the adapter and click listener UI
		Observable.merge(o1,o2)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Subscriber<GitHubMember>() {
		        @Override
		        public void onNext(GitHubMember gitHubMember) { 
		        	Log.d(TAG, "GITHUB_MEMBERS : " + gitHubMember.login);
	        	 	gitHubMembers.add(gitHubMember);
	        	 	simpleGitHubMembers.add(gitHubMember.login);
		        }
		
		        @Override
		        public void onCompleted() { 
		    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
	                android.R.layout.simple_dropdown_item_1line, simpleGitHubMembers);
			        userSearchInput.setAdapter(adapter);
			        userSearchInput.setOnItemClickListener(new autoCompleteClickListener()); 
		        }
		
		        @Override
		        public void onError(Throwable e) { Log.e(TAG, "Ouch!"); }
		    });  
	}
	
	private class autoCompleteClickListener implements ListView.OnItemClickListener
	{
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {	
	    	// Get the item selected
	    	String selected = (String) parent.getItemAtPosition(position);
	    	
	    	// Get the githubmemeber user info
	    	ApiManager.getGitHubMember(selected)
		    	.subscribeOn(Schedulers.io())
		    	.observeOn(AndroidSchedulers.mainThread())
		    	.subscribe(new Action1<GitHubMember>() {
					@Override
					public void call(GitHubMember gitHubMember) {
						textViewLogin.setText(gitHubMember.login);
						textViewEmail.setText(gitHubMember.email);
						textViewName.setText(gitHubMember.name);
						
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
								}
							});
					}
				});
	    	
	    }
	}
	
}
