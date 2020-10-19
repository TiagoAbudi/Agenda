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
    private String data;
    private String telefone;
    private String email;
    private String foto;
    private String cep;
    private String bairro;
    private String rua;
    private String numero;
    private String estado;
    private String cidade;

    @Ignore
    public Aluno(String nome,
                 String sobrenome,
                 String data,
                 String telefone,
                 String email,
                 String foto,
                 String cep,
                 String bairro,
                 String rua,
                 String numero,
                 String estado,
                 String cidade) {

        this.nome = nome;
        this.sobrenome = sobrenome;
        this.data = data;
        this.telefone = telefone;
        this.email = email;
        this.foto = foto;
        this.cep = cep;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.estado = estado;
        this.cidade = cidade;
    }

    public Aluno() {

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

}
