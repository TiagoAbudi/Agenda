package com.example.agenda.ui;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.agenda.database.BD;
import com.example.agenda.model.Aluno;
import com.example.agenda.ui.activity.FormularioAlunoActivity;
import com.example.agenda.ui.adapter.ListaAlunosAdapter;

import static com.example.agenda.ui.activity.ContantesActivities.CHAVE_ALUNO;

public class ListaAlunosView {

   private final ListaAlunosAdapter adapter;
   private final Context context;

   public ListaAlunosView(Context context) {
      this.context = context;
      this.adapter = new ListaAlunosAdapter(this.context);
   }

   public void confirmaRemocao(final MenuItem item) {
      new AlertDialog
              .Builder(context)
              .setTitle("Removendo Aluno")
              .setMessage("Tem certeza que deseja remover o aluno?")
              .setPositiveButton("Sim", (dialogInterface, i) -> {
                 AdapterView.AdapterContextMenuInfo menuInfo =
                         (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                 Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
                 remove(alunoEscolhido);
                 Toast toast = Toast.makeText(context, "Aluno removido", Toast.LENGTH_SHORT);
                 toast.show();
              })
              .setNegativeButton("Não", null)
              .show();
   }

   public void abreEdita(final MenuItem item) {
      AdapterView.AdapterContextMenuInfo menuInfo =
              (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
      Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
      abreEditaAluno(alunoEscolhido);
   }

   private void abreEditaAluno(Aluno aluno) {
      Intent vaiParaFormularioActivity = new Intent(
              context, FormularioAlunoActivity.class);
      vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, aluno);
   }

   public void atualizaAlunos() {
      BD bd = new BD(context);
      adapter.atualiza(bd.buscar());
   }

   private void remove(Aluno aluno) {
      BD bd = new BD(context);
      bd.deletar(aluno);
      adapter.remove(aluno);
   }

   public void configuraAdapter(ListView listaDeAlunos) {
      listaDeAlunos.setAdapter(adapter);
   }

}
