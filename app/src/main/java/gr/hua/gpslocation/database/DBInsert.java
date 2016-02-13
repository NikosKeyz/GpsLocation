package gr.hua.gpslocation.database;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by maria on 2/6/2016.
 */
public class DBInsert extends AsyncTask<String,Void,String>{
    Context ctx;
String maria=android.os.Build.MODEL;


    public DBInsert(Context ctx){
        this.ctx=ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String method= params[0];
        //Url with query to insert data in database
        String url_ins="http://webms.hua.gr/~it21251/android_ex2/addLocation.php";
        //den kserw ti na valw edw apofasise kai pes m auto me tin mac den douleuei panta
        String model = Build.SERIAL;
        String manufacturer = Build.MANUFACTURER;
            String longitute= params[1];
            String latitude= params[2];
            String location= "Lon:"+longitute+" Lat:"+latitude;
            try{
                //open url connection
                URL url = new URL(url_ins);
                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data =
                        URLEncoder.encode("username", "UTF-8")+"="+ URLEncoder.encode(model, "UTF-8")+"&"+
                        URLEncoder.encode("current_location","UTF-8")+"="+ URLEncoder.encode(location,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                httpURLConnection.disconnect();
            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return "Location have been added..";
    }
    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected void onProgressUpdate(Void...values){
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String result){
        Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
    }


}
