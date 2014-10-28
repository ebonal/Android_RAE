package com.rae.placetobe;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rae.placetobe.util.ImageData;

public class HistoryActivity extends Activity {
	
	// create a gridview
	private GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		// get my gridview in my layout
		gridview = (GridView) findViewById(R.id.gridview);
		// set custom adapter to my gridview
	    gridview.setAdapter(new ImageAdapter(this));
	}
	
	
	// Custom adapter to implements item in my gridview
	public class ImageAdapter extends BaseAdapter {
	    private ArrayList<ImageData> imageDataList = new ArrayList<ImageData>();
	    private LayoutInflater inflater;

	    public ImageAdapter(Context context) {
	        inflater = LayoutInflater.from(context);
	        SharedPreferences sharePref = context.getSharedPreferences(MainActivity.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE) ;
	        imageDataList.addAll(ImageData.getAllImageDatas(sharePref)) ;
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
	        ImageView picture;
	        TextView comment;
	        
	        // if it's not recycled, initialize some attributes
	        if (gridViewItem == null) 
	        {  
	        	gridViewItem = inflater.inflate(R.layout.history_gridview_item, parent, false);
	        	gridViewItem.setTag(R.id.picture, gridViewItem.findViewById(R.id.picture));
	        	gridViewItem.setTag(R.id.text, gridViewItem.findViewById(R.id.text));
	        } 
	        
	        // get the view to implement
	        picture = (ImageView)gridViewItem.getTag(R.id.picture);
	        comment = (TextView)gridViewItem.getTag(R.id.text);
	        
	        // create bitmap with the file path item
	        File imgFile = new  File(imageDataList.get(position).getFilePath());
	        BitmapFactory.Options optionsBitmapFactory = new BitmapFactory.Options();
	        optionsBitmapFactory.inSampleSize = 8;
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),optionsBitmapFactory);
            
            // set my value to the view
            picture.setImageBitmap(myBitmap);
            comment.setText(imageDataList.get(position).getComment());

	        return gridViewItem;
	    }
	}
}
