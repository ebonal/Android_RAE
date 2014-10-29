package com.rae.placetobe;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.rae.placetobe.util.SharedPreferencesUtil;

public class AccountFragment extends Fragment
{
	private String name;
	private String email;
	
	private Button btSave;
	private EditText editTextName;
	private EditText editTextEmail;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_account, container, false);
		
		final SharedPreferences pref = SharedPreferencesUtil.getAccountDataPreferences(getActivity());
	    name = pref.getString("account_name", "");
	    email = pref.getString("account_email", "");
	    
	    editTextName = (EditText) getActivity().findViewById(R.id.editTextName);
	    editTextEmail = (EditText) getActivity().findViewById(R.id.editTextEmail);
	    
	    if (name != "")
	    	editTextName.setText(name);
	    if (email != "")
	    	editTextEmail.setText(email);
	    
	    btSave = (Button) getActivity().findViewById(R.id.bt_save);
	    
	   /* btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Editor editor = pref.edit();	
    	    	editor.putString("account_name", editTextName.getText().toString());
    	    	editor.putString("account_email", editTextEmail.getText().toString());
    	    	editor.commit();
            }
        });*/
	    
		return rootView;
	}
	/*

	    // Tab 2
	    Set<String> prefListFollowed = pref.getStringSet("account_followed", new HashSet<String>());
	    
	    ArrayList<String> list = new ArrayList<String>();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, list);
	    
	    listFollowed = (ListView) findViewById(R.id.listViewFollowed);
	    listFollowed.setAdapter(adapter);
	    
	}*/


}
