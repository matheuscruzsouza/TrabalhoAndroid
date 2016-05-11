package com.example.matheus.appandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Aula_view extends AppCompatActivity {

    private TextView Titulo_aula;
    private JSONObject aula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);

        Bundle args = getIntent().getExtras();
        String SAula = args.getString("aula");
        try {
            aula = new JSONObject(SAula);
            this.setTitle(aula.getString("conteudo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Titulo_aula = (TextView) findViewById(R.id.TV_AulaTitulo);

        try {
            Titulo_aula.setText(aula.getString("conteudo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
