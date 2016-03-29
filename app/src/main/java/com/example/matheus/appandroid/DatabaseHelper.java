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
    public static final String TABLE_ALUNO = "Aluno_db";
    public static final String TABLE_PROFESSOR = "Professor_db";
    public static final String TABLE_DISCIPLINA = "Disciplina_db";
    public static final String TABLE_AULA = "Aula_db";
    public static final String TABLE_ALUNO_DISCIPLINA = "Aluno_Disciplina_db";
    public static final String TABLE_PROFESSOR_DISCIPLINA = "Pofessor_Disciplina_db";

    private static final int DATABASE_VERSION = 2;

    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String SOBRENOME = "sobrenome";
    public static final String MATRICULA = "matricula";
    public static final String SENHA = "senha";
    public static final String DESCRICAO = "descricao";


    private static final String TABLE_ALUNO_CREATE = "create table "+ TABLE_ALUNO + "( "+ID+" integer primary key autoincrement, " +
            NOME + " varchar(40) not null, "+SOBRENOME+" varchar(40) not null, "+MATRICULA+" varchar(20) not null" +", "+SENHA+" varchar(20) not null);";

    private static final String TABLE_DISCIPLINA_CREATE = "create table " +TABLE_DISCIPLINA+ "( "+ID+" integer primary key autoincrement, " +
            DESCRICAO + " varchar(40) not null);";

    private static final String TABLE_AULA_CREATE = "create table " +TABLE_AULA+ "( "+ID+" integer primary key autoincrement, " +
            "cod_disciplina int not null, "+DESCRICAO + " varchar(40) not null, data varchar(20) not null);";

    private static final String TABLE_PROFESSOR_CREATE = "create table " + TABLE_PROFESSOR + "( "+ID+" integer primary key autoincrement, " +
            NOME + " varchar(40) not null, "+SOBRENOME+" varchar(40) not null, "+MATRICULA+" varchar(20) not null" +", "+SENHA+" varchar(20) not null);";

    private static final String TABLE_ALUNO_DISCIPLINA_CREATE = "create table " +TABLE_ALUNO_DISCIPLINA+ "( "+ID+" integer primary key autoincrement, " +
            "cod_aluno integer not null, cod_disciplina integer not null);";

    private static final String TABLE_PROFESSOR_DISCIPLINA_CREATE = "create table "+TABLE_PROFESSOR_DISCIPLINA+"( "+ID+" integer primary key autoincrement, " +
            "cod_professor integer not null, cod_disciplina integer not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_ALUNO_CREATE);
        db.execSQL(TABLE_DISCIPLINA_CREATE);
        db.execSQL(TABLE_AULA_CREATE);
        db.execSQL(TABLE_PROFESSOR_CREATE);
        db.execSQL(TABLE_ALUNO_DISCIPLINA_CREATE);
        db.execSQL(TABLE_PROFESSOR_DISCIPLINA_CREATE);
        db.execSQL("Insert into Professor_db(nome, sobrenome, matricula, senha) values('Jorge', 'Ribeiro', '0405', '12345');");
        db.execSQL("Insert into Disciplina_db(descricao) values('Prog. OO Aplicada');");
        db.execSQL("Insert into Professor_Disciplina_db(cod_professor, cod_disciplina) values (0405, 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALUNO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFESSOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AULA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALUNO_DISCIPLINA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFESSOR_DISCIPLINA);
        onCreate(db);

    }
}
