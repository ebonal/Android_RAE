package com.rae.placetobe.location.extra;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.rae.placetobe.R;
import com.rae.placetobe.model.ImageData;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

public class ImageRenderer extends DefaultClusterRenderer<ImageData>
{
	private final IconGenerator mIconGenerator ;
	private final ImageView mImageView;
	private final int mDimension;
	
	private final Context context ;

	public ImageRenderer(Activity activity , GoogleMap map, ClusterManager<ImageData> mClusterManager)
	{
		super(activity, map, mClusterManager);

		this.context = activity ;
		
		mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
		

		// see : https://github.com/square/picasso/issues/308
		mImageView = new ImageView(activity);
		mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));	
		int padding = (int) context.getResources().getDimension(R.dimen.custom_profile_padding);
		mImageView.setPadding(padding, padding, padding, padding);

		mIconGenerator = new IconGenerator(context);
		mIconGenerator.setContentView(mImageView);
	}
	
	@Override
	protected void onBeforeClusterItemRendered(final ImageData imageData, MarkerOptions markerOptions)
	{
		// Draw a single person.
		// Set the info window to show their name.
		//mImageView.setImageResource(myImage.profilePhoto);		
		// see : https://github.com/square/picasso/issues/308
		
		String text = ""+imageData.getTimestamp() ;
		
		// TODO Auto-generated method stub
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon())).title(text);
		Picasso.with(context).load(new File(imageData.getFilePath())).resize(64, 64).centerCrop().into(mImageView, new Callback() {
			@Override
			public void onError() {
			}

			@Override
			public void onSuccess()  {
			}
		}) ;
/*
		Picasso.with(context).load(new File(imageData.getFilePath())).resize(64, 64).centerCrop().into(mImageView, 
			new Callback() {
				@Override
				public void onError() {
					// TODO Auto-generated method stub
				}

				@Override
				public void onSuccess()  {
					
				}
		}) ;*/
	}

	@Override
	protected boolean shouldRenderAsCluster(Cluster<ImageData> cluster) {
		// Always render clusters.
		return cluster.getSize() > 1;
	}
}