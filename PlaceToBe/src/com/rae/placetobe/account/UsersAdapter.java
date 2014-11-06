package com.rae.placetobe.account;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.rae.placetobe.R;
import com.rae.placetobe.data.UsersDao;
import com.rae.placetobe.util.TextHelper;

public class UsersAdapter extends ResourceCursorAdapter
{
	public UsersAdapter(Context context) {
		super(context, R.layout.activity_users_debug_row, null, false);
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
	    TextView tvUserName = (TextView)view.findViewById(R.id.userName) ;
	    TextView tvUserMail = (TextView)view.findViewById(R.id.userMail) ;    
	       		
		
		TextHelper.putString(tvUserName, cursor, UsersDao.IDX_NAME) ;
		TextHelper.putString(tvUserMail, cursor, UsersDao.IDX_EMAIL) ;
	}
}
