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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.rae.placetobe.util.ImageData;

public class HistoryActivity extends Activity {
	
	private GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class ImageAdapter extends BaseAdapter {
	    private Context mContext;
	    // references to our images
	    private ArrayList<ImageData> mImageDataList = new ArrayList<ImageData>();

	    
	    public ImageAdapter(Context c) {
	        mContext = c;
	        SharedPreferences sharePref = c.getSharedPreferences(MainActivity.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE) ;
	        mImageDataList.addAll(ImageData.getAllImageDatas(sharePref)) ;
	    }

	    public int getCount() {
	        return mImageDataList.size();
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    public long getItemId(int position) {
	        return 0;
	    }

	    // create a new ImageView for each item referenced by the Adapter
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View gridViewItem;
	        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        if (convertView == null) {  // if it's not recycled, initialize some attributes
	        	gridViewItem = new View(mContext);
	        	gridViewItem = inflater.inflate(R.layout.history_gridview_item, null);
                TextView textView = (TextView) gridViewItem.findViewById(R.id.grid_text);
                ImageView imageView = (ImageView)gridViewItem.findViewById(R.id.grid_image);
                textView.setText(mImageDataList.get(position).getComment());
                File imgFile = new  File(mImageDataList.get(position).getFilePath());
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
	        } else {
	        	gridViewItem = (View) convertView;
	        }

	        return gridViewItem;
	    }
	}
}
