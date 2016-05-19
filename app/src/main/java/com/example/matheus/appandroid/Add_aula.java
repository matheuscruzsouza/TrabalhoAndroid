package com.example.matheus.appandroid;

import android.app.ProgressDialog;
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

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Add_aula extends AppCompatActivity {

    private JSONObject disciplina;
    private EditText data, descricao;
    private Button salvar, cancelar;
    private String durl = "https://frozen-sea-51497.herokuapp.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aula);

        Bundle args = getIntent().getExtras();
        String materia = args.getString("materia");
        try {
            disciplina = new JSONObject(materia);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        data = (EditText) findViewById(R.id.ET_data);
        descricao = (EditText) findViewById(R.id.ET_descricao);

        salvar = (Button) findViewById(R.id.BT_Salvar);
        cancelar = (Button) findViewById(R.id.BT_Cancelar);

        data.setText(getDateTime());

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTask salvar = new AndroidTask(durl+"/aulas.json", RestFullHelper.POST, getParams());
                salvar.execute();
            }
        });



    }

    private JSONObject getParams(){
        JSONObject params = new JSONObject();
        try {
            params.put("data_abertura", data.getText().toString());
            params.put("conteudo", descricao.getText().toString());
            params.put("disciplina_id", disciplina.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
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
            dialog = new ProgressDialog(Add_aula.this);
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
