package com.example.agenda.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {

   protected static final String NOME_TABELA = "Alunos";
   protected static final String COL_1 = "_id";
   protected static final String COL_2 = "nome";
   protected static final String COL_3 = "sobrenome";
   protected static final String COL_4 = "email";
   protected static final String COL_5 = "telefone";
   protected static final String COL_6 = "data";
   protected static final String COL_7 = "foto";
   protected static final String COL_8 = "cep";
   protected static final String COL_9 = "bairro";
   protected static final String COL_10 = "rua";
   protected static final String COL_11 = "numero";
   protected static final String COL_12 = "estado";
   protected static final String COL_13 = "cidade";
   private static final String NOME_BD = "Agenda";
   private static final int VERSAO_BD = 11;

   public BDCore(Context context) {
      super(context, NOME_BD, null, VERSAO_BD);
   }

   @Override
   public void onCreate(SQLiteDatabase bd) {
      bd.execSQL("CREATE TABLE " + NOME_TABELA + "" +
              "( " + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
              + COL_2 + " TEXT NOT NULL, "
              + COL_3 + " TEXT NOT NULL, "
              + COL_4 + " TEXT NOT NULL, "
              + COL_5 + " TEXT NOT NULL, "
              + COL_6 + " TEXT NOT NULL, "
              + COL_7 + " TEXT NOT NULL, "
              + COL_8 + " TEXT NOT NULL, "
              + COL_9 + " TEXT NOT NULL, "
              + COL_10 + " TEXT NOT NULL, "
              + COL_11 + " TEXT NOT NULL, "
              + COL_12 + " TEXT NOT NULL, "
              + COL_13 + " TEXT NOT NULL)");
   }

   @Override
   public void onUpgrade(SQLiteDatabase bd, int versaoAntiga, int versaoNova) {
      onCreate(bd);
   }

}