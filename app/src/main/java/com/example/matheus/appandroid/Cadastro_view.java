package com.example.matheus.appandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Cadastro_view extends AppCompatActivity {

    private Button BT_save;
    private EditText ET_nome, ET_sobrenome, ET_matricula, ET_senha, ET_conf_senha;
    private TextView TX_Alerta;
    private String texto;
    private DatabaseAdapter BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        BD = new DatabaseAdapter(this);

        BT_save = (Button) findViewById(R.id.BT_save);

        ET_nome = (EditText) findViewById(R.id.ET_Nome);
        ET_sobrenome = (EditText) findViewById(R.id.ET_sobrenome);
        ET_matricula = (EditText) findViewById(R.id.ET_matricula);
        ET_senha = (EditText) findViewById(R.id.ET_senha);
        ET_conf_senha = (EditText) findViewById(R.id.ET_conf_senha);

        TX_Alerta = (TextView) findViewById(R.id.TX_Alerta);

        BT_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ET_senha.getText().toString().equals(ET_conf_senha.getText().toString())){
                    if (ET_nome.getText().toString().equals("")){
                        texto = "O campo nome esta vazio";
                        TX_Alerta.setText(texto);
                    }
                    else{
                        if (ET_sobrenome.getText().toString().equals("")){
                            texto = "O campo sobrenome esta vazio";
                            TX_Alerta.setText(texto);
                        }
                        else{
                            if (ET_matricula.getText().toString().equals("")){
                                texto = "O campo matrícula esta vazio";
                                TX_Alerta.setText(texto);
                            }
                            else{
                                if (ET_senha.getText().toString().equals("")){
                                    texto = "O campo senha esta vazio";
                                    TX_Alerta.setText(texto);
                                }
                                else if (ET_conf_senha.getText().toString().equals("")) {
                                    texto = "O campo confirmar senha esta vazio";
                                    TX_Alerta.setText(texto);
                                } else {
                                    BD.createAluno(ET_nome.getText().toString(), ET_sobrenome.getText().toString(), ET_matricula.getText().toString(), ET_senha.getText().toString());
                                    ET_nome.setText("");
                                    ET_sobrenome.setText("");
                                    ET_matricula.setText("");
                                    ET_senha.setText("");
                                    ET_conf_senha.setText("");
                                    Intent tela = new Intent(Cadastro_view.this, Login_view.class);
                                    startActivity(tela);
                                }
                            }
                        }
                    }
                }
                else{
                    texto = "As senhas não são equivalentes";
                    TX_Alerta.setText(texto);
                }
            }
        });
    }
}
