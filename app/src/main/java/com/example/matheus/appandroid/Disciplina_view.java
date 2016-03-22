package com.example.matheus.appandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;

public class Disciplina_view extends AppCompatActivity {

    private TextView materia_nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        Bundle args = getIntent().getExtras();
        String materia = args.getString("materia_nome");

        materia_nome = (TextView) findViewById(R.id.materia_nome);
        materia_nome.setText("Disciplina de "+ (materia).toLowerCase());

    }
}
