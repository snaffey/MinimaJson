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

import java.io.IOException;
import java.io.InputStream;

public class Main extends Activity implements View.OnClickListener {

    EditText edittext;
    TextView textview1, textview2, textview3, textview4, textview5;
    ImageView imageview;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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

    public void onClick (View v) {
        if (v.getId() == R.id.button1)
            verTempo(v);
    }

    // https://developer.android.com/reference/android/os/AsyncTask
    public void verTempo (View v){
        JSONTarefaTempo task = new JSONTarefaTempo();
        task.execute(new String[]{edittext.getText().toString()});
    }

    private class JSONTarefaTempo extends AsyncTask<String, Void, Tempo> {
        @Override
        protected Tempo doInBackground(String... params) {
            Tempo tempo = new Tempo();
            //Assets
            JsonObject jsonObject = JsonObject.readFrom(loadJson());
            JsonArray weatherArray = jsonObject.get("weather").asArray();
            JsonObject mainObject = jsonObject.get("main").asObject();
            tempo.setId(weatherArray.get(0).asObject().get("id").asInt());
            tempo.setIcon(weatherArray.get(0).asObject().get("icon").asString());
            //tempo.iconData = ;
            tempo.setTemp(mainObject.get("temp").asFloat());
            tempo.setHumidade(mainObject.get("humidity").asFloat());
            tempo.setTemp_min(mainObject.get("temp_min").asFloat());
            tempo.setTemp_max(mainObject.get("temp_max").asFloat());
            return tempo;
        }

        @Override
        protected void onPostExecute(Tempo tempo) {
            super.onPostExecute(tempo);
            textview1.setText("Descrição: " + tempo.getDescription());
            textview2.setText("Temperatura: " + tempo.getTemp()+"º");
            textview3.setText("Humidade: " + tempo.getHumidade());
            textview4.setText("Temperatura Mínima: " + Math.round(tempo.getTemp_min() - 273.15)+"º");
            textview5.setText("Temperatura Máxima: " + Math.round(tempo.getTemp_max() - 273.15)+"º");
        }
    }

    private String loadJson() {
        String json = null;
        try {
            InputStream inputStream = getAssets().open("tempo.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

}
