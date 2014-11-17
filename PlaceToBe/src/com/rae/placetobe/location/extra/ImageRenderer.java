package com.rae.placetobe.location.extra;

import java.io.File;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.rae.placetobe.model.ImageData;
import com.squareup.picasso.Picasso;

public class ImageRenderer extends DefaultClusterRenderer<ImageData>
{
//	private final ImageView mImageView;
//	private final IconGenerator mIconGenerator ;
//	private final int mDimension;
	private final Context context ;
	
	private CustomTarget2 customTarget ;
	

	public ImageRenderer(Activity activity , GoogleMap map, ClusterManager<ImageData> mClusterManager)
	{
		super(activity, map, mClusterManager);

		this.context = activity ;
		/*
		mDimension = (int) context.getResources().getDimension(R.dimen.custom_profile_image);
		
		ImageView mImageView = new ImageView(activity);
		mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));	
		int padding = (int)context.getResources().getDimension(R.dimen.custom_profile_padding);
		mImageView.setPadding(padding, padding, padding, padding);

		mImageView.setImageResource(R.drawable.ic_thumb_img); // default
		*/
		
//		mIconGenerator = new IconGenerator(context);
//		mIconGenerator.setContentView(mImageView);
	}
	
	@Override
	protected void onBeforeClusterItemRendered(final ImageData imageData, MarkerOptions markerOptions)
	{
		/*
		String dateTitle = TimeHelper.formatDate(context, String.valueOf(imageData.getTimestamp())) ;
		markerOptions.icon(BitmapDescriptorFactory.fromBitmap(mIconGenerator.makeIcon()));
		markerOptions.title(dateTitle) ;
*/
		customTarget = new CustomTarget2(context, this, imageData, markerOptions) ;	
		Picasso.with(context).load(new File(imageData.getFilePath())).resize(64, 64).centerCrop().into(customTarget) ;
	}

	@Override
	protected boolean shouldRenderAsCluster(Cluster<ImageData> cluster) {
		// Always render clusters.
		return cluster.getSize() > 1;
	}
}