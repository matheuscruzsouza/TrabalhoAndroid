package com.example.matheus.appandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RemDisciplina_view extends AppCompatActivity {

    private String matricula, durl = "https://frozen-sea-51497.herokuapp.com";
    private ListView LV_Disciplinas;
    private JSONArray retorno;
    private ArrayList<String> ListaDisciplinas;
    private ArrayList<JSONObject> disciplinas;
    private ArrayAdapter<String> Lista;
    private JSONObject pessoa;
    private Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rem_disciplina);

        Bundle args = getIntent().getExtras();
        matricula = args.getString("matricula");
        try {
            pessoa = new JSONObject(matricula);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        disciplinas = new ArrayList<>();
        ListaDisciplinas = new ArrayList<>();

        AlunoTask bgtGets = new AlunoTask(durl+"/disciplinas.json", RestFullHelper.GET, null);
        bgtGets.execute();

        Lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListaDisciplinas);
        LV_Disciplinas = (ListView) findViewById(R.id.LV_Disciplinas);


        LV_Disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AlunoTask bgtpost = null;
                try {
                    bgtpost = new AlunoTask(disciplinas.get(position).getString("url"), RestFullHelper.DELETAR, null);
                    bgtpost.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

            if (method == "GET") {
                if (retorno == null) {
                    try {
                        retorno = http.getJSON(url, method, parametros).getJSONArray("disciplina");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                if (method == "DELETE") {
                    http.getJSON(url, method, parametros);
                }
            }

            return retorno;
        }

        @Override
        protected void onPreExecute() {
            if ("GET".equals(method)) {
                dialog = new ProgressDialog(RemDisciplina_view.this);
                dialog.setTitle("Carregando");
                dialog.show();
            }
        }

        @Override
        protected void onPostExecute(JSONArray aluno) {

            int i;

            if ("GET".equals(method)) {
                for (i = 0; i < retorno.length(); i++) {
                    try {
                        JSONObject obj = retorno.getJSONObject(i);
                        if (obj.getString("professor_id").equals(pessoa.getString("id"))) {
                            String nome = obj.getString("descricao");
                            disciplinas.add(obj);
                            ListaDisciplinas.add(nome);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                dialog.dismiss();
                LV_Disciplinas.setAdapter(Lista);

            }
            else{
                if("DELETE".equals(method)){
                    Intent tela = new Intent(RemDisciplina_view.this, Professor_view.class);
                    Bundle materia = new Bundle();
                    materia.putString("professor", pessoa.toString());
                    tela.putExtras(materia);
                    startActivity(tela);
                    finish();
                }
            }


        }
    }
}

