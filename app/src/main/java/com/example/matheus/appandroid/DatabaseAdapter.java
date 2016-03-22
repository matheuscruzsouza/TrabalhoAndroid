package com.example.matheus.appandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 18/03/2016.
 */
public class DatabaseAdapter {

    private static SQLiteDatabase database_writer, database_reader;
    private DatabaseHelper dbHelper;
    private static String[] allColumns = { DatabaseHelper.ID, DatabaseHelper.NOME, DatabaseHelper.SOBRENOME,
            DatabaseHelper.MATRICULA, DatabaseHelper.SENHA};

    public DatabaseAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
        database_writer = dbHelper.getWritableDatabase();
        database_reader = dbHelper.getReadableDatabase();
    }

    public void createAluno(String nome, String sobrenome, String matricula, String senha) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NOME, nome);
        values.put(DatabaseHelper.SOBRENOME, sobrenome);
        values.put(DatabaseHelper.MATRICULA, matricula);
        values.put(DatabaseHelper.SENHA, senha);
        database_writer.insert(DatabaseHelper.TABLE_NAME, null, values);
        Log.i("Deb", "Inseriu corretamente!");

    }

    public void EliminaAluno (int idContacto){
        database_writer.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.ID + " = " + idContacto,
                null);
    }

    public static Cursor getAlunoMatricula(String matricula){
        Cursor cursor = database_reader.rawQuery("select nome,matricula,senha from " + DatabaseHelper.TABLE_NAME + " where matricula == '" + matricula + "';", null);

        if (cursor.getCount()>0){
            cursor.moveToFirst();
            return cursor;
        }

        return cursor;
    }

    public static boolean loginAluno(String matricula, String senha){
        Cursor alunos = DatabaseAdapter.getAlunoMatricula(matricula);

        if (alunos.getCount()>0){
            String[] mat = {alunos.getString(0), alunos.getString(1), alunos.getString(2)};
            Log.i("Deb", mat[0] + mat[1] + mat[2]);

            alunos.moveToFirst();

            do{
                if ((matricula.equals(alunos.getString(1))) && (senha.equals(alunos.getString(2)))){
                    return true;
                }
            }while(alunos.moveToNext());
        }
        return false;

    }


    public void createDisciplina(String descricao, String matricula) {
        ContentValues values = new ContentValues();
        values.put("descricao", descricao);
        database_writer.insert("Disciplina_db", null, values);

        Cursor disciplina = database_reader.rawQuery("select _id, descricao from Disciplina_db where descricao == '" + descricao.toString() +"';", null);
        Cursor professor = database_reader.rawQuery("select _id, matricula from Professor_db where matricula == '" + matricula.toString() +"';", null);

        if ((disciplina.getCount()>0) && (professor.getCount()>0)){
            disciplina.moveToFirst();
            professor.moveToFirst();
            ContentValues valores = new ContentValues();
            Log.e("Deb", disciplina.getInt(0)+" "+ professor.getInt(0));
            valores.put("cod_disciplina", disciplina.getInt(0));
            valores.put("cod_professor", professor.getInt(0));
            database_writer.insert("Professor_Disciplina_db", null, valores);
        }

        Log.i("Deb", "Inseriu Disciplina corretamente!");

    }

    public void removerDisciplina(String descricao, String matricula){
        Cursor disciplina = database_reader.rawQuery("select _id, descricao from Disciplina_db where descricao == '" + descricao.toString() +"';", null);
        Cursor professor = database_reader.rawQuery("select _id, matricula from Professor_db where matricula == '" + matricula.toString() +"';", null);
        if ((disciplina.getCount()>0) && (professor.getCount()>0)){
            database_writer.delete("Professor_Disciplina_db", "cod_disciplina == '" + disciplina.getString(0) + "' and cod_professor == '" + professor.getString(0) + "';", null);
        }
    }

    public List BuscarDisciplinas(){
        List<String> disciplinas = new ArrayList<>();
        Cursor cursor = database_reader.rawQuery("select descricao from Disciplina_db;", null);

        if (cursor.getCount()>0) {
            cursor.moveToFirst();

            do {
                disciplinas.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        return disciplinas;
    }

    public List BuscarDisciplinasAluno(String matricula){
        List<String> disciplinas = new ArrayList<>();


        Cursor disciplina = database_reader.rawQuery("select D.descricao from Aluno_db A inner join Aluno_Disciplina_db AD Left Join Disciplina_db D where A._id == AD.cod_aluno and D._id == AD.cod_disciplina;", null);

        if (disciplina.getCount()>0) {
            disciplina.moveToFirst();

            do {
                disciplinas.add(disciplina.getString(0));
            }while(disciplina.moveToNext());
        }

        return disciplinas;
    }

    public void adicionarDisciplina(String matricula, String descricao){
        Cursor aluno = database_reader.rawQuery("Select _id from Aluno_db where matricula == '"+matricula+"';", null);
        Cursor disciplina = database_reader.rawQuery("Select _id from Disciplina_db where descricao == '"+descricao+"';", null);

        if ((aluno.getCount()>0) && (disciplina.getCount()>0)){
            aluno.moveToFirst();
            disciplina.moveToFirst();

            ContentValues valores = new ContentValues();
            valores.put("cod_aluno", aluno.getInt(0));
            valores.put("cod_disciplina", disciplina.getInt(0));

            database_writer.insert("Aluno_Disciplina_db", null, valores);

        }

    }


}
