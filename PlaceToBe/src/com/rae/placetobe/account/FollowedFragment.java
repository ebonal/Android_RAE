package com.rae.placetobe.account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.R;
import com.rae.placetobe.sqlite.PlaceToBeContract.Users;
import com.rae.placetobe.sqlite.PlaceToBeHelper;

public class FollowedFragment extends Fragment
{
	@InjectView(R.id.listViewFollowed)  ListView listViewFollowed;
	@InjectView(R.id.imageButtonAdd) ImageButton  btAdd;
	private List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_followed, container,
				false);
		ButterKnife.inject(this, rootView);

		PlaceToBeHelper helper = new PlaceToBeHelper(getActivity()); 
		SQLiteDatabase db = helper.getReadableDatabase();
		
		String[] projection = {
			    Users._ID,
			    Users.COLUMN_NAME_NAME,
			    Users.COLUMN_NAME_EMAIL
		};
		Cursor c = db.query(Users.TABLE_NAME, projection, null, null, null, null, null, null);
		String[] from = new String[] { Users.COLUMN_NAME_NAME, Users.COLUMN_NAME_EMAIL };
		// Fields from the row layout
		int[] to = new int[] { R.id.listViewFollowed };
		
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.id.listViewFollowed, c, from, to);
		listViewFollowed.setAdapter(adapter);
		
	    btAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(getActivity(), AddFollowedActivity.class);
        	   	startActivity(intent);
        	   	// or startActivityForResult pour recevoir un retour sur un onActivityResult avec un setResult(lancé par la nouvelle activité)
        	   	
            }
        });
		
		return rootView;
	}

}
