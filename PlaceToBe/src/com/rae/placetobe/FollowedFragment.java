package com.rae.placetobe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.rae.placetobe.util.FollowData;

public class FollowedFragment extends Fragment
{
	private ListView listViewFollowed;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_followed, container,
				false);

		listViewFollowed = (ListView) rootView
				.findViewById(R.id.listViewFollowed);

		FollowData followData = new FollowData(getActivity());
		
		String prefListFollowed = followData.getListFollowedPref();

		if (prefListFollowed.isEmpty())
			Toast.makeText(getActivity(), "Followed List empty !",
					Toast.LENGTH_LONG).show();
		
		//test 
		prefListFollowed = "Emeric-ebonal@hotmail.fr;Anthony-anthonyfontaine34@gmail.com;Robert-robert.bakic@gmail.com";
		

		FollowData.stringToListMap(prefListFollowed);

		FollowData.setSimpleAdapterToListView(listViewFollowed);

		return rootView;
	}

}
