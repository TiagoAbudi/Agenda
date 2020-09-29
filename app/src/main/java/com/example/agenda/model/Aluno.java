package com.example.agenda.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Aluno implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long _id;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String data;

    @Ignore
    public Aluno(String nome, String sobrenome, String telefone, String email, String data) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
        this.data = data;
    }

    public Aluno() {

    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + " - " + telefone;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public boolean temIdValido() {
        return _id > 0;
    }

    public boolean naoTemIdValido() {
        return _id == 0;
    }


    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}