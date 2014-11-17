package com.rae.placetobe.map;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.data.CursorHelper;
import com.rae.placetobe.data.ImagesDao;
import com.rae.placetobe.debug.ImagesDebugCallbacks;
import com.rae.placetobe.framework.CursorHolder;
import com.rae.placetobe.framework.LoaderHolder;
import com.rae.placetobe.history.ClusterHistoryActivity;
import com.rae.placetobe.location.extra.ImageRenderer;
import com.rae.placetobe.model.ImageData;

public class PtbMapActivity extends AbstractDrawerActivity  implements CursorHolder, LoaderHolder, ClusterManager.OnClusterClickListener<ImageData>
{
	private static final String TAG = PtbMapActivity.class.getSimpleName();

	private LoaderCallbacks<Cursor> callbacks ;
    private GoogleMap googleMap;

    private ClusterManager<ImageData> mClusterManager;
    
	@Override
	protected int getContentViewId() {
		return R.layout.activity_ptb_map ;
	}

    private boolean ensureInitMap()
    {	
    	if(mClusterManager!=null) return true ; // OK
    	
		if(googleMap==null)
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.fragMap)).getMap();
		
		if(googleMap==null) {
			Log.d("TAG", "MAP not available") ;
			return false ;
		}
		
        mClusterManager = new ClusterManager<ImageData>(this, googleMap);		
        mClusterManager.setRenderer(new ImageRenderer(this, googleMap, mClusterManager));
        
        mClusterManager.setOnClusterClickListener(this);

        
		UiSettings settings = googleMap.getUiSettings() ;
		settings.setMyLocationButtonEnabled(true) ;	
		googleMap.setMyLocationEnabled(true);
        googleMap.setOnCameraChangeListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
        googleMap.setOnInfoWindowClickListener(mClusterManager);
        
		return true ;
    }
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ensureInitMap() ;

    	callbacks = new ImagesDebugCallbacks(this, this) ;		

		// You typically initialize a Loader within the activity's onCreate() method, or within the fragment's onActivityCreated() method.
		LoaderManager loaderManager = getLoaderManager() ;
		// Prepare the loader.  Either re-connect with an existing one, or start a new one.
		loaderManager.initLoader(AbstractDrawerActivity.LOADER_IMAGES, null /*args*/, callbacks); 
    }
        
	@Override
	protected void onResume() {
		super.onResume();
		ensureInitMap() ;
	}

	@Override
	public void restartLoader(int loaderId, Bundle args) {
		Log.d(TAG, "restartLoader()") ;
		getLoaderManager().restartLoader(loaderId, args, callbacks) ;
	}

	static final LatLng PARIS            = new LatLng(48.852986, 2.349975); // ND de Paris
	static final LatLng MONTPELLIER      = new LatLng(43.600   , 3.883);
	static final LatLng DEFAULT_LOCATION = MONTPELLIER ;
    
	@Override
	public void onNewCursor(Cursor cursor)
	{
		Log.i(TAG, "onNewCursor") ;
		if(cursor==null) {
			Log.d(TAG, "cursor is NULL()") ;
			return; 
		}
		
		LatLng lastPos = null;
		LatLng curLatLng ;
		
		cursor.moveToFirst(); // Car peut etre deja iteré par d'autre controller
		while(!cursor.isAfterLast()) 
		{
			int id           = cursor.getInt   (ImagesDao.IDX__ID) ;
			String path      = cursor.getString(ImagesDao.IDX_PATH) ;
			String comment   = cursor.getString(ImagesDao.IDX_COMMENT) ;			
			String timestamp = cursor.getString(ImagesDao.IDX_DATE) ;			
			Double lat = CursorHelper.getNDouble(cursor, ImagesDao.IDX_LAT) ;
			Double lng = CursorHelper.getNDouble(cursor, ImagesDao.IDX_LNG) ;
			
			if(lat!=null) {
				curLatLng = new LatLng(lat, lng);
				if(lastPos==null) lastPos = curLatLng ;			
							
				// Extra
                mClusterManager.addItem(new ImageData(id, path, comment, timestamp, curLatLng));
			}
			
			cursor.moveToNext();
		}
		
		// Move the camera instantly to lastPos with a zoom of 15.
		/*if(lastPos==null)*/  lastPos = DEFAULT_LOCATION ;
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPos, 5));
	}

	@Override
	public boolean onClusterClick(Cluster<ImageData> cluster)
	{
		int i = 0 ;
		Parcelable[] ps = new Parcelable[cluster.getItems().size()] ;
		for(ImageData iData : cluster.getItems()) ps[i++] = iData;
		
		
	   	Intent intent = new Intent(this, ClusterHistoryActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP) ;
	    intent.putExtra(ClusterHistoryActivity.EXTRA_PHOTOS, ps) ;
	    startActivity(intent);		

	    // Show a toast with some info when the cluster is clicked.
        String firstName = cluster.getItems().iterator().next().getTimestamp();
        Toast.makeText(this, cluster.getSize() + " (including " + firstName + ")", Toast.LENGTH_SHORT).show();
        return true;
	}
}
