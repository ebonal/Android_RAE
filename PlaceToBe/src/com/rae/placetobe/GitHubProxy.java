package com.rae.placetobe;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import android.os.AsyncTask;
import android.util.Log;

public class GitHubProxy
{
	private static final String TAG = GitHubProxy.class.getSimpleName();
	
	static class MyAsyncTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... urls)
		{
			// https://github.com/ebonal/Android_RAE.git
			RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
					"https://api.github.com") // The base API endpoint.
					.build();

			GitHubService github = restAdapter.create(GitHubService.class);
			List<Contributor> contributors = github.contributors("ebonal",	"Android_RAE");
			for (Contributor contributor : contributors) {
				Log.d(TAG, contributor.login + " - " + contributor.contributions);
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result)	{}
	}	
	
	public class Contributor {
		public String login; // GitHub username.
		public int contributions; // Commit count.
	}

	public interface GitHubService {
		@GET("/repos/{owner}/{repo}/contributors")
		List<Contributor> contributors(@Path("owner") String owner,	@Path("repo") String repo);
	}

	static public void test() {
		Log.d(TAG, "DEBUT TEST");		
		MyAsyncTask task = new MyAsyncTask();
        task.execute(new String[] { "NOTUSED" });		
		Log.d(TAG, "FIN TEST");		
	}
}
