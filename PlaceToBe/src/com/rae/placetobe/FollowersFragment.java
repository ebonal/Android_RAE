package com.rae.placetobe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.rae.placetobe.util.FollowData;

public class FollowersFragment extends Fragment
{
	
	private ListView listViewFollowers;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_followers,
				container, false);
		
		listViewFollowers = (ListView) rootView
				.findViewById(R.id.listViewFollowers);
		
		FollowData followData = new FollowData(getActivity());
		

		String prefListFollowers = followData.getListFollowersPref();

		if (prefListFollowers.isEmpty())
			Toast.makeText(getActivity(), "Followers List empty !",
					Toast.LENGTH_LONG).show();
		
		//test 
		prefListFollowers = "Lelouch vi Britannia-lelouch@britannia.com;Emeric-ebonal@hotmail.fr;Anthony-anthonyfontaine34@gmail.com;Robert-robert.bakic@gmail.com";
		
		FollowData.stringToListMap(prefListFollowers);

		FollowData.setSimpleAdapterToListView(listViewFollowers);
		
		return rootView;
	}

}
