package com.rae.placetobe.util;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class ApiManager {
	
	private interface ApiManagerService {
        @GET("/users/{username}")
        GitHubMember getMember(@Path("username") String username);
        
        @GET("/users")
        List<GitHubMember> getMembersList();
    }

    private static final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("https://api.github.com").build();

    private static final ApiManagerService apiManager = restAdapter.create(ApiManagerService.class);
	
	public static GitHubMember getGitHubMember(final String username) {
		return apiManager.getMember(username);
	}
	
	public static List<GitHubMember> getGitHubMembersList() {
		return apiManager.getMembersList();
	}
	
}
