package com.example.matheus.appandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Disciplina_view extends AppCompatActivity {

    private ListView Lista_aulas;
    private String id, durl = "https://frozen-sea-51497.herokuapp.com";
    private JSONObject materia;
    private JSONArray retorno;
    private ArrayList<String> ListaAulas;
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplina);

        ListaAulas = new ArrayList<>();

        Bundle args = getIntent().getExtras();
        String SMateria = args.getString("materia_nome");

        try {
            materia = new JSONObject(SMateria);
            id = materia.getString("id");
            setTitle("Disciplina de " + (materia.getString("descricao")).toLowerCase());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Lista_aulas = (ListView) findViewById(R.id.LV_aulas_disc);

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaAulas);

        AlunoTask bgtGet = new AlunoTask(durl+"/disciplinas/"+id+".json", RestFullHelper.GET, null);
        bgtGet.execute();

        Lista_aulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tela = new Intent(Disciplina_view.this, Aula_view.class);
                Bundle materia = new Bundle();
                try {
                    materia.putString("aula", retorno.get(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tela.putExtras(materia);
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

            if (retorno == null) {
                try {
                    retorno = http.getJSON(url, method, parametros).getJSONArray("aulas");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return retorno;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Disciplina_view.this);
            dialog.setTitle("Carregando");
            dialog.show();
        }

        @Override
        protected void onPostExecute(JSONArray aluno) {

            int i;

            for (i = 0; i < retorno.length(); i++) {
                try {
                    JSONObject obj = retorno.getJSONObject(i);
                    String nome = obj.getString("conteudo");
                    ListaAulas.add(nome);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            dialog.dismiss();
            Lista_aulas.setAdapter(itemsAdapter);

        }
    }


}