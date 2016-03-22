package com.example.matheus.appandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login_view extends AppCompatActivity {

    private Button BT_PTela, BT_cadastrar;
    private EditText ET_Matricula, ET_Senha;
    private TextView Alerta;
    private String texto;
    private DatabaseAdapter BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        BD = new DatabaseAdapter(this);
        BT_PTela = (Button) findViewById(R.id.BT_PTela);
        BT_cadastrar = (Button) findViewById(R.id.BT_Cadastrar);
        ET_Matricula = (EditText) findViewById(R.id.ET_matricula);
        ET_Senha = (EditText) findViewById(R.id.ET_senha);
        Alerta = (TextView) findViewById(R.id.TX_Alert);

        BT_PTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BD.loginAluno(ET_Matricula.getText().toString(), ET_Senha.getText().toString()) == true) {
                    Intent tela = new Intent(Login_view.this, Aluno_view.class);
                    Bundle materia = new Bundle();
                    materia.putString("matricula", ET_Matricula.getText().toString());
                    tela.putExtras(materia);
                    startActivity(tela);
                }
                else{
                    if(ET_Senha.getText().toString().equals("professor")) {
                        Intent tela = new Intent(Login_view.this, Professor_view.class);
                        Bundle materia = new Bundle();
                        materia.putString("matricula", ET_Matricula.getText().toString());
                        tela.putExtras(materia);
                        startActivity(tela);
                    }
                    else{
                        texto = "A matrícula ou senha está incorreta!";
                        Alerta.setText(texto);
                    }
                }

            }
        });

        BT_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(Login_view.this, Cadastro_view.class);
                startActivity(tela);
            }
        });
    }
}
