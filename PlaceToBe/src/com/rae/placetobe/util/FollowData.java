package com.rae.placetobe.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FollowData
{
	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String LIST_FOLLOWED = "list_followed";
	private static final String LIST_FOLLOWERS = "list_followers";
	private static Context context;
	private static List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	
	public FollowData(Context c)
	{
		context = c;
	}
	
	public String getListFollowedPref()
	{
		String prefListFollowed;		
		SharedPreferences pref = SharedPreferencesUtil.getAccountDataPreferences(context);
		prefListFollowed = pref.getString(LIST_FOLLOWED, "");
		return prefListFollowed;
	}
	
	public String getListFollowersPref()
	{
		String prefListFollowers;		
		SharedPreferences pref = SharedPreferencesUtil.getAccountDataPreferences(context);
		prefListFollowers = pref.getString(LIST_FOLLOWERS, "");
		return prefListFollowers;
	}
	
	// Fill a List<HashMap<String, String>> with a string
	static public void stringToListMap(String str)
	{
		String[] elements = str.split(";");
		HashMap<String, String> item;
		
		for (int i = 0; i < elements.length; i++)
		{
			String[] e = elements[i].split("-");
			
			item = new HashMap<String, String>();
			item.put(NAME, e[0]);
			item.put(EMAIL, e[1]);
			list.add(item);
		}
	}
	
	// create a SimpleAdapter and set it to the listView
	static public void setSimpleAdapterToListView(ListView listView)
	{
		ListAdapter adapter = new SimpleAdapter(context, list,
				android.R.layout.simple_list_item_2, new String[] { NAME,
						EMAIL }, new int[] { android.R.id.text1,
						android.R.id.text2 });

		listView.setAdapter(adapter);
	}
	
}
