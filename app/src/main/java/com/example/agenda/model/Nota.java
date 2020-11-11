package com.example.agenda.model;

import java.io.Serializable;
import java.util.List;

public class Nota implements Serializable {

   private String materia;
   private String nota;
   private List<String> provas;

   public Nota(String materia, String nota, List<String> provas) {
      this.materia = materia;
      this.nota = nota;
      this.provas = provas;
   }

   public String getMateria() {
      return materia;
   }

   public void setMateria(String materia) {
      this.materia = materia;
   }

   public String getNota() {
      return nota;
   }

   public void setNota(String nota) {
      this.nota = nota;
   }

   public List<String> getProvas() {
      return provas;
   }

   public void setProvas(List<String> provas) {
      this.provas = provas;
   }

   @Override
   public String toString() {
      return this.materia;
   }

}
