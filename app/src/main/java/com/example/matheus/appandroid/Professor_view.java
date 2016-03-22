package com.example.matheus.appandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class Professor_view extends AppCompatActivity {

    private String matricula;
    private ListView LV_Disciplinas;
    private Button BT_AddDisc, BT_RemDisc;
    private DatabaseAdapter BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        Bundle args = getIntent().getExtras();
        matricula = args.getString("matricula");

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Aplica a setinha no actionBar

        BD = new DatabaseAdapter(this);

        BT_AddDisc = (Button) findViewById(R.id.BT_AddDisc);
        BT_RemDisc = (Button) findViewById(R.id.BT_RemDisc);

        BT_AddDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(Professor_view.this, AddDisciplina_view.class);
                Bundle materia = new Bundle();
                materia.putString("matricula", matricula);
                tela.putExtras(materia);
                startActivity(tela);
            }
        });

        BT_RemDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(Professor_view.this, RemDisciplina_view.class);
                Bundle materia = new Bundle();
                materia.putString("matricula", matricula);
                tela.putExtras(materia);
                startActivity(tela);
            }
        });


        final List<String> disciplinas = BD.BuscarDisciplinas() ;

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, disciplinas);
        LV_Disciplinas = (ListView) findViewById(R.id.LV_Disciplinas);
        LV_Disciplinas.setAdapter(itemsAdapter);

        LV_Disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tela = new Intent(Professor_view.this, Disciplina_view.class);
                Bundle materia = new Bundle();
                materia.putString("materia_nome", disciplinas.get(position));
                tela.putExtras(materia);
                startActivity(tela);
            }
        });



    }
}
