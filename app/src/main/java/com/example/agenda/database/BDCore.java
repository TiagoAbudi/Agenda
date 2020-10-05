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
    private static final String NOME_BD = "Agenda";
    private static final int VERSAO_BD = 8;

    public BDCore(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE " + NOME_TABELA + "(" + COL_1 + " integer PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " text NOT NULL DEFAULT 'nome', "
                + COL_3 + " text NOT NULL DEFAULT 'sobrenome', "
                + COL_4 + " text NOT NULL DEFAULT 'email', "
                + COL_5 + " text NOT NULL DEFAULT 'telefone', "
                + COL_6 + "date NOT NULL DEFAULT '01/01/2020',"
                + COL_7 + " text NOT NULL  DEFAULT 'foto');"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versaoAntiga, int versaoNova) {
        bd.execSQL("DROP TABLE Alnuos");
    }
}
