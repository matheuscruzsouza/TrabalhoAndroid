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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLClientInfoException;

public class AddDisciplina_view extends AppCompatActivity {

    private String matricula, durl = "https://frozen-sea-51497.herokuapp.com";
    private EditText ET_nome;
    private Button BT_Salvar, BT_cancelar;
    private JSONObject pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disciplina);

        Bundle args = getIntent().getExtras();
        matricula = args.getString("matricula");

        try {
            pessoa = new JSONObject(matricula);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        BT_Salvar = (Button) findViewById(R.id.BT_Salvar);
        BT_cancelar = (Button) findViewById(R.id.BT_Cancelar);

        ET_nome = (EditText) findViewById(R.id.ET_nome);

        BT_Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject params = new JSONObject();
                try {
                    params.put("professor_id", pessoa.getString("id"));
                    params.put("descricao", ET_nome.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AndroidTask salvar = new AndroidTask(durl+"/disciplinas.json", RestFullHelper.POST, params);
                salvar.execute();

                Intent tela = new Intent(AddDisciplina_view.this, Professor_view.class);
                Bundle materia = new Bundle();
                materia.putString("professor", pessoa.toString());
                tela.putExtras(materia);
                startActivity(tela);
                finish();

            }
        });

        BT_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class AndroidTask extends AsyncTask<String, String, JSONArray> {

        String url = null;
        String method = null;
        JSONObject parametros = null;

        ProgressDialog dialog;

        public AndroidTask(String url, String method, JSONObject parametros) {
            this.url = url;
            this.method = method;
            this.parametros = parametros;

        }


        @Override
        protected JSONArray doInBackground(String... params) {
            RestFullHelper http = new RestFullHelper();


            JSONObject x = http.getJSON(url, method, parametros);
            Log.e("retorno", x.toString());

            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(AddDisciplina_view.this);
            dialog.setTitle("Carregando");
            //dialog.show();
        }

        @Override
        protected void onPostExecute(JSONArray aluno) {

            //dialog.dismiss();

            finish();

        }
    }
}
