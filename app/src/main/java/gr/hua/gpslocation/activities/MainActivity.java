package gr.hua.gpslocation.activities;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ApplicationErrorReport;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import gr.hua.gpslocation.MyContentProvider;
import gr.hua.gpslocation.R;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: checkGPSstatus();

        EnableGPSIfPossible();

        initializeButton();

    }

    private void checkGPSstatus() {

        int tries = 0;

        //TODO: add while gps is not enabled exit

    }

    private void EnableGPSIfPossible() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //calls dialog alert function
            buildAlertMessageNoGps();
            int count = getApplicationContext().getContentResolver().delete(MyContentProvider.myUri, null, null);
            Log.e("Delete", "Records Deleted " + count);
        }
    }

    private void initializeButton() {

        /* Connect button varieable with layout element*/
        button = (Button) findViewById(R.id.button);

        /* Set on-click functionality */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Create intent */
                Intent i = new Intent();
                i.setClassName("gr.hua.gpslocation", "gr.hua.gpslocation.activities.ShowLocations");

                /* Start intent */
                startActivity(i);

            }
        });

    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}




