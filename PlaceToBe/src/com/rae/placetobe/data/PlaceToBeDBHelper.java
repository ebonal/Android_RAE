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
    public static final String DATABASE_NAME    = "PlaceToBe";

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
		Log.d(TAG, "onCreate()") ;
		
		db.execSQL( Users.CREATE_TABLE_STATEMENT);
		db.execSQL(Images.CREATE_TABLE_STATEMENT);


        // Test data
        String debut = new StringBuilder("INSERT INTO users(")
        .append(Users.COLUMN_USER_ID).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_NAME).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_EMAIL).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_FOLLOWED).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_FOLLOWERS).append(") values (").toString() ;
        
        
        db.execSQL(debut+ "101, 'Robert Bakic'    , 'robert.bakic@gmail.com', 1, 0)");
        db.execSQL(debut+ "102, 'Anthony Fontaine', 'anthony.fontaine@gmail.com', 1, 0)");
        db.execSQL(debut+ "103, 'Emeric Bonal'    , 'emeric.bonal@gmail.com', 0, 0)");
        db.execSQL(debut+ "104, 'Edward Elric'    , 'fullmetal@gmail.com', 0, 1)");
        db.execSQL(debut+ "105, 'Light Yagami'    , 'kira@gmail.com', 0, 0)");
        db.execSQL(debut+ "106, 'Lelouch Vi Britannia'    , 'britannia@gmail.com', 1, 1)");
        db.execSQL(debut+ "107, 'Shirō Emiya'    , 'fate@gmail.com', 0, 0)");
        db.execSQL(debut+ "108, 'Kirigaya Kazuto'    , 'kirito@gmail.com', 0, 0)");
        db.execSQL(debut+ "109, 'Haruyuki Arita'    , 'silvercrow@gmail.com', 0, 1)");
        db.execSQL(debut+ "110, 'Kira Yamato'    , 'kira.yamato@gmail.com', 1, 0)");

	}

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
		Log.d(TAG, "onUpgrade()") ;
        // This database upgrade policy is to discard the data
        db.execSQL(DROP_TABLE_STATEMENT + Users.TABLE_NAME);
        db.execSQL(DROP_TABLE_STATEMENT + Images.TABLE_NAME);
                
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    	onUpgrade(db, oldVersion, newVersion);
    }
}
