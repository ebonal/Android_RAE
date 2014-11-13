package com.rae.placetobe.location.extra;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
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


public class ImageRenderer extends DefaultClusterRenderer<ImageData>
{
	private final IconGenerator mIconGenerator ;
	private final IconGenerator mClusterIconGenerator ;
	private final ImageView mImageView;
	private final ImageView mClusterImageView;
	private final int mDimension;
	
	private final Context context ;

	public ImageRenderer(Activity activity , GoogleMap map, ClusterManager<ImageData> mClusterManager)
	{
		super(activity, map, mClusterManager);

		this.context = activity ;
		
		mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
		
		View multiProfile = activity.getLayoutInflater().inflate(R.layout.multi_profile, null);
		mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

		mClusterIconGenerator = new IconGenerator(context);
		mClusterIconGenerator.setContentView(multiProfile);

		// see : https://github.com/square/picasso/issues/308
		mImageView = new ImageViewForPicasso(activity);
		mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));	
		int padding = (int) context.getResources().getDimension(R.dimen.custom_profile_padding);
		mImageView.setPadding(padding, padding, padding, padding);

		mIconGenerator = new IconGenerator(context);
		mIconGenerator.setContentView(mImageView);
	}

	@Override
	protected void onBeforeClusterItemRendered(ImageData imageData, MarkerOptions markerOptions)
	{
		// Draw a single person.
		// Set the info window to show their name.
		//mImageView.setImageResource(myImage.profilePhoto);

		// see : https://github.com/square/picasso/issues/308
		Picasso.with(context).load(new File(imageData.getFilePath())).resize(64, 64).centerCrop().into(mImageView);
		Bitmap icon = mIconGenerator.makeIcon();		
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(""+imageData.getTimestamp());
	}

	@Override
	protected void onBeforeClusterRendered(Cluster<ImageData> cluster, MarkerOptions markerOptions) 
	{
		// Draw multiple people.
		// Note: this method runs on the UI thread. Don't spend too much time in
		// here (like in this example).
/*
		List<Drawable> listDrawable = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
		int width  = mDimension;
		int height = mDimension;

		for (ImageData p : cluster.getItems())
		{
			// Draw 4 at most.
			if (listDrawable.size()==4) break;
			
			Drawable drawable = context.getResources().getDrawable(p.profilePhoto);		
			drawable.setBounds(0, 0, width, height);
			listDrawable.add(drawable);
		}
		
		MultiDrawable multiDrawable = new MultiDrawable(listDrawable);
		multiDrawable.setBounds(0, 0, width, height);
		mClusterImageView.setImageDrawable(multiDrawable);
*/		
		Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
	}

	@Override
	protected boolean shouldRenderAsCluster(Cluster<ImageData> cluster) {
		// Always render clusters.
		return cluster.getSize() > 1;
	}
}