package com.rae.placetobe.history;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.util.ImageData;

public class HistoryActivity extends AbstractDrawerActivity
{
	private GridView gridview;

	@Override
	protected int getContentViewId() {
		return R.layout.activity_history;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); // Will inflate the layout using getContentViewId()
		
		// get my gridview in my layout
		gridview = (GridView) findViewById(R.id.gridview);
		// set custom adapter to my gridview
	    gridview.setAdapter(new ImageAdapter(this,R.layout.history_gridview_item));
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
	    public ImageAdapter(Context context, int textViewResourceId) {
	        inflater = LayoutInflater.from(context);
			// get my list of imageData in sharedPref
	        imageDataList = ImageData.getAllImageDatas(getBaseContext()) ;
	    }
	    
	    // Object to stock gridViewItem data
	    private class GridViewItem {
	    	SquareImageView picture;
	    	TextView comment;
			ImageData imageData;
			Bitmap bitmap;
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
	    	
	    	GridViewItem gridViewItem = null;
	        
	        // if it's not recycled, initialize some attributes
	        if (convertView == null) {  
	        	convertView = inflater.inflate(R.layout.history_gridview_item, parent, false);
	        	
	        	gridViewItem = new GridViewItem();
	        	gridViewItem.picture = (SquareImageView)convertView.findViewById(R.id.picture);
	        	gridViewItem.comment = (TextView)convertView.findViewById(R.id.text);
	        	convertView.setTag(gridViewItem);
	        }
	        
	        // get tag of my view
	        gridViewItem = (GridViewItem)convertView.getTag();
	        
	        // get the imageData
	        gridViewItem.imageData = imageDataList.get(position);
	        
	        // Init the bitmap picture and the comment text
	        gridViewItem.bitmap = null;
	        // gridViewItem.picture.setImageBitmap(gridViewItem.bitmap);
	        gridViewItem.comment.setText("");
	        
	        // Async task to load my picture and comment
	        new DownloadAsyncTask().execute(gridViewItem);

	        return convertView;
	    }
	    
	    private class DownloadAsyncTask extends AsyncTask<GridViewItem, Void, GridViewItem> 
	    {
			@Override
			protected GridViewItem doInBackground(GridViewItem... params) {
				GridViewItem gridViewItem = params[0];
				// Create bitmap option to load light picture
				BitmapFactory.Options optionsBitmapFactory = new BitmapFactory.Options();
				optionsBitmapFactory.inSampleSize = 8;
				// Create bitmap with the file path item
				gridViewItem.bitmap = BitmapFactory.decodeFile(gridViewItem.imageData.getFilePath(), optionsBitmapFactory);
				
				return gridViewItem;
			}
			
			@Override
			protected void onPostExecute(GridViewItem result) {
				// TODO Auto-generated method stub
				if (result.bitmap == null) {
					// result.picture.setImageResource(R.drawable.ic_place_to_be);
					// result.comment.setText("");
				} else {
					// Set my result to my view
					result.picture.setImageBitmap(result.bitmap);
					// result.picture.setSquareDimmension();
					result.comment.setText(result.imageData.getComment());
				}
			}
		}
	}
}
