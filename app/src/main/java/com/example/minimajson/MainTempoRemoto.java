package com.example.minimajson;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainTempoRemoto extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintempo);
        new GetMethodTempo().execute("https://api.openweathermap.org/data/2.5/weather?q=funchal&appid=d77e204ec084228723f9b072db2355ff");
    }

    public class GetMethodTempo extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected String doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                int responseCode = urlConnection.getResponseCode();
                Log.v("HttpURLConnectionTempo", "Response Code: " + responseCode);
                if(responseCode == HttpURLConnection.HTTP_OK){
                    server_response = readStream(urlConnection.getInputStream());
                    Log.v("HttpURLConnectionTempo", server_response);
                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("ResponseTempo", "" + server_response);
        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try{
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (reader != null){
                try{
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
