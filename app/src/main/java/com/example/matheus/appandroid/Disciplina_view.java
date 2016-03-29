package com.example.matheus.appandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.util.List;

public class Disciplina_view extends AppCompatActivity {

    private ListView Lista_aulas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        Bundle args = getIntent().getExtras();
        String materia = args.getString("materia_nome");

        setTitle("Disciplina de " + (materia).toLowerCase());

        Lista_aulas = (ListView) findViewById(R.id.LV_aulas_disc);

        final String[] disciplinas = {"Aula dia XX/XX/2016"};

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, disciplinas);
        Lista_aulas.setAdapter(itemsAdapter);

        Lista_aulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tela = new Intent(Disciplina_view.this, Aula_view.class);
                Bundle materia = new Bundle();
                materia.putString("aula_nome", disciplinas[position]);
                tela.putExtras(materia);
                startActivity(tela);
            }
        });

    }
}