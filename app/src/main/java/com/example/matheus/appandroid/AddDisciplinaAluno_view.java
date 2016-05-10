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
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddDisciplinaAluno_view extends AppCompatActivity {

    private String matricula, durl = "https://frozen-sea-51497.herokuapp.com";
    private ListView LV_Disciplinas;
    private JSONArray retorno;
    private ArrayList<String> ListaDisciplinas;
    private ArrayAdapter<String> Lista;
    private JSONObject pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disciplina_aluno);

        Bundle args = getIntent().getExtras();
        matricula = args.getString("pessoa");
        try {
            pessoa = new JSONObject(matricula);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListaDisciplinas = new ArrayList<>();

        AlunoTask bgtGets = new AlunoTask(durl+"/disciplinas.json", RestFullHelper.GET, null);
        bgtGets.execute();

        Lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListaDisciplinas);
        LV_Disciplinas = (ListView) findViewById(R.id.LV_Disciplinas);


        LV_Disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                JSONObject param = getParams(retorno.optJSONObject(position));

                AlunoTask bgtpost = new AlunoTask(durl+"/aluno_disciplinas.json", RestFullHelper.POST, param);
                bgtpost.execute();

            }
        });
    }

    public JSONObject getParams(JSONObject disciplina){
        JSONObject params = new JSONObject();
        try {
            params.put("aluno_id", pessoa.getString("id"));
            params.put("disciplina_id", disciplina.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
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
                if (method == "POST") {
                    JSONObject x = http.getJSON(url, method, parametros);
                    Log.e("retorno", x.toString());
                }
            }

            return retorno;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(AddDisciplinaAluno_view.this);
            dialog.setTitle("Carregando");
            dialog.show();
        }

        @Override
        protected void onPostExecute(JSONArray aluno) {

            int i;

            if (method == "GET") {
                for (i = 0; i < retorno.length(); i++) {
                    try {
                        JSONObject obj = retorno.getJSONObject(i);
                        String nome = obj.getString("descricao");
                        ListaDisciplinas.add(nome);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            else{
                if(method == "POST"){
                    Intent tela = new Intent(AddDisciplinaAluno_view.this, Aluno_view.class);
                    Bundle materia = new Bundle();
                    materia.putString("aluno", matricula);
                    tela.putExtras(materia);
                    startActivity(tela);
                    finish();
                }
            }

            dialog.dismiss();
            LV_Disciplinas.setAdapter(Lista);

        }
    }
}
