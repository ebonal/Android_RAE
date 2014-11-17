package com.rae.placetobe.history;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.rae.placetobe.R;
import com.rae.placetobe.data.ImagesDao;
import com.rae.placetobe.util.TextHelper;
import com.squareup.picasso.Picasso;

public class HistoryAdapter extends ResourceCursorAdapter
{
	public HistoryAdapter(Context context) {
		super(context, R.layout.history_gridview_item, null, false);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{		
        // Get my view
        final ImageView picture = (ImageView)view.findViewById(R.id.picture);
        final TextView  comment = (TextView) view.findViewById(R.id.text);
		
		TextHelper.putString(comment, cursor, ImagesDao.IDX_COMMENT) ;
        
		String path = cursor.getString(ImagesDao.IDX_PATH) ;
		
       	picture.setImageResource(R.drawable.ic_thumb_img);
		Picasso.with(context).load(new File(path)).resize(200, 200).centerCrop().into(picture);
		
		/*
       	Observable.just(path)
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
						picture.setImageBitmap(bitmap);
					}
				});
			*/

	}
}
