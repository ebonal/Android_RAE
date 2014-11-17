package com.rae.placetobe.history;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rae.placetobe.R;
import com.rae.placetobe.model.ImageData;
import com.squareup.picasso.Picasso;

public class ClusterHistoryActivity extends AbstractHistoryActivity
{	
	private static final String TAG = ClusterHistoryActivity.class.getSimpleName();
	
	@InjectView(R.id.gridview) GridView gridview;

	@Override
	protected GridView getGridView() {
		return gridview;
	}
	
	public static final String EXTRA_PHOTOS = "EXTRA_PHOTOS" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(TAG, "onCreate()") ;
		super.onCreate(savedInstanceState); // Will inflate the layout using getContentViewId()
		
		ButterKnife.inject(this);

		int i = 0 ;
		Intent intent = getIntent();
		
		Parcelable[] ps = intent.getParcelableArrayExtra(EXTRA_PHOTOS) ;
		if(ps!=null && ps.length>0)
		{
			ImageData[] iDatas = new ImageData[ps.length] ;	
			for(Parcelable p : ps) iDatas[i++] = (ImageData)p ;
			gridview.setAdapter(new ClusterHistoryAdapter(getApplicationContext(), iDatas));
		}
	}
	
	public class ClusterHistoryAdapter extends ArrayAdapter<ImageData> 
	{
		final private LayoutInflater layoutinflater ;
		public ClusterHistoryAdapter(Context context, ImageData[] datas) {
			super(context, R.layout.history_gridview_item, datas);
			layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			// assign the view we are converting to a local variable
			View view = convertView;

			// first check to see if the view is null. if so, we have to inflate it.
			// to inflate it basically means to render, or show, the view.
			if (view== null) {
				view = layoutinflater.inflate(R.layout.history_gridview_item, null);
			}

	        // Get my view
	        final ImageView picture = (ImageView)view.findViewById(R.id.picture);
	        final TextView  comment = (TextView) view.findViewById(R.id.text);

	        ImageData data = getItem(position) ;
	        
	        comment.setText(data.getComment());
	       	picture.setImageResource(R.drawable.ic_thumb_img);
			Picasso.with(getContext()).load(new File(data.getFilePath())).resize(200, 200).centerCrop().into(picture);
	        
			return view;
		}
	}
}
