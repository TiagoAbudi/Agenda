package com.example.agenda.formatter;

public class FormataData {

   public String formata(String dataComFormatacao) {
      return dataComFormatacao
              .replaceAll("([0-9]{2})([0-9]{2})([0-9]{4})", "$1/$2/$3");
   }

   public String remove(String dataComFormatacao) {
      return dataComFormatacao
              .replace("/", "");
   }

}
