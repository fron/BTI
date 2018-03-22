package com.example.francisqueins.breakingtheice;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Francisqueins on 9/18/2017.
 */


public class myAsyncTask extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    myAsyncTask(Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params){

        String type = params[0];
        String login_url = "http://10.0.2.2/login.php";  //*** IP for ACCESS ***//
        String register_url = "http://10.0.2.2/register.php";
        String alert_url = "http://10.0.2.2/alert.php";
        String points_url = "http://10.0.2.2/lookForAlerts.php";
        if(type.equals("login")) {
            try {
                String usr_phone = params[1];
                String usr_pwd = params[2];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("usr_phone", "UTF-8")+"="+URLEncoder.encode(usr_phone, "UTF-8")+"&"
                        +URLEncoder.encode("usr_pwd", "UTF-8")+"="+URLEncoder.encode(usr_pwd, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(type.equals("register")) {

            try {
                String usr_phone = params[1];
                String usr_name = params[2];
                String usr_pwd = params[3];
                String usr_created = params[4];
                String usr_lastLogin = params[5];
                String usr_addAlertDate = params[6];
                String usr_addAlertTime = params[7];
                String usr_active = params[8];

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("usr_phone", "UTF-8")+"="+URLEncoder.encode(usr_phone, "UTF-8")+"&"
                        +URLEncoder.encode("usr_name", "UTF-8")+"="+URLEncoder.encode(usr_name, "UTF-8")+"&"
                        +URLEncoder.encode("usr_pwd", "UTF-8")+"="+URLEncoder.encode(usr_pwd, "UTF-8")+"&"
                        +URLEncoder.encode("usr_created", "UTF-8")+"="+URLEncoder.encode(usr_created, "UTF-8")+"&"
                        +URLEncoder.encode("usr_lastLogin", "UTF-8")+"="+URLEncoder.encode(usr_lastLogin, "UTF-8")+"&"
                        +URLEncoder.encode("usr_addAlertDate", "UTF-8")+"="+URLEncoder.encode(usr_addAlertDate, "UTF-8")+"&"
                        +URLEncoder.encode("usr_addAlertTime", "UTF-8")+"="+URLEncoder.encode(usr_addAlertTime, "UTF-8")+"&"
                        +URLEncoder.encode("usr_active", "UTF-8")+"="+URLEncoder.encode(usr_active, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(type.equals("alert")) {

            try {
                String alt_phone   = params[1];
                String alt_locLat  = params[2];
                String alt_locLong = params[3];
                String alt_created = params[4];
                String alt_time    = params[5];
                String alt_active = params[6];

                URL url = new URL(alert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("alt_phone", "UTF-8")+"="+URLEncoder.encode(alt_phone, "UTF-8")+"&"
                        +URLEncoder.encode("alt_latitude", "UTF-8")+"="+URLEncoder.encode(alt_locLat, "UTF-8")+"&"
                        +URLEncoder.encode("alt_longitude", "UTF-8")+"="+URLEncoder.encode(alt_locLong, "UTF-8")+"&"
                        +URLEncoder.encode("alt_date", "UTF-8")+"="+URLEncoder.encode(alt_created, "UTF-8")+"&"
                        +URLEncoder.encode("alt_time", "UTF-8")+"="+URLEncoder.encode(alt_time, "UTF-8")+"&"
                        +URLEncoder.encode("alt_active", "UTF-8")+"="+URLEncoder.encode(alt_active, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if(type.equals("locatePoints")) {

            try {

                String alt_latitude   = params[1];
                String alt_longitude  = params[2];

                URL url = new URL(points_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("alt_latitude", "UTF-8")+"="+URLEncoder.encode(alt_latitude, "UTF-8")+"&"
                        +URLEncoder.encode("alt_longitude", "UTF-8")+"="+URLEncoder.encode(alt_longitude, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    protected void onPreExecute()
    {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result)
    {
        alertDialog.setMessage(result);
        alertDialog.show();
    }

    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }

}
