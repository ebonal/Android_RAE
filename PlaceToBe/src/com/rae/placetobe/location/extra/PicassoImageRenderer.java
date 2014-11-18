package com.rae.placetobe.location.extra;

import java.io.File;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.rae.placetobe.R;
import com.rae.placetobe.model.ImageData;
import com.rae.placetobe.util.TimeHelper;
import com.squareup.picasso.Picasso;

public class PicassoImageRenderer extends DefaultClusterRenderer<ImageData>
{
	private final Context context ;	
	private final ClusterManager<ImageData> clusterManager ;
	
	private PicassoTarget customTarget ;
	
	public PicassoImageRenderer(Activity activity , GoogleMap map, ClusterManager<ImageData> clusterManager)
	{
		super(activity, map, clusterManager);
		this.context = activity ;
		this.clusterManager = clusterManager ;
	}
	
	@Override
	protected void onBeforeClusterItemRendered(final ImageData imageData, MarkerOptions markerOptions)
	{
    	String dateTitle = TimeHelper.formatDate(context, String.valueOf(imageData.getTimestamp())) ;
	  	markerOptions.title(dateTitle) ;
	  	markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_thumb_img)) ;
	  	
	    customTarget = new PicassoTarget(context, this, clusterManager, imageData);
		Picasso.with(context).load(new File(imageData.getFilePath())).resize(64, 64).centerCrop().into(customTarget) ;
	}

	@Override
	protected boolean shouldRenderAsCluster(Cluster<ImageData> cluster) {
		// Always render clusters.
		return cluster.getSize() > 1;
	}
}