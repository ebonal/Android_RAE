package com.rae.placetobe.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.rae.placetobe.R;

public class ImageData implements ClusterItem 
{
	final private int    id  ;
	final private String filePath  ;
	final private String comment   ;
	final private long   timestamp ;

    public final int profilePhoto;
    private final LatLng mPosition;
	
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
	public int getPhotoPictureId() {
		return profilePhoto ;
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
		
        profilePhoto = R.drawable.turtle; // pictureResource;
        mPosition = position;
	}

}
