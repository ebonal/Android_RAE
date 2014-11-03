package com.rae.placetobe.util;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

import com.rae.placetobe.SearchUserActivity.GitHubMember;

public class ApiManager {
	
	private interface ApiManagerService {
        @GET("/users/{username}")
        GitHubMember getMember(@Path("username") String username);
    }

    private static final RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("https://api.github.com")
            .build();

    private static final ApiManagerService apiManager = restAdapter.create(ApiManagerService.class);

	public static ApiManagerService getApimanager() {
		return apiManager;
	}
	
}
