package gr.hua.gpslocation;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import gr.hua.gpslocation.database.DBHelper;

/**
 * Created by maria on 2/8/2016.
 */
public class MyContentProvider extends ContentProvider {

    private static final String AUTHORITY="gr.hua.gpslocation.MyContentProvider";
    public static final Uri myUri = Uri.parse("content://"+AUTHORITY+"/"+ DBHelper.DATABASE_TABLE);
    private static final String PATH="locations";

    private static final UriMatcher sUriMatcher ;
    static{
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, PATH, 1);

    }

    DBHelper mDBHelper;


    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(this.getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {

        SQLiteDatabase mDB = mDBHelper.getReadableDatabase();
        Cursor mCursor;
        switch(sUriMatcher.match(uri)){
            case 1:
                mCursor = mDB.query(DBHelper.DATABASE_TABLE, null, null, null, null, null, null);
                break;
            case 2:
                selection = "_USEID=?";
                selectionArgs[0] = uri.getLastPathSegment();
                mCursor = mDB.query(DBHelper.DATABASE_TABLE, null, selection, selectionArgs, null, null, null);
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }
        return mCursor;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        long id = -1;
        SQLiteDatabase mDB = mDBHelper.getWritableDatabase();

        switch(sUriMatcher.match(uri)){
            case 1:
                id = mDB.insert(DBHelper.DATABASE_TABLE, null, values);
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }

        return Uri.parse(uri.toString()+"/"+id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int count=0;
        SQLiteDatabase mDB = mDBHelper.getWritableDatabase();

        switch(sUriMatcher.match(uri)){
            case 1:
                count = mDB.delete(DBHelper.DATABASE_TABLE,null,null);
                break;
            default:
                throw new IllegalArgumentException ("Content URI pattern not recognizable: "+uri);
        }

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) { return null; }

}
