package com.rae.placetobe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AccountActivity extends Activity
{
	private TabHost tabHost;
	private TabSpec tabSpec;
	
	private String name;
	private String email;
	
	private Button btSave = (Button) findViewById(R.id.bt_save);
	private EditText editTextName = (EditText) findViewById(R.id.editTextName);
	private EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		tabSpec = tabHost.newTabSpec("account").setIndicator(getResources().getString(R.string.account_tab_account));
	    tabHost.addTab(tabSpec);
	    tabSpec = tabHost.newTabSpec("followed").setIndicator(getResources().getString(R.string.account_tab_followed));
	    tabHost.addTab(tabSpec);
	    tabSpec = tabHost.newTabSpec("followers").setIndicator(getResources().getString(R.string.account_tab_followers));
	    tabHost.addTab(tabSpec);
	    
	    final SharedPreferences pref = getPreferences(MODE_PRIVATE);
	    name = pref.getString("account_name", "");
	    email = pref.getString("account_email", "");
	    
	    if (name != "")
	    	editTextName.setText(name);
	    if (email != "")
	    	editTextEmail.setText(email);
	    
	    btSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Editor editor = pref.edit();	
    	    	editor.putString("account_name", editTextName.getText().toString());
    	    	editor.putString("account_email", editTextEmail.getText().toString());
    	    	editor.commit();
            }
        });
	    
	    
	    
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
