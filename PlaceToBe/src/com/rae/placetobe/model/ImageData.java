package com.rae.placetobe.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ImageData implements ClusterItem 
{
	final private int    id  ;
	final private String filePath  ;
	final private String comment   ;
	final private long   timestamp ;
    final private LatLng mPosition ;
	
	public int getId() {
		return id;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getComment() {
		return comment;
	}
	public long getTimestamp() {
		return timestamp;
	}

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
    
	// private constructor ensure the use of the #addPhoto factory method.
	public ImageData(int id, String filePath, String comment, long timestamp, LatLng position/*, String name, int pictureResource*/)
	{
		this.id        = id ;
		this.filePath  = filePath ;
		this.comment   = comment ; 
		this.timestamp = timestamp ;
        mPosition = position;
	}

}
