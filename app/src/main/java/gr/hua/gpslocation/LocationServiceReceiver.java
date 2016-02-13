package gr.hua.gpslocation;

import android.app.ActivityManager;
import android.app.Application;
import android.app.ApplicationErrorReport;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by maria on 2/8/2016.
 */
public class LocationServiceReceiver extends BroadcastReceiver {
    public LocationServiceReceiver() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        final LocationManager locationManager;
        locationManager= (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        boolean isGPSEnabled ;
        // check if gps is enable
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.e("gps", "" + isGPSEnabled);

        if ((arg1.getAction().matches("android.location.PROVIDERS_CHANGED")||
                arg1.getAction().matches("android.intent.action.BOOT_COMPLETED")) && isGPSEnabled){
            SystemClock.sleep(5000);
            Intent intent =new Intent(context, GetLocationService.class);
            context.startService(intent);
            Toast.makeText(context, "Location Service created ", Toast.LENGTH_SHORT).show();

        }
        else if(arg1.getAction().matches("android.location.PROVIDERS_CHANGED")&& !isGPSEnabled){
            context.stopService(new Intent(context, GetLocationService.class));
        }


    }

}
