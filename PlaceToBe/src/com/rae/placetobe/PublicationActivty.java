package com.rae.placetobe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.rae.placetobe.util.ImageData;
import com.rae.placetobe.util.ImageUtil;

public class PublicationActivty extends Activity
{
	private static final String TAG = PublicationActivty.class.getSimpleName();
	
	private ImageView mImageView;
	private EditText  mCommentText;

	private String mCurrentPhotoPath; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publication_activty);		

		mImageView   = (ImageView) findViewById(R.id.imageViewPhoto);
		mCommentText = (EditText) findViewById(R.id.editTextComment);
		
		Intent intent = getIntent();
		mCurrentPhotoPath = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
//    	GalleryUtil.addPic(this, mCurrentPhotoPath);

		
		// Bundle extras = data.getExtras();
		// Bitmap imageBitmap = (Bitmap) extras.get("data");
		try 
		{
        	Log.d(TAG,"path : " + mCurrentPhotoPath) ;

			Bitmap imageBitmap = ImageUtil.decodeSampledBitmapFromResource(mCurrentPhotoPath, 640,  480) ;
        	Log.d(TAG,"imageBitmap : " + imageBitmap) ;
			
		     // Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(mCurrentPhotoFile));
			mImageView.setImageBitmap(imageBitmap);	
		}
		catch(Exception e) {
			
		}

		
		
		
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
		
		if(id==R.id.action_commit) 
		{
			ImageData.addPhoto(getPreferences(Context.MODE_PRIVATE), mCurrentPhotoPath, mCommentText.getText().toString()) ;
			finish();
			return true ;
		}
		
		if(id==R.id.action_cancel) 
		{
			finish();
			return true ;
		}
		
		// TODO
		if (id == R.id.action_settings)
		{
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
