package com.example.agenda.model;

public class Usuario {

   String uuid;
   String nome;
   String sobrenome;
   String telefone;
   String foto;

   public Usuario(String uuid, String nome, String sobrenome, String telefone, String foto) {
      this.uuid = uuid;
      this.nome = nome;
      this.sobrenome = sobrenome;
      this.telefone = telefone;
      this.foto = foto;
   }

   public String getUuid() {
      return uuid;
   }

   public void setUuid(String uuid) {
      this.uuid = uuid;
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

   public String getTelefone() {
      return telefone;
   }

   public void setTelefone(String telefone) {
      this.telefone = telefone;
   }

   public String getFoto() {
      return foto;
   }

   public void setFoto(String foto) {
      this.foto = foto;
   }
}
