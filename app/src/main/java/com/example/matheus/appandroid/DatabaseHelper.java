package com.example.matheus.appandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

/**
 * Created by Matheus on 18/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "AppAndroid.db";
    public static final String TABLE_NAME = "Aluno_db";
    private static final int DATABASE_VERSION = 3;
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String SOBRENOME = "sobrenome";
    public static final String MATRICULA = "matricula";
    public static final String SENHA = "senha";
    private static final String DATABASE_ALUNO_CREATE = "create table "+ TABLE_NAME + "( "+ID+" integer primary key autoincrement, " +
            "nome text not null, sobrenome text not null, matricula text not null" +", senha text not null);";
    private static final String DATABASE_DISCIPLINA_CREATE = "create table Disciplina_db( "+ID+" integer primary key autoincrement, " +
            "descricao text not null);";
    private static final String DATABASE_PROFESSOR_CREATE = "create table Professor_db( "+ID+" integer primary key autoincrement, " +
            "nome text not null, sobrenome text not null, matricula text not null" +", senha text not null);";
    private static final String DATABASE_ALUNO_DISCIPLINA_CREATE = "create table Aluno_Disciplina_db( "+ID+" integer primary key autoincrement, " +
            "cod_aluno integer not null, cod_disciplina integer not null);";
    private static final String DATABASE_PROFESSOR_DISCIPLINA_CREATE = "create table Professor_Disciplina_db( "+ID+" integer primary key autoincrement, " +
            "cod_professor integer not null, cod_disciplina integer not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_ALUNO_CREATE);
        db.execSQL(DATABASE_DISCIPLINA_CREATE);
        db.execSQL(DATABASE_PROFESSOR_CREATE);
        db.execSQL(DATABASE_ALUNO_DISCIPLINA_CREATE);
        db.execSQL(DATABASE_PROFESSOR_DISCIPLINA_CREATE);
        db.execSQL("Insert into Professor_db(nome, sobrenome, matricula, senha) values('Jorge', 'Ribeiro', '0405', '12345');");
        db.execSQL("Insert into Disciplina_db(descricao) values('Prog. OO Aplicada');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
