package com.rae.placetobe;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rae.placetobe.util.ImageData;

public class HistoryActivity extends Activity
{
	// create a gridview
	private GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		// get my gridview in my layout
		gridview = (GridView) findViewById(R.id.gridview);
		// set custom adapter to my gridview
	    gridview.setAdapter(new ImageAdapter(this));
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) 
	{
	    super.onConfigurationChanged(newConfig);

	    // Checks the orientation of the screen and change the num of columns 
	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	gridview.setNumColumns(3);
	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	gridview.setNumColumns(2);
	    }
	}
	
	
	// Custom adapter to implements item in my gridview
	public class ImageAdapter extends BaseAdapter 
	{
	    final private SparseArray<ImageData> imageDataList ;
	    final private LayoutInflater inflater ;
	    
	    // Init my inflater for inflate my custom item view and my list of imageData
	    public ImageAdapter(Context context) {
	        inflater = LayoutInflater.from(context);
	        imageDataList = ImageData.getAllImageDatas(context) ;
	    }
	    
	    @Override
	    public boolean areAllItemsEnabled() {
	        return false;
	    }

	    @Override
	    public boolean isEnabled(int position) {
	        return false;
	    }

	    public int getCount() {
	        return imageDataList.size();
	    }

	    public Object getItem(int position) {
	    	return imageDataList.get(position);
	    }

	    public long getItemId(int position) {
	    	return imageDataList.get(position).getId();
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) 
	    {
	        View gridViewItem = convertView;
	        
	        // if it's not recycled, initialize some attributes
	        if (gridViewItem == null) {  
	        	gridViewItem = inflater.inflate(R.layout.history_gridview_item, parent, false);
	        	gridViewItem.setTag(R.id.picture, gridViewItem.findViewById(R.id.picture));
	        	gridViewItem.setTag(R.id.text, gridViewItem.findViewById(R.id.text));
	        } 
	        
	        // Get the view to implement
	        ImageView picture = (ImageView)gridViewItem.getTag(R.id.picture);
	        TextView comment  = (TextView)gridViewItem.getTag(R.id.text);
	        
	        BitmapFactory.Options optionsBitmapFactory = new BitmapFactory.Options();
	        optionsBitmapFactory.inSampleSize = 8;
	        // Create bitmap with the file path item
            Bitmap myBitmap = BitmapFactory.decodeFile(imageDataList.get(position).getFilePath(), optionsBitmapFactory);
                       
            // Set my value to the view
            picture.setImageBitmap(myBitmap);
            comment.setText(imageDataList.get(position).getComment());

	        return gridViewItem;
	    }
	}
}
