package com.rae.placetobe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.rae.placetobe.util.CameraUtil;
import com.rae.placetobe.util.ImageData;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity
{
	private final static String TAG = MainActivity.class.getSimpleName();
	public  final static String EXTRA_FILE_PATH = MainActivity.class.getPackage().getName()+".EXTRA_FILE_PATH";

	public final static String PREFERENCE_FILE_NAME = "ImageData" ;
	
	private String mCurrentPhotoPath; 
    

	/** Variable pour le Menu tiroir */
	// Vue pour mon menu tiroir
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	// Variable pour afficher mon menu
	private String[] mMainNavTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Initialisation des valeures pour le menu tiroir
		mMainNavTitles = getResources().getStringArray(R.array.main_nav_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // Définition de mon adapter pour ma list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
        		R.layout.drawer_list_item, mMainNavTitles));
        // Click listener sur ma list list view
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_menu_hamburger_white,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            	invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	invalidateOptionsMenu();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
	@Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        return super.onPrepareOptionsMenu(menu);
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
    	
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

	public void addPhoto(View view) {
		mCurrentPhotoPath = CameraUtil.startImageCaptureActivity(this) ;
	}
	
	public void addVideo(View view)
	{
		CameraUtil.startVideoCaptureActivity(this) ;
	}
	
	public void showGallery(View view)
	{
		ImageData.dump(getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)) ;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
    	Log.d(TAG,"onActivityResult") ;
		super.onActivityResult(requestCode, resultCode, data);
		
    	if(requestCode == CameraUtil.REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
		{
	    	Intent intent = new Intent(this, PublicationActivty.class);
	    	intent.putExtra(EXTRA_FILE_PATH, mCurrentPhotoPath);
	        startActivity(intent);
	        return ;
		}

    	if(requestCode == CameraUtil.REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK)
		{
    		Toast.makeText(this, "YEP !", Toast.LENGTH_LONG).show() ;
    		return ;
		}
	}

	
	
	
	/** Menu tiroir */
	
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	
    public void launchNewActivity(View view,String activity) {
	   	String label = "com.rae.placetobe." + activity;
	   	
	   	Class<?> labelClass = null;
	   	if(label != null) {
	   	    try {
	   	    	labelClass = Class.forName(label);
	   	    } catch (ClassNotFoundException e) {
	   	        // TODO Auto-generated catch block
	   	        e.printStackTrace();
	   	    }
	   	}
	   	// on instancie un nouvel Intent
	   	Intent intent = new Intent(this, labelClass);
	   	// on lance l'activit?
	   	startActivity(intent);
   }
	
	// Swaps fragments in the main content view
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	    	switch(position){
				case 0 : 
					addPhoto(view);
					break;
				case 3 : 
					 launchNewActivity(view,"HistoryActivity");
					break;
				default: 
					break;
			}
	    }
	}

}
