package com.rae.placetobe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.rae.placetobe.util.ApiManager;
import com.rae.placetobe.util.GitHubMember;

public class ProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		Intent intent = getIntent();
		String user = intent.getStringExtra("user");
		
    	// Get the githubmemeber user info
    	ApiManager.getGitHubMember(user)
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
