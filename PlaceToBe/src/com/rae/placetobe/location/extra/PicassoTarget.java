package com.rae.placetobe.location.extra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.rae.placetobe.R;
import com.rae.placetobe.model.ImageData;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class PicassoTarget implements Target
{
	private final IconGenerator mIconGenerator;
	private final ImageView mImageView;
    private final ImageData imageData ;
    private final ClusterManager<ImageData> clusterManager  ;
    private final DefaultClusterRenderer<ImageData> renderer  ;
    
    public PicassoTarget(Context context, DefaultClusterRenderer<ImageData> renderer, ClusterManager<ImageData> clusterManager, ImageData imageData)
    {
    	this.clusterManager = clusterManager ;    	
    	this.renderer = renderer ;
		this.imageData = imageData ;
    	
		int mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
    	mIconGenerator = new IconGenerator(context);

    	mImageView = new ImageView(context);
    	mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));

		int padding = (int)context.getResources().getDimension(R.dimen.custom_profile_padding);
    	mImageView.setPadding(padding, padding, padding, padding);  
    }
    
    @Override
	public void onPrepareLoad(Drawable arg0) {
		// TODO Auto-generated method stub
	}

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, LoadedFrom from) 
    {
    	Log.i("onBitmapLoaded()", "Bitmap loaded");
   	
    	mImageView.setImageBitmap(bitmap);
//    	mImageView.refreshDrawableState();
		mIconGenerator.setContentView(mImageView);

		Marker marker = renderer.getMarker(imageData) ;
    	Log.i("onBitmapLoaded()", "Marker : " + marker);
		if(marker!=null) {
			marker.setIcon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon()));
		}
	}

	@Override
	public void onBitmapFailed(Drawable arg0) {
		Log.i("onBitmapLoaded()", "onBitmapFailed !!!!");		
	}
}