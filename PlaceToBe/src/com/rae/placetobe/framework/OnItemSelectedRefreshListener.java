package com.rae.placetobe.framework;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class OnItemSelectedRefreshListener implements OnItemSelectedListener
{
	static public interface Refreshable {
		void refresh() ;
	}

	final private  Refreshable refreshable ;
	
	public OnItemSelectedRefreshListener(Refreshable refreshable) {
		this.refreshable = refreshable ;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		refreshable.refresh() ;
	}

	@Override
	final public void onNothingSelected(AdapterView<?> parent) {
	}
	
}
