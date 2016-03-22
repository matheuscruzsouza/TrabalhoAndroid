package com.example.matheus.appandroid;

import android.util.Log;

/**
 * Created by Matheus on 19/03/2016.
 */
public class Aluno {
    String matricula, nome, sobrenome, senha;

    public Aluno(long id, String nome, String sobrenome, String matricula, String senha) {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void imprimirAluno(){
        Log.i("Deb",this.getNome());
        Log.i("Deb",this.getSobrenome());
        Log.i("Deb",this.getMatricula());
    }
}
