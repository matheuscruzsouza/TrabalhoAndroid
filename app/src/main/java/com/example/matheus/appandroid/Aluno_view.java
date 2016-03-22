package com.example.matheus.appandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class Aluno_view extends AppCompatActivity {

    private ListView LV_Disciplinas;
    private DatabaseAdapter BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Aplica a setinha no actionBar

        BD = new DatabaseAdapter(this);

        final List<String> disciplinas = BD.BuscarDisciplinas() ;

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, disciplinas);
        LV_Disciplinas = (ListView) findViewById(R.id.LV_Disciplinas);
        LV_Disciplinas.setAdapter(itemsAdapter);

        LV_Disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tela = new Intent(Aluno_view.this, Disciplina_view.class);
                Bundle materia = new Bundle();
                materia.putString("materia_nome", disciplinas.get(position));
                tela.putExtras(materia);
                startActivity(tela);
            }
        });



    }
}

