package com.example.agenda.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

import static com.example.agenda.database.BDCore.COL_1;
import static com.example.agenda.database.BDCore.COL_10;
import static com.example.agenda.database.BDCore.COL_11;
import static com.example.agenda.database.BDCore.COL_12;
import static com.example.agenda.database.BDCore.COL_13;
import static com.example.agenda.database.BDCore.COL_2;
import static com.example.agenda.database.BDCore.COL_3;
import static com.example.agenda.database.BDCore.COL_4;
import static com.example.agenda.database.BDCore.COL_5;
import static com.example.agenda.database.BDCore.COL_6;
import static com.example.agenda.database.BDCore.COL_7;
import static com.example.agenda.database.BDCore.COL_8;
import static com.example.agenda.database.BDCore.COL_9;
import static com.example.agenda.database.BDCore.NOME_TABELA;

public class BD {
    private final SQLiteDatabase bd;

    public BD(Context context) {
        BDCore auxBd = new BDCore(context);
        bd = auxBd.getWritableDatabase();
    }

    public void inserir(Aluno aluno) {
        ContentValues valores = new ContentValues();
        valores.put(COL_2, aluno.getNome());
        valores.put(COL_3, aluno.getSobrenome());
        valores.put(COL_4, aluno.getEmail());
        valores.put(COL_5, aluno.getTelefone());
        valores.put(COL_6, aluno.getData());
        valores.put(COL_7, (aluno.getFoto() == null) ? "" : aluno.getFoto());
        valores.put(COL_8, aluno.getCep());
        valores.put(COL_9, aluno.getBairro());
        valores.put(COL_10, aluno.getRua());
        valores.put(COL_11, aluno.getNumero());
        valores.put(COL_12, aluno.getEstado());
        valores.put(COL_13, aluno.getCidade());
        bd.insert(NOME_TABELA, null, valores);
    }

    public void atualizar(Aluno aluno) {
        ContentValues valores = new ContentValues();
        valores.put("_id", aluno.getId());
        valores.put("nome", aluno.getNome());
        valores.put("sobrenome", aluno.getSobrenome());
        valores.put("data", aluno.getData());
        valores.put("telefone", aluno.getTelefone());
        valores.put("email", aluno.getEmail());
        valores.put("foto", (aluno.getFoto() == null) ? "" : aluno.getFoto());
        valores.put("cep", aluno.getCep());
        valores.put("bairro", aluno.getBairro());
        valores.put("rua", aluno.getRua());
        valores.put("numero", aluno.getNumero());
        valores.put("estado", aluno.getEstado());
        valores.put("cidade", aluno.getCidade());

        bd.update(NOME_TABELA,
                valores,
                "_id = ?",
                new String[]{"" + aluno.getId()});
    }

    public void deletar(Aluno aluno) {
        bd.delete(NOME_TABELA,
                "_id =" + aluno.getId(),
                null);
    }

    public List<Aluno> buscar() {

        List<Aluno> list = new ArrayList<Aluno>();
        String[] colunas = new String[]{COL_1,
                COL_2,
                COL_3,
                COL_4,
                COL_5,
                COL_6,
                COL_7,
                COL_8,
                COL_9,
                COL_10,
                COL_11,
                COL_12,
                COL_13};

        Cursor cursor = bd.query(NOME_TABELA,
                colunas,
                null,
                null,
                null,
                null,
                "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Aluno aluno = new Aluno();
                aluno.setId(cursor.getLong(0));
                aluno.setNome(cursor.getString(1));
                aluno.setSobrenome(cursor.getString(2));
                aluno.setEmail(cursor.getString(3));
                aluno.setTelefone(cursor.getString(4));
                aluno.setData(cursor.getString(5));
                aluno.setFoto(cursor.getString(6));
                aluno.setCep(cursor.getString(7));
                aluno.setBairro(cursor.getString(8));
                aluno.setRua(cursor.getString(9));
                aluno.setNumero(cursor.getString(10));
                aluno.setEstado(cursor.getString(11));
                aluno.setCidade(cursor.getString(12));
                list.add(aluno);
            } while (cursor.moveToNext());
        }
        return (list);
    }

}
