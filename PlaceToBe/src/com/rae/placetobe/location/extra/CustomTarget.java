package com.rae.placetobe.location.extra;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

// this class is within the Activity class
public class CustomTarget implements Target
{
    public ImageView imageView;

    public CustomTarget(ImageView imageView)
    {
        this.imageView = imageView;
    }

    @Override
	public void onPrepareLoad(Drawable arg0) {
		// TODO Auto-generated method stub
	}

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, LoadedFrom from) {
        Log.i("bitmap loaded", "bitmap loaded");
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
    }

	@Override
	public void onBitmapFailed(Drawable arg0) {
		// TODO Auto-generated method stub
		
	}

}