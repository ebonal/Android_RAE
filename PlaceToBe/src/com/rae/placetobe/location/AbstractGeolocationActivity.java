package com.rae.placetobe.location;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.rae.placetobe.R;

abstract public class AbstractGeolocationActivity extends Activity
		implements LocationListener, AddressHolder,
				   GooglePlayServicesClient.ConnectionCallbacks,
				   GooglePlayServicesClient.OnConnectionFailedListener
{
	private static final String TAG = AbstractGeolocationActivity.class.getSimpleName();

	abstract protected void setGeoStatus(int status);

	abstract protected void updateLocation(Location location);

	// A request to connect to Location Services
	private LocationRequest locationRequest;

	// Stores the current instantiation of the location client in this object
	private LocationClient locationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);

		// Create a new global location parameters object
		locationRequest = LocationRequest.create();

		/*
		 * Set the update interval
		 */
		locationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

		// Use high accuracy
		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		// Set the interval ceiling to one minute
		locationRequest
				.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

		/*
		 * Create a new location client, using the enclosing class to handle
		 * callbacks.
		 */
		locationClient = new LocationClient(this, this, this);
	}

	/*
	 * Called when the Activity is no longer visible at all. Stop updates and
	 * disconnect.
	 */
	@Override
	public void onStop()
	{
		Log.d(TAG, "onStop()");
		
		// If the client is connected
		if (locationClient.isConnected()) {
			stopPeriodicUpdates();
		}

		// After disconnect() is called, the client is considered "dead".
		locationClient.disconnect();

		super.onStop();
	}

	/**
	 * In response to a request to start updates, send a request to Location
	 * Services
	 */
	final private void startPeriodicUpdates()
	{
		Log.d(TAG, "startPeriodicUpdates()");
		locationClient.requestLocationUpdates(locationRequest, this);
		setGeoStatus(R.string.location_requested);
	}

	/**
	 * In response to a request to stop updates, send a request to Location
	 * Services
	 */
	final private void stopPeriodicUpdates()
	{
		Log.d(TAG, "stopPeriodicUpdates()");
		locationClient.removeLocationUpdates(this);
		setGeoStatus(R.string.location_updates_stopped);
	}

	/*
	 * Called when the Activity is restarted, even before it becomes visible.
	 */
	@Override
	public void onStart()
	{
		Log.d(TAG, "onStart()");
		super.onStart();

		// Connect the client. Don't re-start any requests here; instead, wait
		// or onResume()
		locationClient.connect();
	}

	/**
	 * Invoked by the "Get Location" button.
	 * 
	 * Calls getLastLocation() to get the current location
	 */
	public Location getCurrentLocation()
	{
		Log.d(TAG, "getCurrentLocation()");

		// If Google Play Services is available
		if (LocationUtils.isServicesConnected(this))
			// Get the current location
			return locationClient.getLastLocation();

		return null;
	}

	/**
	 * Invoked by the "Get Address" button. Get the address of the current
	 * location, using reverse geocoding. This only works if a geocoding service
	 * is available.
	 */
	// BOB : Il faut avoir recuperer au moins une fois la location (request
	// update demarré)
	final public boolean startGetAddressTask(Location location)
	{
		Log.d(TAG, "startGetAddressTask() : " + location);

		// In Gingerbread and later, use Geocoder.isPresent() to see if a
		// geocoder is available.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
				&& !Geocoder.isPresent())
		{
			// No geocoder is present. Issue an error message
			Toast.makeText(this, R.string.no_geocoder_available,
					Toast.LENGTH_LONG).show();
			return false;
		}

		if (!LocationUtils.isServicesConnected(this))
			return false; // Display message

		if (location == null)
			return false; // Task not launch

		// Start the background task
		new GetAddressTask(this, this).execute(location);
		return true;
	}

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle bundle)
	{
		Log.d(TAG, "onConnected()");
		setGeoStatus(R.string.connected);
		startPeriodicUpdates();
	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected()
	{
		Log.d(TAG, "onDisconnected()");
		setGeoStatus(R.string.disconnected);
	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult)
	{
		Log.d(TAG, "onConnectionFailed()");
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution())
		{
			try
			{
				// Start an Activity that tries to resolve the error
				connectionResult
						.startResolutionForResult(
								this,
								ErrorDialogFragment.CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e)
			{
				// Log the error
				e.printStackTrace();
			}
		} else
		{
			// If no resolution is available, display a dialog to the user with
			// the error.
			ErrorDialogFragment.showErrorDialog(this, connectionResult.getErrorCode());
		}
	}

	/**
	 * Report location updates to the UI.
	 * 
	 * @param location
	 *            The updated location.
	 */
	@Override
	public void onLocationChanged(Location location)
	{
		Log.d(TAG, "onLocationChanged() : " + location);

		// Report to the UI that the location was updated
		setGeoStatus(R.string.location_updated);

		// In the UI, set the latitude and longitude to the value received
		updateLocation(location);
	}
}
