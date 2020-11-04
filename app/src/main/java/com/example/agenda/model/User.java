package com.example.agenda.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

   public static final Creator<User> CREATOR = new Creator<User>() {
      @Override
      public User createFromParcel(Parcel in) {
         return new User(in);
      }

      @Override
      public User[] newArray(int size) {
         return new User[size];
      }
   };
   private String uid;
   private String nome;
   private String sobrenome;
   private String telefone;
   private String foto;
   private String token;
   private boolean online;

   public User() {
   }

   public User(String uid, String nome, String sobrenome, String telefone, String foto) {
      this.uid = uid;
      this.nome = nome;
      this.sobrenome = sobrenome;
      this.telefone = telefone;
      this.foto = foto;
   }

   protected User(Parcel in) {
      uid = in.readString();
      nome = in.readString();
      sobrenome = in.readString();
      telefone = in.readString();
      foto = in.readString();
      token = in.readString();
      online = in.readInt() == 1;
   }

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

   @Override
   public int describeContents() {
      return 0;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public boolean isOnline() {
      return online;
   }

   public void setOnline(boolean online) {
      this.online = online;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(uid);
      dest.writeString(nome);
      dest.writeString(sobrenome);
      dest.writeString(telefone);
      dest.writeString(foto);
      dest.writeString(token);
      dest.writeInt(online ? 1 : 0);
   }
}
