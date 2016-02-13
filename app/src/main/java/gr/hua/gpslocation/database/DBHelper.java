package gr.hua.gpslocation.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by maria on 2/8/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    //Declare 3 columns in database
    public static final String KEY_USEID= "_USEID";
    public static final String KEY_USERNAME = "_USERNAME";
    public static final String KEY_CURRENT_LOCATION = "_CURRENT_LOCATION";

    private static final String DATABASE_NAME = "gpsprovider";
    public static final String DATABASE_TABLE = "locations";
    private static final int DATABASE_VERSION = 1;

    //Create the database
    private static final String DATABASE_CREATE =
            "CREATE TABLE "+ DATABASE_TABLE +
                    " ("+ KEY_USEID +" VARCHAR2(200) NOT NULL, "
                    + KEY_USERNAME +" VARCHAR2(200) NOT NULL, "
                    + KEY_CURRENT_LOCATION +" VARCHAR2(200) NOT NULL "
                  + ");";


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);

    }
    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
