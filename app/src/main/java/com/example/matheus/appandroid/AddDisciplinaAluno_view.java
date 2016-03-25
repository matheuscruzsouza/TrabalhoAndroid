package com.example.matheus.appandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class AddDisciplinaAluno_view extends AppCompatActivity {

    private String matricula;
    private ListView LV_Disciplinas;
    private DatabaseAdapter BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disciplina_aluno);

        Bundle args = getIntent().getExtras();
        matricula = args.getString("matricula");

        BD = new DatabaseAdapter(this);

        final List<String> disciplinas = BD.BuscarDisciplinas() ;

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, disciplinas);
        LV_Disciplinas = (ListView) findViewById(R.id.LV_Disciplinas);
        LV_Disciplinas.setAdapter(itemsAdapter);

        LV_Disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BD.adicionarDisciplina(matricula, disciplinas.get(position));
                Intent tela = new Intent(AddDisciplinaAluno_view.this, Aluno_view.class);
                Bundle materia = new Bundle();
                materia.putString("matricula", matricula);
                tela.putExtras(materia);
                startActivity(tela);
                finish();
            }
        });
    }
}
