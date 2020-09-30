package com.example.agenda.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDCore extends SQLiteOpenHelper {

    protected static final String NOME_TABELA = "Alunos";
    protected static final String COL_1 = "_id";
    protected static final String COL_2 = "nome";
    protected static final String COL_3 = "sobrenome";
    protected static final String COL_4 = "data";
    protected static final String COL_5 = "telefone";
    protected static final String COL_6 = "email";
    private static final String NOME_BD = "Agenda";
    private static final int VERSAO_BD = 5;

    public BDCore(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        onUpgrade(bd, 4, VERSAO_BD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int versaoAntiga, int versaoNova) {

        if (versaoAntiga < 5) {
            bd.execSQL("CREATE TABLE " + NOME_TABELA + "(" + COL_1 + " integer PRIMARY KEY AUTOINCREMENT, "
                    + COL_2 + " text NOT NULL, "
                    + COL_3 + " text NOT NULL, "
                    + COL_4 + " text NOT NULL, "
                    + COL_5 + " text NOT NULL);"
            );
        }
        if (versaoAntiga < 6) {
            bd.execSQL("ALTER TABLE " + NOME_TABELA + " ADD COLUMN "
                    + COL_4 + " data NOT NULL default '01/01/2020'");
        }
    }
}
