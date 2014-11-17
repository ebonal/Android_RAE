package com.rae.placetobe.history;

import android.content.res.Configuration;
import android.widget.GridView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;

abstract public class AbstractHistoryActivity extends AbstractDrawerActivity
{
	abstract protected GridView getGridView() ;
	
	@Override
	protected int getContentViewId() {
		return R.layout.activity_history;
	}	
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen and change the num of columns 
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	getGridView().setNumColumns(3);
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	getGridView().setNumColumns(2);
	    }
	}	
}
