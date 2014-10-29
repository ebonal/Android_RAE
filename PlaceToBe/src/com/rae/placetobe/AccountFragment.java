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
import android.widget.Toast;

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
		
		// View creation from the layout
		View rootView = inflater.inflate(R.layout.fragment_account, container, false);
		
		final SharedPreferences pref = SharedPreferencesUtil.getAccountDataPreferences(getActivity());
	    name = pref.getString("account_name", "");
	    email = pref.getString("account_email", "");
	    
	    editTextName = (EditText) rootView.findViewById(R.id.editTextName);
	    editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
	    
	    if (name != "")
	    	editTextName.setText(name);
	    if (email != "")
	    	editTextEmail.setText(email);
	    
	    // On clic we save name and email values in preferences
	    btSave = (Button) rootView.findViewById(R.id.bt_save);
	    btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Editor editor = pref.edit();	
    	    	editor.putString("account_name", editTextName.getText().toString());
    	    	editor.putString("account_email", editTextEmail.getText().toString());
    	    	editor.commit();
    	    	Toast.makeText(getActivity(), "Validé !", Toast.LENGTH_SHORT).show();
            }
        });
	    
		return rootView;
	}

}
