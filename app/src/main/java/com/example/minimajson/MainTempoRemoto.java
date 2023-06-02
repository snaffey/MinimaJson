package com.example.minimajson;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainTempoRemoto extends Activity implements View.OnClickListener {
    EditText edittext;
    TextView textview1, textview2, textview3, textview4, textview5;
    ImageView imageview;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintempo);
        edittext = (EditText) findViewById(R.id.editText1);
        textview1 = (TextView) findViewById(R.id.textView1);
        textview2 = (TextView) findViewById(R.id.textView2);
        textview3 = (TextView) findViewById(R.id.textView3);
        textview4 = (TextView) findViewById(R.id.textView4);
        textview5 = (TextView) findViewById(R.id.textView5);
        imageview = (ImageView) findViewById(R.id.imageView1);
        btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String city = edittext.getText().toString();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=d77e204ec084228723f9b072db2355ff";
        new GetMethodTempo().execute(url);
    }

    public class GetMethodTempo extends AsyncTask<String, Void, Tempo> {
        @Override
        protected Tempo doInBackground(String... strings) {
            Tempo tempo = new Tempo();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String server_response = readStream(urlConnection.getInputStream());
                    JsonObject jsonObject = JsonObject.readFrom(server_response);
                    JsonArray weatherArray = jsonObject.get("weather").asArray();
                    JsonObject mainObject = jsonObject.get("main").asObject();
                    tempo.setId(weatherArray.get(0).asObject().get("id").asInt());
                    tempo.setIcon(weatherArray.get(0).asObject().get("icon").asString());
                    tempo.setTemp(mainObject.get("temp").asFloat());
                    tempo.setHumidade(mainObject.get("humidity").asFloat());
                    tempo.setTemp_min(mainObject.get("temp_min").asFloat());
                    tempo.setTemp_max(mainObject.get("temp_max").asFloat());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return tempo;
        }

        @Override
        protected void onPostExecute(Tempo tempo) {
            super.onPostExecute(tempo);
            textview1.setText("Descrição: " + tempo.getDescription());
            textview2.setText("Temperatura: " + tempo.getTemp() + "º");
            textview3.setText("Humidade: " + tempo.getHumidade());
            textview4.setText("Temperatura Mínima: " + Math.round(tempo.getTemp_min() - 273.15) + "º");
            textview5.setText("Temperatura Máxima: " + Math.round(tempo.getTemp_max() - 273.15) + "º");
        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
