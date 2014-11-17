package com.rae.placetobe.data;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.rae.placetobe.util.ApiManager;
import com.rae.placetobe.util.GitHubMember;

public class PlaceToBeDBHelper extends SQLiteOpenHelper implements PtbColumns
{
	private static final String TAG = PlaceToBeDBHelper.class.getSimpleName() ;
	
    // Bump this for each change in the schema
    public static final int    DATABASE_VERSION = 9;

    public static final String DATABASE_NAME    = "PlaceToBe";

	private static PlaceToBeDBHelper instance;
	
	// Login List of HB user
	private static final String[] GITHUB_MEMBERS = new String[]{
		"AnthonyFontaine", "RobertBakic", "ebonal", "motof", "MiloIgor", "rmarcou", "moea-chan", "uanatol", "tpuch24", "Darthevel", "Argeel", "MichaelAdjedj"
	};

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
    
	public void onCreate(final SQLiteDatabase db)  {
		Log.d(TAG, "onCreate()") ;
		
		db.execSQL( Users.CREATE_TABLE_STATEMENT);
		db.execSQL(Images.CREATE_TABLE_STATEMENT);

        // Test data
        final String debut = new StringBuilder("INSERT INTO users(")
        .append(Users.COLUMN_USER_ID).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_NAME).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_EMAIL).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_FOLLOWED).append(PtbColumns.COMMA_SEP)
        .append(Users.COLUMN_FOLLOWERS).append(") values (").toString() ;
        
		// Observable on HB user list
		// Get the github account link to my user
		Observable<GitHubMember> o1 = Observable.from(GITHUB_MEMBERS)
			.flatMap(new Func1<String, Observable<GitHubMember>>(){
	    		@Override
	    		public Observable<GitHubMember> call(String s){
					return ApiManager.getGitHubMember(s);
	    		}
	    	});
		
		// Observable on Retrofit github members list
		Observable<GitHubMember> o2 = ApiManager.getGitHubMembersList()
			.flatMap(new Func1<List<GitHubMember>, Observable<GitHubMember>>() {
		        @Override
		        public Observable<GitHubMember> call(List<GitHubMember> gitHubMembers) {
		            return Observable.from(gitHubMembers);
		        }
		    });
		
		// Observable the two observable
		// Put the github member into a list of github member
		// Set the adapter and click listener UI
		Observable.merge(o1,o2)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(new Subscriber<GitHubMember>() {
		        @Override
		        public void onNext(GitHubMember gitHubMember) { 
		        	Log.d(TAG, "GITHUB_MEMBERS : " + gitHubMember.login);
		        	if(gitHubMember.email == null) gitHubMember.email = "";
		        	db.execSQL(debut + gitHubMember.id + ", '" + gitHubMember.login + "' , '" + gitHubMember.email + "', 0, 0)");
		        }
		
		        @Override
		        public void onCompleted() { Log.i(TAG, "Completed!");}
		
		        @Override
		        public void onError(Throwable e) { Log.e(TAG, "Ouch!"); }
		    });

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
