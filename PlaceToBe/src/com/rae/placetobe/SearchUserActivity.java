package com.rae.placetobe;

import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchUserActivity extends Activity {
	
	@InjectView(R.id.editText) EditText searchInput;
	@InjectView(R.id.listView) ListView resultList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_user);
		ButterKnife.inject(this);	
	}
	
	public class GitHubMember {
	    public String login;
	    public int followers;

	    @Override
	    public String toString() {
	        return String.format(Locale.US, "%s: %d followers", login, followers);
	    }
	}
}
