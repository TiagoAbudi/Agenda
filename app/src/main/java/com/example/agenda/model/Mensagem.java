package com.example.agenda.model;

import com.google.gson.annotations.SerializedName;

public class Mensagem {

   @SerializedName("text")
   private String texto;
   private int id;

   public Mensagem(int id, String texto) {
      this.id = id;
      this.texto = texto;
   }

   public String getTexto() {
      return texto;
   }

   public int getId() {
      return id;
   }
}
