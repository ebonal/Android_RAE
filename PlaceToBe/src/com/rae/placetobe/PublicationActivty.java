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
		mCommentText = (EditText)  findViewById(R.id.editTextComment);
		
		Intent intent = getIntent();
		mCurrentPhotoPath = intent.getStringExtra(MainActivity.EXTRA_FILE_PATH);		
	}

	
	/*
	 * Le chargement de la photo se fait ici, car la taille de mImageView n'est pas encore defini dans le onCreate()
	 *  mImageView.getWidth() == mImageView.getHeight() == 0 ;
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		try 
		{
        	Log.d(TAG,"path : " + mCurrentPhotoPath) ;
        	
        	// !! BOB : La methode setPic de la doc officiel semble un peu erronée car elle ne produit pas forcement 
        	// un scaleFactor qui soit un multiple de 2.
        	// Pour rappel dans setPic() @ http://developer.android.com/training/camera/photobasics.html
            // int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        	
        	// @see http://developer.android.com/training/displaying-bitmaps/load-bitmap.html
			Bitmap imageBitmap = ImageUtil.decodeSampledBitmapFromResource(mCurrentPhotoPath, mImageView.getWidth(),  mImageView.getHeight()) ;
        	Log.d(TAG,"imageBitmap : " + imageBitmap) ;
			
			mImageView.setImageBitmap(imageBitmap);	
		}
		catch(Exception e) {
			
		}
		
		super.onWindowFocusChanged(hasFocus);
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
			
			// De-commenter cette ligne pour ajouter la photo dans la galerie du telephone
	    	//GalleryUtil.addPic(this, mCurrentPhotoPath);

			
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
