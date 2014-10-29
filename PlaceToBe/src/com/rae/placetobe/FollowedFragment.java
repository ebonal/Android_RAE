package com.rae.placetobe;

import com.rae.placetobe.util.SharedPreferencesUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FollowedFragment extends Fragment
{
	private String list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_followed, container, false);
		
		final SharedPreferences pref = SharedPreferencesUtil.getAccountDataPreferences(getActivity());
		list = pref.getString("list_followed", "");
		
		if (list == "")
			Toast.makeText(getActivity(), "Followed List empty !", Toast.LENGTH_LONG).show();
		
		
	    
		return rootView;
	}

}
