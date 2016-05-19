package com.example.matheus.appandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class Aluno_view extends AppCompatActivity {

    private String matricula, nome, sobrenome, durl = "https://frozen-sea-51497.herokuapp.com";
    private ListView LV_Disciplinas;
    private Button BT_AddDisc;
    private JSONObject pessoa;
    private JSONArray retorno;
    private ArrayList<String> ListaDisciplinas;
    private ArrayList<JSONObject> disciplinas;
    private ArrayAdapter<String> Lista;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.M_atualizarCadastro:
                Intent tela = new Intent(Aluno_view.this, Cadastro_view.class);
                Bundle method = new Bundle();
                method.putString("tipo", "PUT");
                method.putString("pessoa", matricula);
                tela.putExtras(method);
                startActivity(tela);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno);

        Bundle args = getIntent().getExtras();
        matricula = args.getString("aluno");
        try {
            pessoa = new JSONObject(matricula);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AlunoTask bgtGet = new AlunoTask(durl+"/aluno_disciplinas.json", RestFullHelper.GET, null);
        bgtGet.execute();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Aplica a setinha no actionBar

        ListaDisciplinas = new ArrayList<>();
        disciplinas = new ArrayList<>();

        try {
            nome = pessoa.getString("nome");
            sobrenome = pessoa.getString("sobrenome");
            if (nome != "none"){
                setTitle("Olá, "+nome+" "+sobrenome);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Lista = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListaDisciplinas);
        LV_Disciplinas = (ListView) findViewById(R.id.LV_Disciplinas);

        LV_Disciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tela = new Intent(Aluno_view.this, Disciplina_view.class);
                Bundle materia = new Bundle();
                try {
                    materia.putString("materia_nome", disciplinas.get(position).getJSONObject("disciplina").toString());
                    Log.e("Materia_nome", disciplinas.get(position).getJSONObject("disciplina").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tela.putExtras(materia);
                startActivity(tela);
            }
        });

        BT_AddDisc = (Button) findViewById(R.id.BT_AddDisc);

        BT_AddDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tela = new Intent(Aluno_view.this, AddDisciplinaAluno_view.class);
                Bundle materia = new Bundle();
                materia.putString("pessoa", matricula);
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
                    retorno = http.getJSONList(url, method, parametros);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return retorno;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Aluno_view.this);
            dialog.setTitle("Carregando");
            dialog.show();
        }

        @Override
        protected void onPostExecute(JSONArray aluno) {

            int i;

            for (i = 0; i < retorno.length(); i++) {
                try {
                    JSONObject obj = retorno.getJSONObject(i);
                    if (obj.getString("aluno_id").equals(pessoa.getString("id"))) {
                        JSONObject disciplina = obj.getJSONObject("disciplina");
                        String nome = disciplina.getString("descricao");
                        ListaDisciplinas.add(nome);
                        disciplinas.add(obj);
                        Log.i("lista", ListaDisciplinas.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            dialog.dismiss();
            LV_Disciplinas.setAdapter(Lista);

        }
    }

}

