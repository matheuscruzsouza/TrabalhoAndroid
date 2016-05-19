package com.example.matheus.appandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Cadastro_view extends AppCompatActivity {

    private Button BT_save;
    private EditText ET_nome, ET_sobrenome, ET_matricula, ET_senha, ET_conf_senha;
    private TextView TX_Alerta;
    private String texto, metodo, matricula;
    private JSONObject pessoa;
    private String durl = "http://frozen-sea-51497.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        BT_save = (Button) findViewById(R.id.BT_save);

        ET_nome = (EditText) findViewById(R.id.ET_Nome);
        ET_sobrenome = (EditText) findViewById(R.id.ET_sobrenome);
        ET_matricula = (EditText) findViewById(R.id.ET_matricula);
        ET_senha = (EditText) findViewById(R.id.ET_senha);
        ET_conf_senha = (EditText) findViewById(R.id.ET_conf_senha);

        TX_Alerta = (TextView) findViewById(R.id.TX_Alerta);

        Bundle args = getIntent().getExtras();
        metodo = args.getString("tipo");
        if ("PUT".equals(metodo)) {
            matricula = args.getString("pessoa");
            try {
                pessoa = new JSONObject(matricula);
                Log.e("Pessoa", pessoa.toString());
                ET_nome.setText(pessoa.getString("nome"));
                ET_sobrenome.setText(pessoa.getString("sobrenome"));
                ET_matricula.setText(pessoa.getString("matricula"));
                ET_senha.setText(pessoa.getString("senha"));
                //ET_senha.setFocusable(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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

                                    if ("POST".equals(metodo)) {
                                        JSONObject params = getParams();

                                        AlunoTask salvar = new AlunoTask(durl + "/alunos.json", RestFullHelper.POST, params);
                                        salvar.execute();
                                    }
                                    else{
                                        if ("PUT".equals(metodo)) {
                                            JSONObject params = getParams();

                                            try {
                                                AlunoTask salvar = new AlunoTask(pessoa.getString("url"), RestFullHelper.PUT, params);
                                                salvar.execute();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }
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

    private JSONObject getParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("matricula", ET_matricula.getText().toString());
            params.put("nome", ET_nome.getText().toString());
            params.put("sobrenome", ET_sobrenome.getText().toString());
            params.put("senha", ET_senha.getText().toString());

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return params;
    }


    public class AlunoTask extends AsyncTask<String, String, JSONObject> {

        String url = null;
        String method = null;
        JSONObject parametros = null;

        ProgressDialog dialog;

        public AlunoTask(String url, String method, JSONObject parametros) {
            this.url = url;
            this.method = method;
            this.parametros = parametros;

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            RestFullHelper http = new RestFullHelper();

            return http.getJSON(url, method, parametros);
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Cadastro_view.this);
            dialog.show();
        }

        @Override
        protected void onPostExecute(JSONObject aluno) {

            dialog.dismiss();
            if (method == "POST") {
                Intent tela = new Intent(Cadastro_view.this, Login_view.class);
                startActivity(tela);
                finish();
            }
            else{
                if (method == "PUT"){
                    finish();
                }
            }
        }
    }
}
