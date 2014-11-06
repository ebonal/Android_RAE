package com.rae.placetobe.data;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;


// BOB : J'utilise cette class Proxy sur le sur Content Provider uniquement pour pouvoir 
//       tracer les appels.

public class DBProxy
{	
	static public Uri insert(Context context, Uri tableUri, ContentValues values)  {
	    return context.getContentResolver().insert(tableUri, values);
	}	
	
	static public int delete(Context context, Uri tableUri, String rowIds) {
		return context.getContentResolver().delete(tableUri, rowIds, null) ;
	}
	
	static public int update(Context context, Uri tableUri, Integer id, ContentValues values) {
		tableUri = Uri.parse(tableUri.toString() + "/" + id) ;
		return context.getContentResolver().update(tableUri, values, null, null) ;
	}
}
