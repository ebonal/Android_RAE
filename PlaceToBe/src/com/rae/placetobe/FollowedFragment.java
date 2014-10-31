package com.rae.placetobe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.rae.placetobe.util.SharedPreferencesUtil;

public class FollowedFragment extends Fragment
{
	private String prefList;
	private ListView listViewFollowed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_followed, container, false);
		
		listViewFollowed = (ListView) rootView.findViewById(R.id.listViewFollowed);

		final SharedPreferences pref = SharedPreferencesUtil
				.getAccountDataPreferences(getActivity());
		prefList = pref.getString("list_followed", "");

		if (prefList == "")
			Toast.makeText(getActivity(), "Followed List empty !",
					Toast.LENGTH_LONG).show();

		// List initialization
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		
		StringToListMap(prefList, list);
		
		SetSimpleAdapterToListView(getActivity(), list, listViewFollowed);

		return rootView;
	}
	
	// Fill a List<HashMap<String, String>> with a string
	static void StringToListMap(String str, List<HashMap<String, String>> list)
	{
		
		
	}

	// create a SimpleAdapter and set it to the listView
	static void SetSimpleAdapterToListView(Context context, List<HashMap<String, String>> listItem, ListView listView)
	{
		ListAdapter adapter = new SimpleAdapter(
				context, 
				listItem,
				android.R.layout.simple_list_item_2, 
				new String[] { "text1", "text2" }, 
				new int[] { android.R.id.text1, android.R.id.text2 });

		listView.setAdapter(adapter);
	}
	
}
