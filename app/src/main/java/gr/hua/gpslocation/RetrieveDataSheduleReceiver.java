package gr.hua.gpslocation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by maria on 2/12/2016.
 */
public class RetrieveDataSheduleReceiver extends BroadcastReceiver{

    private static final long REPEAT_TIME = 1000 * 10;

    public RetrieveDataSheduleReceiver() { }

    @Override
    public void onReceive(Context context, Intent arg1) {
        //Alarm Manager to start service every 10 seconds
        LocationManager locationManager= (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if ((arg1.getAction().matches("android.location.PROVIDERS_CHANGED")||
                arg1.getAction().matches("android.intent.action.BOOT_COMPLETED")) && isGPSEnabled) {

            Toast.makeText(context, "Retrieve Data Service created ", Toast.LENGTH_SHORT).show();

            AlarmManager service = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);

            Intent i = new Intent(context, RetrieveDataStartServiceReceiver.class);
            PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Calendar cal = Calendar.getInstance();
            // Fetch every 10 seconds

            service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(), REPEAT_TIME, pending);

        } else if(arg1.getAction().matches("android.location.PROVIDERS_CHANGED")&& !isGPSEnabled){

            context.stopService(new Intent(context, RetrieveDataService.class));

        }

    }
}
