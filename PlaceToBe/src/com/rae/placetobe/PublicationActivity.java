package com.rae.placetobe;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
       	Log.d(TAG, "onCreate()") ;
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_publication);		
		ButterKnife.inject(this);		
		
		setTitle("");
	}
	
	// NOTE : SAVING ACTIVITY STATE
	// onSaveInstanceState are not always called see : http://developer.android.com/reference/android/app/Activity.html
	// so each time the activity is paused, the data are saved in the shared preferences.
	// @Override
	// protected void onPause()
	// {
	//		super.onPause();
	//
	//      USE shared preferences to save the data here
	// }

	/*
	 * The image is loaded here because the size of the imageView is undefined in the onCreate() method.
	 *  mImageView.getWidth() == mImageView.getHeight() == 0 ;
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		try 
		{
			String mCurrentPhotoPath = SharedPreferencesUtil.restoreFilePath(this) ;
			originalImageBitmap = ImageUtil.decodeSampledBitmapFromResource(mCurrentPhotoPath, mImageView.getWidth(),  mImageView.getHeight()) ;
           	Log.d(TAG,"imageBitmap : " + originalImageBitmap) ;

           	setViewBitmap(SharedPreferencesUtil.restoreBlackAndWhite(this));
		}
		catch(Exception e) {
			Log.e(TAG, "onWindowFocusChanged", e) ;
		}
		
		super.onWindowFocusChanged(hasFocus);
	}
	
	
	private void setViewBitmap(Boolean bw) 
	{
		if(bw) {
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
			Boolean blackAndWhite = SharedPreferencesUtil.restoreBlackAndWhite(this) ;
			blackAndWhite = !blackAndWhite ;
			SharedPreferencesUtil.backupBlackAndWhite(this, blackAndWhite);

	        Observable.just(blackAndWhite)
	        .subscribeOn(Schedulers.newThread())
	        .observeOn(AndroidSchedulers.mainThread())
	        .subscribe(new Action1<Boolean>() {
		        @Override
		        public void call(Boolean blackAndWhite) {
		        	setViewBitmap(blackAndWhite);
		        }
		    });
		}

		if(id==R.id.action_commit)  {
			String mCurrentPhotoPath = SharedPreferencesUtil.restoreFilePath(this) ;
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
