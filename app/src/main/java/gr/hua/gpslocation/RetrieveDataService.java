package gr.hua.gpslocation;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import gr.hua.gpslocation.database.DBSelect;

public class RetrieveDataService extends Service {
    public RetrieveDataService() {
    }
    SQLiteDatabase db;



    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Retrive Data Service", "Service Created");
    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        Log.e("Retrive Data Service", "Service Started");
        super.onStart(intent, startId);
        //Start asynctask to retrieve data from server
        String method="select";
        DBSelect select = new DBSelect(getApplicationContext());
        select.execute(method);


    }


        @Override
    public IBinder onBind(Intent intent) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        //delete current data from db
        int count=getApplicationContext().getContentResolver().delete(MyContentProvider.myUri,null,null);
        Log.e("Delete", "Records Deleted " + count);
        Toast.makeText(getApplicationContext(), "Retrieve Data Service destroyed", Toast.LENGTH_SHORT).show();

    }
}
