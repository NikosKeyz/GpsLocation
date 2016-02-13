package gr.hua.gpslocation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by maria on 2/12/2016.
 */
public class RetrieveDataStartServiceReceiver extends BroadcastReceiver {
    public RetrieveDataStartServiceReceiver(){
    }
    @Override
    public void onReceive(Context context, Intent arg1) {
        LocationManager locationManager= (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(isGPSEnabled){
            Intent service = new Intent(context, RetrieveDataService.class);
            context.startService(service);
        }



    }

}
