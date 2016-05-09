package com.example.matheus.appandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private String matricula, durl = "https://frozen-sea-51497.herokuapp.com/disciplinas.json";
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

        AlunoTask bgtGets = new AlunoTask(durl, RestFullHelper.GET, null);
        bgtGets.execute();

        Lista = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ListaDisciplinas);
        LV_Disciplinas = (ListView) findViewById(R.id.LV_Disciplinas);


        LV_Disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //BD.adicionarDisciplina(matricula, ListaDisciplinas.get(position));
                Intent tela = new Intent(AddDisciplinaAluno_view.this, Aluno_view.class);
                Bundle materia = new Bundle();
                materia.putString("aluno", matricula);
                tela.putExtras(materia);
                startActivity(tela);
                finish();
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


            try {
                retorno = http.getJSONList(url, method, parametros);
            } catch (JSONException e) {
                e.printStackTrace();
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

            for (i = 0; i<retorno.length(); i++){
                try {
                    JSONObject obj = retorno.getJSONObject(i);
                    String nome = obj.getString("descricao");
                    ListaDisciplinas.add(nome);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            dialog.dismiss();
            LV_Disciplinas.setAdapter(Lista);

        }
    }
}
