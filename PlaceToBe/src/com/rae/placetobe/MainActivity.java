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

import com.rae.placetobe.util.CameraUtil;
import com.rae.placetobe.util.ImageUtil;

public class MainActivity extends Activity
{
	private final static String TAG = MainActivity.class.getSimpleName();
	public  final static String EXTRA_FILE_PATH = MainActivity.class.getPackage().getName()+".EXTRA_FILE_PATH";

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

	public void addPhoto(View view)
	{
		mCurrentPhotoPath = CameraUtil.startCaptureActivity(this) ;
	}
	
	public void showGallery(View view)
	{
		mCurrentPhotoPath = CameraUtil.startCaptureActivity(this) ;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
    	Log.d(TAG,"onActivityResult") ;
		
    	if(requestCode == CameraUtil.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
		{
	    	Intent intent = new Intent(this, PublicationActivty.class);
	    	intent.putExtra(EXTRA_FILE_PATH, mCurrentPhotoPath);
	        startActivity(intent);
	        return ;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

}
