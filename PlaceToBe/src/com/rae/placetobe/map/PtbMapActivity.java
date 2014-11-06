package com.rae.placetobe.map;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.data.CursorHelper;
import com.rae.placetobe.data.ImagesDao;
import com.rae.placetobe.debug.ImagesDebugCallbacks;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;

public class PtbMapActivity extends AbstractDrawerActivity  implements CursorHolder, LoaderHolder
{
	private static final String TAG = PtbMapActivity.class.getSimpleName();

	private LoaderCallbacks<Cursor> callbacks ;
    private GoogleMap googleMap;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_ptb_map ;
	}
		
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragMap)).getMap();
        
    	callbacks = new ImagesDebugCallbacks(this, this) ;		

		// You typically initialize a Loader within the activity's onCreate() method, or within the fragment's onActivityCreated() method.
		LoaderManager loaderManager = getLoaderManager() ;
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_IMAGES, null /*args*/, callbacks); 
    }
    
	@Override
	protected void onResume()
	{
		super.onResume();

		if(googleMap==null)
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragMap)).getMap();
		
		if(googleMap==null) {
			Log.d("TAG", "MAP not available") ;
			return ;
		}
		
		UiSettings settings = googleMap.getUiSettings() ;
		settings.setMyLocationButtonEnabled(true) ;	
		googleMap.setMyLocationEnabled(true);
	}

	@Override
	public void restartLoader(int loaderId, Bundle args) {
		Log.d(TAG, "restartLoader()") ;
		getLoaderManager().restartLoader(loaderId, args, callbacks) ;
	}

	static final LatLng DEFAULT_LOCATION = new LatLng(48.852986, 2.349975); // ND de Paris

	@Override
	public void onNewCursor(Cursor cursor)
	{
		if(cursor==null) {
			Log.d(TAG, "cursor is NULL()") ;
			return; 
		}
		
		BitmapDescriptor bmHueGreen  = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN) ;
		BitmapDescriptor bmHueOrange = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE) ;
		
		LatLng lastPos = null;
		LatLng curLatLng ;
		
		cursor.moveToFirst(); // Car peut etre deja iteré par d'autre controller
		while(!cursor.isAfterLast()) 
		{
			int id         = cursor.getInt   (ImagesDao.IDX__ID) ;
			String comment = cursor.getString(ImagesDao.IDX_COMMENT) ;			
			Double lat = CursorHelper.getNDouble(cursor, ImagesDao.IDX_LAT) ;
			Double lng = CursorHelper.getNDouble(cursor, ImagesDao.IDX_LNG) ;
			if(lat!=null) 
			{
				curLatLng = new LatLng(lat, lng);
				if(lastPos==null) lastPos = curLatLng ;			
			
				MarkerOptions mo = new MarkerOptions()
		        	.position(curLatLng)
		        	.title(comment) ;

				if(id%2!=0) mo.icon(bmHueGreen);
				else	 	mo.icon(bmHueOrange);

				googleMap.addMarker(mo) ;				
			}
			
			cursor.moveToNext();
		}
		
		// Move the camera instantly to lastPos with a zoom of 15.
		if(lastPos==null)  lastPos = DEFAULT_LOCATION ;
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPos, 15));
	}
}
