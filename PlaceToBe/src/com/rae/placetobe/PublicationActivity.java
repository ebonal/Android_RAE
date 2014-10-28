package com.rae.placetobe;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.rae.placetobe.util.ImageData;
import com.rae.placetobe.util.ImageUtil;

public class PublicationActivity extends Activity
{
	private static final String TAG = PublicationActivity.class.getSimpleName();
	
	private ImageView mImageView;
	private EditText  mCommentText;

	private Bitmap originalImageBitmap ;
	private Bitmap whiteAndBlackImageBitmap = null ; // Lazy initialization
	private Bitmap currentBitmap ;
	
	private String mCurrentPhotoPath; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_publication);		
		setTitle("");
		
		mImageView   = (ImageView) findViewById(R.id.imageViewPhoto);
		mCommentText = (EditText)  findViewById(R.id.editTextComment);
		
		mCurrentPhotoPath = getIntent().getStringExtra(MainActivity.EXTRA_FILE_PATH);		
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

           	applyCurrentBitmap(originalImageBitmap);
 			
			mImageView.setImageBitmap(originalImageBitmap);	
		}
		catch(Exception e) {
			Log.e(TAG, "onWindowFocusChanged", e) ;
		}
		
		super.onWindowFocusChanged(hasFocus);
	}

	private void toggleFilter() 
	{
		if(currentBitmap==originalImageBitmap) {
			// Toggle to black and white
			if(whiteAndBlackImageBitmap==null) 
				whiteAndBlackImageBitmap = ImageUtil.applyBlackAndWithFilter(originalImageBitmap) ;
			applyCurrentBitmap(whiteAndBlackImageBitmap);
			return ;
		}
		
		applyCurrentBitmap(originalImageBitmap);
	}

	private void applyCurrentBitmap(Bitmap newCurrentBitmap) 
	{
		currentBitmap = newCurrentBitmap ;
		mImageView.setImageBitmap(currentBitmap);			
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
		
		if(id==R.id.action_toggle) {
			toggleFilter();
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
