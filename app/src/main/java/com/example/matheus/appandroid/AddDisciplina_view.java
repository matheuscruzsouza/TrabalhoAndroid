package com.example.matheus.appandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.SQLClientInfoException;

public class AddDisciplina_view extends AppCompatActivity {

    private String matricula;
    private EditText ET_nome;
    private Button BT_Salvar, BT_cancelar;
    private DatabaseAdapter BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disciplina);

        Bundle args = getIntent().getExtras();
        matricula = args.getString("matricula");

        BD = new DatabaseAdapter(this);

        BT_Salvar = (Button) findViewById(R.id.BT_Salvar);
        BT_cancelar = (Button) findViewById(R.id.BT_Cancelar);

        ET_nome = (EditText) findViewById(R.id.ET_nome);

        BT_Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD.createDisciplina(ET_nome.getText().toString(), matricula);
                ET_nome.setText("");
                Intent tela = new Intent(AddDisciplina_view.this, Professor_view.class);
                Bundle materia = new Bundle();
                materia.putString("matricula", matricula);
                tela.putExtras(materia);
                startActivity(tela);
                finish();
            }
        });
    }
}
