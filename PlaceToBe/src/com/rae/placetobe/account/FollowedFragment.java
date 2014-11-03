package com.rae.placetobe.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.R;
import com.rae.placetobe.util.FollowData;

public class FollowedFragment extends Fragment
{
	@InjectView(R.id.listViewFollowed)  ListView listViewFollowed;
	@InjectView(R.id.imageButtonAdd) Button  btAdd;
	private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_followed, container,
				false);
		ButterKnife.inject(getActivity());

		FollowData followData = new FollowData(getActivity());

		String prefListFollowed = followData.getListFollowedPref();

		if (prefListFollowed.isEmpty())
		{
			Toast.makeText(getActivity(), "Followed List empty !",
					Toast.LENGTH_SHORT).show();
			prefListFollowed = "Emeric-ebonal@hotmail.fr;Anthony-anthonyfontaine34@gmail.com;Robert-robert.bakic@gmail.com";
		}

		FollowData.fillListWithString(prefListFollowed, list);

		FollowData.setSimpleAdapterToListView(listViewFollowed, list);
		
	    btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), AddFollowedActivity.class);
        	   	startActivity(intent);
        	   	// or startActivityForResult pour recevoir un retour sur un onActivityResult avec un setResult(lancé par la nouvelle activité)
        	   	// faire un choix un/un ou plusieurs à la fois
            }
        });
		
		return rootView;
	}

}
