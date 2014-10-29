package com.rae.placetobe;

import rx.Observable;
import rx.functions.Action1;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.util.ImageData;
import com.rae.placetobe.util.ImageUtil;

public class PublicationActivity extends Activity
{
	private static final String TAG = PublicationActivity.class.getSimpleName();
	
	@InjectView(R.id.imageViewPhoto)  ImageView mImageView;
	@InjectView(R.id.editTextComment) EditText  mCommentText;

	private Bitmap originalImageBitmap ;
	private Bitmap whiteAndBlackImageBitmap = null ; // Lazy initialization
	
	private String mCurrentPhotoPath; 
	
	final static private String BUNDLE_BLACK_AND_WHITE = "BUNDLE_BW" ;
	final static private String BUNDLE_FILE_PATH       = "BUNDLE_FILE_PATH" ;
	
	private Boolean blackAndWhite = Boolean.FALSE ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_publication);		
		ButterKnife.inject(this);		
		
		setTitle("");
		if(savedInstanceState!=null && savedInstanceState.containsKey(BUNDLE_BLACK_AND_WHITE))
			blackAndWhite = savedInstanceState.getBoolean(BUNDLE_BLACK_AND_WHITE) ;
		
		if(savedInstanceState!=null && savedInstanceState.containsKey(BUNDLE_FILE_PATH))
			mCurrentPhotoPath = savedInstanceState.getString(BUNDLE_FILE_PATH) ;
		else
			mCurrentPhotoPath = getIntent().getStringExtra(MainActivity.EXTRA_FILE_PATH);
	}
	
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);	
		outState.putBoolean(BUNDLE_BLACK_AND_WHITE, blackAndWhite);
		outState.putString (BUNDLE_FILE_PATH      , mCurrentPhotoPath);
	}


	/*
	 * The image is loaded here because the size of the imageView is undefined in the onCreate() method.
	 *  mImageView.getWidth() == mImageView.getHeight() == 0 ;
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		try 
		{
        	// BOB : The original setPic() method in the official documentation is buggy, 
			// openGL texture size on older phone MUST be a power of 2.
        	// original setPic() : see  @ http://developer.android.com/training/camera/photobasics.html
            // int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        	
			// The correct version is from here :
        	// @see http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
        	originalImageBitmap = ImageUtil.decodeSampledBitmapFromResource(mCurrentPhotoPath, mImageView.getWidth(),  mImageView.getHeight()) ;
           	Log.d(TAG,"imageBitmap : " + originalImageBitmap) ;

           	setViewBitmap(blackAndWhite);
           	
           	if(blackAndWhite) {
           		if(whiteAndBlackImageBitmap==null)	whiteAndBlackImageBitmap = ImageUtil.applyBlackAndWithFilter(originalImageBitmap) ;
           		mImageView.setImageBitmap(whiteAndBlackImageBitmap);
           	}
           	else mImageView.setImageBitmap(originalImageBitmap);
		}
		catch(Exception e) {
			Log.e(TAG, "onWindowFocusChanged", e) ;
		}
		
		super.onWindowFocusChanged(hasFocus);
	}
	
	private void setViewBitmap(boolean bAndW) 
	{
		if(bAndW) {
			// Toggle to black and white
			if(whiteAndBlackImageBitmap==null) 
				whiteAndBlackImageBitmap = ImageUtil.applyBlackAndWithFilter(originalImageBitmap) ;
			
			mImageView.setImageBitmap(whiteAndBlackImageBitmap);			
			return ;
		}
		
		mImageView.setImageBitmap(originalImageBitmap);					
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.publication_activty, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if(id==R.id.action_toggle) 
		{
			blackAndWhite = !blackAndWhite ;
			
			Boolean[] filters = { blackAndWhite } ;
		    Observable.from(filters).subscribe(new Action1<Boolean>() {
		        @Override
		        public void call(Boolean bw) {
		        	setViewBitmap(bw);
		        }
		    });
		}
		
		if(id==R.id.action_commit)  {
			ImageData.addPhoto(this, mCurrentPhotoPath, mCommentText.getText().toString()) ;
			// Uncomment this line to add the picture to the phone gallery
	    	// GalleryUtil.addPic(this, mCurrentPhotoPath);			
			finish();
			return true ;
		}
		
		if(id==R.id.action_cancel) {
			finish();
			return true ;
		}
				
		return super.onOptionsItemSelected(item);
	}
}
