package gr.hua.gpslocation;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        super.onDestroy();
        //delete current data from db
        int count=getApplicationContext().getContentResolver().delete(MyContentProvider.myUri,null,null);
        Log.e("Delete", "Records Deleted " + count);
        Toast.makeText(getApplicationContext(), "Retrieve Data Service destroyed", Toast.LENGTH_SHORT).show();

    }
}
