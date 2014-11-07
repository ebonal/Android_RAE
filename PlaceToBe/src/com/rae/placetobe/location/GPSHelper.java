package com.rae.placetobe.location;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class GPSHelper
{
	static public boolean checkPlayServices(Activity activity)
	{
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
		if (status != ConnectionResult.SUCCESS)
		{
			if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
				showErrorDialog(activity, status);
			}
			else
			{
				Toast.makeText(activity, "This device is not supported.", Toast.LENGTH_LONG).show();
				activity.finish();
			}
			return false;
		}
		return true;
	}

	static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
	static private void showErrorDialog(Activity activity, int code) {
		GooglePlayServicesUtil.getErrorDialog(code, activity, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}
	
	// snippet to include in onActivityResult()
	static public void onActivityResult(Activity activity, int requestCode, int resultCode)
	{
		if(REQUEST_CODE_RECOVER_PLAY_SERVICES!=requestCode) return ;
		if (resultCode == Activity.RESULT_CANCELED) {
			Toast.makeText(activity, "Google Play Services must be installed.", Toast.LENGTH_SHORT).show();
			activity.finish();
		}
	}

}
