package com.rae.placetobe.util;

import java.util.Locale;

public class GitHubMember {
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
}