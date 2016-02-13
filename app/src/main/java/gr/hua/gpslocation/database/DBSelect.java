package gr.hua.gpslocation.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import gr.hua.gpslocation.MyContentProvider;

/**
 * Created by maria on 2/9/2016.
 */
public class DBSelect extends AsyncTask<String,Void,String> {

    Context ctx;
    public DBSelect(Context ctx){
        this.ctx=ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        //server url with select query
        String select_url="http://www.dit.hua.gr/~it21251/android_ex2/select.php";
        String method=params[0];
        String result = null;
        if(method.equals("select")){
            try{
                //connect to url
                URL url = new URL(select_url);
                HttpURLConnection httpURLConnection= (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                //BufferReader to get data
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                StringBuilder stringBuilder = new StringBuilder();
                //put data in stringBuilder
                while ((line=bufferedReader.readLine())!=null){

                    stringBuilder.append(line+"\n");

                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();//close Connection
                result = stringBuilder.toString();
                //paring data
                int useid;
                String username;
                String current_location;
                //create json object to collect data
                try{
                    JSONArray jArray = new JSONArray(result);
                    //delete current data from db
                    int count=ctx.getContentResolver().delete(MyContentProvider.myUri,null,null);
                    Log.e("Delete","Records Deleted "+count);
                    for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        useid=json_data.getInt("useid");
                        username=json_data.getString("username");
                        current_location=json_data.getString("current_location");
                        //add new data to db
                        ContentValues values = new ContentValues();
                        values.put(DBHelper.KEY_USEID,useid);
                        values.put(DBHelper.KEY_USERNAME,username);
                        values.put(DBHelper.KEY_CURRENT_LOCATION, current_location);
                        ctx.getContentResolver().insert(MyContentProvider.myUri, values);
                    }

                }catch(JSONException e1){
                    Toast.makeText(ctx, "No data Found", Toast.LENGTH_LONG).show();
                }
            }catch  (MalformedURLException e){
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return result;
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
      Log.e("Data on web server","Data found!");
    }


}
