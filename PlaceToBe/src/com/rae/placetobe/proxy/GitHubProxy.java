package com.rae.placetobe.proxy;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import android.util.Log;
import android.util.Pair;

public class GitHubProxy
{
	private static final String TAG = GitHubProxy.class.getSimpleName();

	// https://github.com/ebonal/Android_RAE.git
	final static private String GITHUB_COM = "https://api.github.com" ;  // The base API endpoint.
		
	public class Contributor {
		public String login; // GitHub username.
		public int contributions; // Commit count.
	}

	public interface GitHubStub 
	{
		@GET("/repos/{owner}/{repo}/contributors")
		List<Contributor> contributors(@Path("owner") String owner,
									   @Path("repo") String repo);
	}

	static private final GitHubStub githubStub ;
	static {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(GITHUB_COM).build();
		githubStub = restAdapter.create(GitHubStub.class);
	}
	
	static public void test() 
	{
        Observable.just(new Pair<String,String>("ebonal", "Android_RAE"))
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe(new Action1<Pair<String, String>>() 
        {
	        @Override
	        public void call(Pair<String, String> ownerRepo)
	        {
	    		Log.d(TAG, "DEBUT TEST");		
	
				List<Contributor> contributors = githubStub.contributors(ownerRepo.first, ownerRepo.second);
				for (Contributor contributor : contributors) {
					Log.d(TAG, contributor.login + " - " + contributor.contributions);
				}

				Log.d(TAG, "FIN TEST");		
	        }
	        
	        
	    });
	}
}
