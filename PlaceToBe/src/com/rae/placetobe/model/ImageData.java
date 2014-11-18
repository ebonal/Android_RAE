package com.rae.placetobe.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ImageData implements ClusterItem, Parcelable 
{
	@Override
	public boolean equals(Object o)
	{
		if(o==this) return true;
		if(o==null) return false ;
		if(!(o instanceof ImageData)) return false ;
		
		ImageData ido = (ImageData)o ; 
		if(filePath==null || ido.filePath==null) return false ;
		if(ido.filePath.equals(filePath)) return true ;		
		return false ;
	}

	@Override
	public int hashCode() {
		if(filePath==null) return super.hashCode();
		return filePath.hashCode() ;
	}

	private int    id  ;
	private String filePath  ;
	private String comment   ;
	private String timestamp ;
    private LatLng mPosition ;
	
	public int getId() {
		return id;
	}
	public String getFilePath() {
		return filePath;
	}
	public String getComment() {
		return comment;
	}
	public String getTimestamp() {
		return timestamp;
	}

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
    
	public ImageData() {} // For parcelable
	
	public ImageData(int id, String filePath, String comment, String timestamp, LatLng position)
	{
		this.id        = id ;
		this.filePath  = filePath ;
		this.comment   = comment ; 
		this.timestamp = timestamp ;
        mPosition = position;
	}
	
	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static final Parcelable.Creator<ImageData> CREATOR = new Creator<ImageData>() {  
		 public ImageData createFromParcel(Parcel source) 
		 {  
			 ImageData im = new ImageData();  
			 im.id = source.readInt() ;
			 im.filePath = source.readString() ;
			 im.comment  = source.readString() ;
		     im.timestamp = source.readString();  
//		     im.mPosition = source.readParcelable(null);  
		     return im;  
		 }

		@Override
		public ImageData[] newArray(int size) {
		     return new ImageData[size];  
		} 
	} ;
	
	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		 parcel.writeInt(id);
		 parcel.writeString(filePath);  
		 parcel.writeString(comment);  
		 parcel.writeString(timestamp);  
//		 parcel.writeParcelable(mPosition, PARCELABLE_WRITE_RETURN_VALUE);  		
	}

}
