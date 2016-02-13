package gr.hua.gpslocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ShowLocations extends Activity {
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_locations);
        button = (Button) findViewById(R.id.main);
        //button to go back in main
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClassName("gr.hua.gpslocation", "gr.hua.gpslocation.MainActivity");
                startActivity(i);

            }
        });
        //handle intent
        Intent intent = this.getIntent();
        Context ctx= getApplicationContext();

        String URL = "content://gr.hua.gpslocation.MyContentProvider/locations";
        Uri uri = Uri.parse(URL);

        //Receive data from Content Provider
        Cursor c = this.getContentResolver().query(uri, null, null, null, null);
        if (c.getCount()==0) {
            Log.e("Sorry", "No records found");
            Toast.makeText(ctx,"No Records Found!Open Your Gps And Try Again",Toast.LENGTH_LONG).show();
            return;
        }
        if (c.moveToFirst()) {
            TableLayout stk = (TableLayout) findViewById(R.id.table_main);
            TableRow tbrow0 = new TableRow(this);//Create a dynamic table
            tbrow0.setBackgroundColor(Color.parseColor("#003399"));
            TextView tv0 = new TextView(this);
            tv0.setText(" USEID ");
            tv0.setTextColor(Color.WHITE);
            tv0.setGravity(Gravity.CENTER);
            tv0.setTypeface(null, Typeface.BOLD);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(this);
            tv1.setText(" USERNAME ");
            tv1.setTextColor(Color.WHITE);
            tv1.setGravity(Gravity.CENTER);
            tv1.setTypeface(null, Typeface.BOLD);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(this);
            tv2.setText(" LOCATION ");
            tv2.setTextColor(Color.WHITE);
            tv2.setGravity(Gravity.CENTER);
            tv2.setTypeface(null, Typeface.BOLD);
            tbrow0.addView(tv2);
            stk.addView(tbrow0);

            do {
                TableRow tbrow = new TableRow(this);
                TextView t0v = new TextView(this);
                t0v.setText(c.getString(c.getColumnIndex("_USEID")));
                t0v.setTextColor(Color.parseColor("#000066"));
                t0v.setGravity(Gravity.CENTER);
                tbrow.addView(t0v);
                TextView t1v = new TextView(this);
                t1v.setText(c.getString(c.getColumnIndex("_USERNAME")));
                t1v.setTextColor(Color.parseColor("#000066"));
                t1v.setGravity(Gravity.CENTER);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(c.getString(c.getColumnIndex("_CURRENT_LOCATION")));
                t2v.setTextColor(Color.parseColor("#000066"));
                t2v.setGravity(Gravity.CENTER);
                tbrow.addView(t2v);
                stk.addView(tbrow);

            } while (c.moveToNext());
        }
    }
}
