package com.rae.placetobe.debug;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.rae.placetobe.R;
import com.rae.placetobe.data.ImagesDao;
import com.rae.placetobe.util.TextHelper;

public class ImagesDebugAdapter extends ResourceCursorAdapter
{
	public ImagesDebugAdapter(Context context) {
		super(context, R.layout.activity_images_debug_row, null, false);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{		
	    TextView tvImageId      = (TextView)view.findViewById(R.id.imageId) ;
	    TextView tvImagePath    = (TextView)view.findViewById(R.id.imagePath) ;
	    TextView tvImageComment = (TextView)view.findViewById(R.id.imageComment) ;
		
		TextHelper.putInteger(tvImageId     , cursor, ImagesDao.IDX__ID) ;
		TextHelper.putString (tvImagePath   , cursor, ImagesDao.IDX_PATH) ;
		TextHelper.putString (tvImageComment, cursor, ImagesDao.IDX_COMMENT) ;
	}
}
