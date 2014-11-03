package com.rae.placetobe.history;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
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
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.AbstractDrawerActivity;
import com.rae.placetobe.R;
import com.rae.placetobe.util.ImageData;

public class HistoryActivity extends AbstractDrawerActivity
{
	@InjectView(R.id.gridview) GridView gridview;
	
	@Override
	protected int getContentViewId() {
		return R.layout.activity_history;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); // Will inflate the layout using getContentViewId()
		
		ButterKnife.inject(this);
		// set custom adapter to my gridview
		gridview.setAdapter(new GridViewAdapter(this,R.layout.history_gridview_item));
	}
	
	// Custom adapter to implements item in my gridview
	public class GridViewAdapter extends BaseAdapter
	{
		final private LayoutInflater inflater ;
	    final private SparseArray<ImageData> imageDataList ;
	    final private List<Bitmap> imageBitmapList;
	    
	    // Get layoutInflater to the context for inflate my custom item view
	    // Get the imageDataList in my sharedPref
	    // Init list of Bitmap and add a first item at null
	    public GridViewAdapter(Context context, int textViewResourceId) {
	        inflater = LayoutInflater.from(context);
			// get my list of imageData in sharedPref
	        imageDataList = ImageData.getAllImageDatas(getBaseContext());
	        imageBitmapList = new ArrayList<Bitmap>();
	        imageBitmapList.add(0,null);
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
	    public View getView(final int position, View convertView, ViewGroup parent) 
	    {
	    	final ImageView picture;
	    	final TextView comment;
	    	
	        // if it's not recycled, initialize some attributes
	        if (convertView == null) {  
	        	convertView = inflater.inflate(R.layout.history_gridview_item, parent, false);
	        	convertView.setTag(R.id.picture, convertView.findViewById(R.id.picture));
	        	convertView.setTag(R.id.text, convertView.findViewById(R.id.text));
	        }
	        
	        // Get my view
	        picture = (ImageView)convertView.getTag(R.id.picture);
        	comment = (TextView)convertView.getTag(R.id.text);
        	
        	// if my bitmap is null at this position
        	// Getting my bitmap with observable Async
	        if(imageBitmapList.get(position) == null){
	        	imageBitmapList.add(position+1,null);
	        	ImageData imageData = imageDataList.get(position);
	        	comment.setText(imageData.getComment());
	        	picture.setImageResource(R.drawable.ic_thumb_img);
	        	Observable.just(imageData)
					.map(new Func1<ImageData, String>(){
			    		@Override
			    		public String call(ImageData imageData){
							return imageData.getFilePath();
			    		}
			    	})
			    	.map(new Func1<String, Bitmap>(){
			    		@Override
			    		public Bitmap call(String filePath){
			    			BitmapFactory.Options optionsBitmapFactory = new BitmapFactory.Options();
							optionsBitmapFactory.inSampleSize = 8;
							Bitmap bitmap = BitmapFactory.decodeFile(filePath, optionsBitmapFactory);
							return bitmap;
			    		}
			    	})
			    	.subscribeOn(Schedulers.newThread())
			    	.observeOn(AndroidSchedulers.mainThread())
			    	.subscribe(new Action1<Bitmap>() {
						@Override
						public void call(Bitmap bitmap) {
							imageBitmapList.set(position,bitmap);
							picture.setImageBitmap(bitmap);
						}
					});
	        } else {
	        	comment.setText(imageDataList.get(position).getComment());
	        	picture.setImageBitmap(imageBitmapList.get(position));
	        }
	        return convertView;
	    }
	    
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
	
}
