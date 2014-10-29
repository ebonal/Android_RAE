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
import com.rae.placetobe.util.SharedPreferencesUtil;

public class PublicationActivity extends Activity
{
	private static final String TAG = PublicationActivity.class.getSimpleName();
	
	@InjectView(R.id.imageViewPhoto)  ImageView mImageView;
	@InjectView(R.id.editTextComment) EditText  mCommentText;

	private Bitmap originalImageBitmap ;
	private Bitmap whiteAndBlackImageBitmap = null ; // Lazy initialization
	
	private String mCurrentPhotoPath; 
	private Boolean blackAndWhite = Boolean.FALSE ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
       	Log.d(TAG, "onCreate()") ;
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_publication);		
		ButterKnife.inject(this);		
		
		setTitle("");
		mCurrentPhotoPath = SharedPreferencesUtil.restoreFilePath(this) ;
		blackAndWhite     = SharedPreferencesUtil.restoreBlackAndWhite(this); 
	}
	
	
	// onSaveInstanceState are not always called see : http://developer.android.com/reference/android/app/Activity.html
	// so each time the activity is paused, the data are NOW saved in the shared preferences.
	@Override
	protected void onPause()
	{
       	Log.d(TAG, "onPause()") ;
		super.onPause();
		SharedPreferencesUtil.backupBlackAndWhite(this, blackAndWhite);
		// Note : mCurrentPhotoPath is saved on MainActivity
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
		getMenuInflater().inflate(R.menu.publication_menu, menu);
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

		// onSub
		
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
