package com.example.matheus.appandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_view extends AppCompatActivity {

    private Button BT_PTela, BT_cadastrar;
    private EditText ET_Matricula, ET_Senha;
    private TextView Alerta;
    private String texto;
    String durl = "https://frozen-sea-51497.herokuapp.com/alunos.json";
    JSONArray retorno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

<<<<<<< HEAD
        retorno = null;

=======
>>>>>>> ecb04366956e10b66bc580cbe78faf9fc8f585ac
        BT_PTela = (Button) findViewById(R.id.BT_PTela);
        BT_cadastrar = (Button) findViewById(R.id.BT_Cadastrar);
        ET_Matricula = (EditText) findViewById(R.id.ET_matricula);
        ET_Senha = (EditText) findViewById(R.id.ET_senha);
        Alerta = (TextView) findViewById(R.id.TX_Alert);

        BT_PTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlunoTask login = new AlunoTask(durl, RestFullHelper.GET, null);
                login.execute();
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

    public class AlunoTask extends AsyncTask<String, String, JSONArray> {
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
        protected JSONArray doInBackground(String... params) {
            RestFullHelper http = new RestFullHelper();

<<<<<<< HEAD
            if (retorno == null) {
                try {
                    retorno = http.getJSON(url, method, parametros).getJSONArray("aluno");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
=======
            try {
                retorno = http.getJSONList(url, method, parametros);
            } catch (JSONException e) {
                e.printStackTrace();
>>>>>>> ecb04366956e10b66bc580cbe78faf9fc8f585ac
            }
            return retorno;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Login_view.this);
            dialog.setTitle("Carregando");
            dialog.show();
        }

        @Override
        protected void onPostExecute(JSONArray aluno) {

            int i;
            Boolean BAluno = false;
            JSONObject pessoa = null;

            for (i = 0; i < retorno.length(); i++) {
                try {
                    JSONObject obj = retorno.getJSONObject(i);
                    if (ET_Matricula.getText().toString().equals(obj.getString("matricula"))){
                        if (ET_Senha.getText().toString().equals(obj.getString("senha"))){
                            BAluno = true;
                            pessoa = obj;
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            dialog.dismiss();

            if (BAluno == true) {
                Intent tela = new Intent(Login_view.this, Aluno_view.class);
                Bundle materia = new Bundle();
                materia.putString("aluno", pessoa.toString());
                tela.putExtras(materia);
                startActivity(tela);
                finish();
            } else {
                if (ET_Senha.getText().toString().equals("professor")) {
                    Intent tela = new Intent(Login_view.this, Professor_view.class);
                    Bundle materia = new Bundle();
                    materia.putString("matricula", ET_Matricula.getText().toString());
                    tela.putExtras(materia);
                    startActivity(tela);
                    finish();
                } else {
                    texto = "A matrícula ou senha está incorreta!";
                    Alerta.setText(texto);
                }
            }

        }
    }

}
