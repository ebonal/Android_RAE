package com.rae.placetobe.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.util.FollowData;
import com.rae.placetobe.R;

public class FollowersFragment extends Fragment
{

	@InjectView(R.id.listViewFollowers)  ListView listViewFollowers;
	private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_followers,
				container, false);
		ButterKnife.inject(this, rootView);

		FollowData followData = new FollowData(getActivity());

		String prefListFollowers = followData.getListFollowersPref();

		if (prefListFollowers.isEmpty())
		{
			Toast.makeText(getActivity(), "Followers List empty !",
					Toast.LENGTH_LONG).show();
			prefListFollowers = "Lelouch vi Britannia-lelouch@britannia.com;Emeric-ebonal@hotmail.fr;Anthony-anthonyfontaine34@gmail.com;Robert-robert.bakic@gmail.com";
		}
		FollowData.fillListWithString(prefListFollowers, list);

		FollowData.setSimpleAdapterToListView(listViewFollowers, list);

		return rootView;
	}

}
