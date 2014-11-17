package com.rae.placetobe.location.extra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;
import com.rae.placetobe.R;
import com.rae.placetobe.model.ImageData;
import com.rae.placetobe.util.TimeHelper;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class CustomTarget2 implements Target
{
	private final int mDimension;
	private final IconGenerator mIconGenerator;
	private final ImageView mImageView;
	
    private final ImageData imageData ;
    private final MarkerOptions markerOptions ;    
        
    private ImageRenderer renderer ;
    
    public CustomTarget2(Context context, ImageRenderer renderer, ImageData imageData, MarkerOptions markerOptions)
    {
    	this.renderer = renderer ;
    	
		mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
    	mIconGenerator = new IconGenerator(context);

    	mImageView = new ImageView(context);
    	mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));	
		int padding = (int)context.getResources().getDimension(R.dimen.custom_profile_padding);
		mImageView.setPadding(padding, padding, padding, padding);

		this.imageData = imageData ;
    	this.markerOptions = markerOptions ;
    	String dateTitle = TimeHelper.formatDate(context, String.valueOf(imageData.getTimestamp())) ;
    	this.markerOptions.title(dateTitle) ;
    }
    
    @Override
	public void onPrepareLoad(Drawable arg0) {
		// TODO Auto-generated method stub
	}

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, LoadedFrom from) 
    {
    	Log.i("bitmap loaded", "bitmap loaded");
    	
    	mImageView.setImageBitmap(bitmap);
		mIconGenerator.setContentView(mImageView);
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon()));
		
		//renderer.getMarker(imageData).hideInfoWindow();
		//renderer.getMarker(imageData).showInfoWindow();
    }

	@Override
	public void onBitmapFailed(Drawable arg0) {
		Log.i("bitmap loaded", "onBitmapFailed !!!!");		
	}
}