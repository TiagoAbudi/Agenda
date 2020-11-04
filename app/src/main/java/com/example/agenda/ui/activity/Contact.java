package com.example.agenda.ui.activity;

public class Contact {

   private String uid;
   private String nome;
   private String ultimaMensagem;
   private long timestamp;
   private String foto;

   public String getUid() {
      return uid;
   }

   public void setUid(String uid) {
      this.uid = uid;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public String getUltimaMensagem() {
      return ultimaMensagem;
   }

   public void setUltimaMensagem(String ultimaMensagem) {
      this.ultimaMensagem = ultimaMensagem;
   }

   public long getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public String getFoto() {
      return foto;
   }

   public void setFoto(String foto) {
      this.foto = foto;
   }
}
