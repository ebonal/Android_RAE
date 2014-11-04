package com.rae.placetobe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlaceToBeDBHelper extends SQLiteOpenHelper implements PtbColumns
{
	private static final String TAG = PlaceToBeDBHelper.class.getSimpleName() ;
	
    // Bump this for each change in the schema
    public static final int    DATABASE_VERSION = 1;
    public static final String DATABASE_NAME    = "PlaceTobe";

	private static PlaceToBeDBHelper instance;

	synchronized static public PlaceToBeDBHelper getInstance(Context context)
	{
		Log.d(TAG, "getInstance()") ;
		if (instance == null)
			instance = new PlaceToBeDBHelper(context.getApplicationContext());
		return instance;
	}

    private PlaceToBeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
	public void onCreate(SQLiteDatabase db)  {
		db.execSQL( Users.CREATE_TABLE_STATEMENT);
		db.execSQL(Images.CREATE_TABLE_STATEMENT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database upgrade policy is to discard the data
        db.execSQL(DROP_TABLE_STATEMENT + Users.TABLE_NAME);
        db.execSQL(DROP_TABLE_STATEMENT + Images.TABLE_NAME);
        onCreate(db);
    }
    
    
}
