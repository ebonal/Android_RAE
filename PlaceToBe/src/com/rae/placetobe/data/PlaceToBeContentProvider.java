package com.rae.placetobe.data;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/* 
 * The ContentProvider can be accessed from several programs at the same time,
 * therefore you must implement the access thread-safe.
 * The easiest way is to use the keyword synchronized in front of all methods of the ContentProvider...
 */
public class PlaceToBeContentProvider extends ContentProvider
{
	private static final String TAG = PlaceToBeContentProvider.class.getSimpleName() ;

	private static final String AUTHORITY = "com.rae.placetobe.provider";

	private static final String USERS_PATH     = "users";
	private static final String FOLLOWED_PATH     = "followed";
	private static final String IMAGES_PATH    = "images";	
	
	public static final Uri USERS_URI   = Uri.parse("content://" + AUTHORITY + "/" + USERS_PATH);
	public static final Uri FOLLOWED_URI   = Uri.parse("content://" + AUTHORITY + "/" + FOLLOWED_PATH);
	public static final Uri IMAGES_URI  = Uri.parse("content://" + AUTHORITY + "/" + IMAGES_PATH);

	  // Used for the UriMacher
	private static final int USERS    		 = 20;
	private static final int USERS_ID 		 = 21;
	private static final int IMAGES   		 = 30;
	private static final int IMAGES_ID 		 = 31;
	private static final int USERS_FOLLOW 	 = 40;
	  
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
	    sURIMatcher.addURI(AUTHORITY, USERS_PATH      			, USERS);
	    sURIMatcher.addURI(AUTHORITY, USERS_PATH+"/#" 			, USERS_ID);
	    sURIMatcher.addURI(AUTHORITY, FOLLOWED_PATH 	, USERS_FOLLOW);
	    sURIMatcher.addURI(AUTHORITY, IMAGES_PATH     			, IMAGES);
	    sURIMatcher.addURI(AUTHORITY, IMAGES_PATH+"/#"			, IMAGES_ID);
	  }
	
	@Override	
	public synchronized String getType(Uri uri) {
		return null;
	}

	/*	Initialize your provider. The Android system calls this method immediately after it creates your provider.
		Notice that your provider is not created until a ContentResolver object tries to access it. 
		You should perform only fast-running initialization tasks in this method, and defer database creation 
		and data loading until the provider actually receives a request for the data.
		If you do lengthy tasks in onCreate(), you will slow down your provider's startup.
		 In turn, this will slow down the response from the provider to other applications. */
	@Override
	public synchronized boolean onCreate() {
		Log.d(TAG, "onCreate()") ;
		PlaceToBeDBHelper.getInstance(getContext()) ;
		return true;
	}

	/* Must return a Cursor object, or if it fails, throw an Exception.
	   If you are using an SQLite database as your data storage, you can simply
	   return the Cursor returned by one of the query() methods of the SQLiteDatabase class.
	   If the query does not match any rows, you should return a Cursor instance whose getCount() method returns 0.
	   You should return null only if an internal error occurred during the query process.	 */
	@Override
	public synchronized Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		// NOte : On n'utilise pas le parametre projection, mais l'URI pour determiner la projection
		
		Log.d(TAG, "query()") ;

		Cursor cursor ;

		int uriType = sURIMatcher.match(uri);
		switch (uriType)
		{
			case USERS :
				cursor = UsersDao.findAll(getContext(), selectionArgs) ;
				break ;
			
			case USERS_FOLLOW :
				cursor = UsersDao.findFollow(getContext(), selectionArgs) ;
				break ;
				
			case IMAGES :
				cursor = ImagesDao.findAll(getContext(), selectionArgs) ;
				break ;

			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}

		// Make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}
	
	/* Adds a new row to the appropriate table, using the values in the ContentValues argument.
	   If a column name is not in the ContentValues argument, you may want to provide a default value
	   for it either in your provider code or in your database schema.
	   This method should return the content URI for the new row. 
	   To construct this, append the new row's _ID (or other primary key) value to the table's content URI, using withAppendedId().	 */
	@Override
	public synchronized Uri insert(Uri uri, ContentValues values)
	{
		Context context = getContext() ;

		Log.d(TAG, "insert()") ;
	    int uriType = sURIMatcher.match(uri);
	    
	    long insertedId = 0;
	    switch (uriType) 
	    {
			case USERS:
				insertedId = UsersDao.insert(getContext(), values);
				context.getContentResolver().notifyChange(uri, null);
			    return Uri.parse(USERS_PATH + "/" + insertedId);

			case IMAGES:
				insertedId = ImagesDao.insert(getContext(), values);
				context.getContentResolver().notifyChange(uri, null);
			    return Uri.parse(IMAGES_PATH + "/" + insertedId);
			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	}

	
	/*
		Takes the same ContentValues argument used by insert(), and the same selection 
		and selectionArgs arguments used by delete() and ContentProvider.query().
		This may allow you to re-use code between these methods. */
	@Override
	public synchronized int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
	{
		Context context = getContext() ;

		Log.d(TAG, "update()") ;
		int uriType = sURIMatcher.match(uri);
	
		String id = uri.getLastPathSegment();
		
		switch (uriType)
		{
			case USERS_ID :
				UsersDao.update(context, id, values) ;
				break;

			case IMAGES_ID :
				ImagesDao.update(context, id, values) ; //  selection, selectionArgs...
				break;

			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		context.getContentResolver().notifyChange(uri, null);
		return 1 ; // rowsUpdated;
	}

	/*
		The delete() method does not have to physically delete rows from your data storage.
		If you are using a sync adapter with your provider, you should consider marking a deleted row
		with a "delete" flag rather than removing the row entirely. 
		The sync adapter can check for deleted rows and remove them from the server before deleting them from the provider.	 */
	@Override
	public synchronized int delete(Uri uri, String selection, String[] selectionArgs)
	{
		Context context = getContext() ;
		
		Log.d(TAG, "delete()") ;
	    int uriType = sURIMatcher.match(uri);
	    int rowsDeleted = 0;
	    
		switch (uriType)
		{
			case USERS:
				rowsDeleted = UsersDao.delete(context, selection) ; 
				break;

			case IMAGES:
				rowsDeleted = ImagesDao.delete(context, selection) ; 
				break;

			default:
				throw new IllegalArgumentException("Unknown URI: " + uri);
		}
		
		context.getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}
}