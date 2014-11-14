package com.rae.placetobe.location.extra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

// see : https://github.com/square/picasso/issues/308

public class ImageViewForPicasso extends ImageView implements Target
{
	public ImageViewForPicasso(Context context) {
		super(context);
	}

	@Override
	public void onBitmapFailed(Drawable drawable) {
//	    Drawable d = getResources().getDrawable(R.drawable.nofavicon);
	    // textView.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
	    // textView.setCompoundDrawablePadding(5); 
	}

	@Override
	public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
	    // Drawable d = new BitmapDrawable(getContext().getResources(), bitmap);
	    this.setImageBitmap(bitmap);
	    this.refreshDrawableState();
        // textView[picassoCounter].setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
        // textView[picassoCounter].setCompoundDrawablePadding(5);    
	}

	@Override
	public void onPrepareLoad(Drawable drawable) {
		// TODO Auto-generated method stub		
	}

}
