package com.rae.placetobe.location;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Define a DialogFragment to display the error dialog generated in
 * showErrorDialog.
 */
public class ErrorDialogFragment extends DialogFragment 
{
	private static final String TAG = ErrorDialogFragment.class.getSimpleName() ;
	
    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    /**
     * Show a dialog returned by Google Play services for the
     * connection error code
     *
     * @param errorCode An error code returned from onConnectionFailed
     */
    static public void showErrorDialog(Activity activity, int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
            errorCode,
            activity,
            CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(activity.getFragmentManager(), TAG);
        }
    }
    
	
	
	
	
	

    // Global field to contain the error dialog
    private Dialog mDialog;

    /**
     * Default constructor. Sets the dialog field to null
     */
    public ErrorDialogFragment() {
        super();
        mDialog = null;
    }

    /**
     * Set the dialog to display
     *
     * @param dialog An error dialog
     */
    public void setDialog(Dialog dialog) {
        mDialog = dialog;
    }

    /*
     * This method must return a Dialog to the DialogFragment.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return mDialog;
    }
}

