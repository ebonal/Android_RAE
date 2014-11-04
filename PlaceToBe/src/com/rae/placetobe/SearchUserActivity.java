package com.rae.placetobe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import rx.Observable;
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
	
	List<GitHubMember> gitHubMembers;
	
	private static final String[] GITHUB_MEMBERS = new String[]{
		"AnthonyFontaine", "RobertBakic", "ebonal", "motof", "MiloIgor", "rmarcou", "moea-chan", "uanatol", "tpuch24", "Darthevel", "Argeel", "MichaelAdjedj"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user);
		ButterKnife.inject(this);
		
		Observable.just("")
	    	.map(new Func1<String, List<GitHubMember>>(){
	    		@Override
	    		public List<GitHubMember> call(String s){
	    			return ApiManager.getGitHubMembersList();
	    		}
	    	})
	    	.subscribeOn(Schedulers.newThread())
	    	.observeOn(AndroidSchedulers.mainThread())
	    	.subscribe(new Action1<List<GitHubMember>>() {
				@Override
				public void call(List<GitHubMember> members) {
					gitHubMembers = members;
					for (GitHubMember member : gitHubMembers) {
						Log.d(TAG, member.login);
					}
				}
			});

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, GITHUB_MEMBERS);
		
        userSearchInput.setAdapter(adapter);
        userSearchInput.setOnItemClickListener(new autoCompleteClickListener());   
	}
	
	private class autoCompleteClickListener implements ListView.OnItemClickListener
	{
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {	    	
	    	String selected = (String) parent.getItemAtPosition(position);
	    	
	    	Observable.just(selected)
				.map(new Func1<String, GitHubMember>(){
		    		@Override
		    		public GitHubMember call(String s){
						return ApiManager.getGitHubMember(s);
		    		}
		    	})
		    	.subscribeOn(Schedulers.io())
		    	.observeOn(AndroidSchedulers.mainThread())
		    	.subscribe(new Action1<GitHubMember>() {
					@Override
					public void call(GitHubMember gitHubMember) {
						textViewLogin.setText(gitHubMember.login);
						textViewEmail.setText(gitHubMember.email);
						textViewName.setText(gitHubMember.name);
						
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
