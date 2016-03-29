package com.example.matheus.appandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Aula_view extends AppCompatActivity {

    private TextView Titulo_aula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aula);

        Bundle args = getIntent().getExtras();
        String aula_nome = args.getString("aula_nome");

        this.setTitle(aula_nome);

        Titulo_aula = (TextView) findViewById(R.id.TV_AulaTitulo);

        Titulo_aula.setText("Teste de Aula");


    }
}
