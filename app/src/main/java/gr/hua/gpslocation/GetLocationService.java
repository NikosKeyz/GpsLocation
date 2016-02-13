package gr.hua.gpslocation;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import gr.hua.gpslocation.database.DBInsert;

public class GetLocationService extends Service {

    private LocationManager locationManager;

    public GetLocationService() { }

    @Override
    public IBinder onBind(Intent arg0) { return null; }

    @Override
    public void onCreate() {

        super.onCreate();

        Log.e("Location Service", "Service Created");

    }

    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {

        super.onStart(intent, startId);

        Log.e("Location Service", "Service Started");
        //initiate location manager
        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        //check for permissions
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"Sth went wrong",Toast.LENGTH_LONG).show();
        }


    //call location manager every 30 seconds if position have changed
    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
            30000,5, listener);


    }

    private LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            Log.e("Location Service", "Location Changed");
            //check for new location
            if (location == null)
                return;
            //check for internet connection
            if (isConnectingToInternet(getApplicationContext())) {

                try {

                    String method="insert";
                    //get longitude and latitude
                    String longitude= Double.toString(location.getLongitude());
                    String latitude= Double.toString(location.getLatitude());
                    //call async task to insert current location to database
                    DBInsert insert = new DBInsert(getApplicationContext());
                    insert.execute(method, longitude, latitude);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        public void onProviderDisabled(String provider) {

            Log.e("Status","Provider is Disable");

        }

        @Override
        public void onProviderEnabled(String provider) {

            Log.e("Status","Provider is Enable");

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            Log.e("Status","Status changed");

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        Toast.makeText(getApplicationContext(), "Location Service destroyed", Toast.LENGTH_SHORT).show();

    }

    public static boolean isConnectingToInternet(Context _context) {

        ConnectivityManager connectivity =
                (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();

            if (info != null) {
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
            }

        }

        return false;
    }
}