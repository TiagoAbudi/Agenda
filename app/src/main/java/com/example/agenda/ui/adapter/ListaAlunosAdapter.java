package com.example.agenda.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.model.Aluno;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosAdapter extends BaseAdapter {

   private final List<Aluno> alunoLista = new ArrayList<>();
   private final Context context;

   public ListaAlunosAdapter(Context context) {
      this.context = context;
   }

   @Override
   public int getCount() {
      return alunoLista.size();
   }

   @Override
   public Aluno getItem(int posicao) {
      return alunoLista.get(posicao);
   }

   @Override
   public long getItemId(int posicao) {
      return alunoLista.get(posicao).getId();
   }

   @Override
   public View getView(int posicao, View view, ViewGroup viewGroup) {
      View viewCriada = criaView(viewGroup);
      Aluno alunoDevolvido = alunoLista.get(posicao);
      vincula(viewCriada, alunoDevolvido);
      return viewCriada;
   }

   private void vincula(View view, Aluno alunos) {
      TextView nome = view.findViewById(R.id.item_aluno_nome);
      nome.setText(alunos.getNomeCompleto());

      TextView telefone = view.findViewById(R.id.item_aluno_telefone);
      telefone.setText(alunos.getTelefone());

      setaFotoDePerfil(view, alunos);
   }

   private void setaFotoDePerfil(View view, Aluno alunos) {
      if (!alunos.getFoto().equals("")) {
         ImageView foto = view.findViewById(R.id.foto_perfil);
         Bitmap bitmap = BitmapFactory.decodeFile(alunos.getFoto());
         foto.setImageBitmap(bitmap);
      }
   }

   private View criaView(ViewGroup viewGroup) {
      return LayoutInflater
              .from(context)
              .inflate(R.layout.item_aluno, viewGroup, false);
   }

   public void atualiza(List<Aluno> alunos) {
      this.alunoLista.clear();
      this.alunoLista.addAll(alunos);
      notifyDataSetChanged();
   }

   public void remove(Aluno aluno) {
      alunoLista.remove(aluno);
      notifyDataSetChanged();
   }
}
