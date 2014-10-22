package com.rae.placetobe;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rae.placetobe.util.ImageUtil;

public class MainActivity extends Activity
{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	public final static String EXTRA_MESSAGE = TAG+".MESSAGE";

	private String mCurrentPhotoPath; 
    
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showPhoto(View view)
	{
		dispatchTakePictureIntent();
	}

	static final int REQUEST_TAKE_PHOTO = 1;

	private void dispatchTakePictureIntent()
	{
	    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) == null) return ;
	   
        // Create the File where the photo should go

        try
        {
        	File mCurrentPhotoFile = ImageUtil.createImageFile();
        	
        	mCurrentPhotoPath = ImageUtil.getPath(mCurrentPhotoFile) ;
        	
        	Log.d(TAG, "mCurrentPhotoPath : " + mCurrentPhotoPath) ;
        	        	
            if(mCurrentPhotoFile != null) {
            	Uri uri = Uri.fromFile(new File(mCurrentPhotoPath)) ;
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
        catch (Exception ex) {
            // Error occurred while creating the File
            // ...
        }
 	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
    	Log.d(TAG,"onActivityResult") ;
		if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
		{
	    	Intent intent = new Intent(this, PublicationActivty.class);
	    	intent.putExtra(EXTRA_MESSAGE, mCurrentPhotoPath);
	        startActivity(intent);
		}
	}	
	
	
	
	
	protected void onActivityResultXXX(int requestCode, int resultCode, Intent data)
	{
    	Log.d(TAG,"onActivityResult") ;
    	
		/*
		Intent intent = new Intent(this, PublicationActivity.class);
		intent.putExtra(EXTRA_MESSAGE, mCurrentPhotoPath);
		*/
		if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
		{

			
			
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
