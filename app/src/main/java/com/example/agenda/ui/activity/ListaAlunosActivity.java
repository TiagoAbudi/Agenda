package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.model.Aluno;
import com.example.agenda.ui.ListaAlunosView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.agenda.ui.activity.ContantesActivities.CHAVE_ALUNO;

public class ListaAlunosActivity extends AppCompatActivity {

   private ListaAlunosView listaAlunosView;

   @Override
   protected void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lista_alunos);
      listaAlunosView = new ListaAlunosView(this);
      configuraFabNovoAluno();
      configuraLista();
   }

   @Override
   public void onCreateContextMenu(ContextMenu menu,
                                   View v, ContextMenu.ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
      getMenuInflater()
              .inflate(R.menu.activity_lista_alunos_menu, menu);
   }

   @Override
   public boolean onContextItemSelected(final MenuItem item) {
      int itemId = item.getItemId();
      if (itemId == R.id.activity_lista_alunos_menu_remover) {
         listaAlunosView.confirmaRemocao(item);
      }
      if (itemId == R.id.activity_lista_alunos_menu_editar) {
         Aluno alunoEscolhido = listaAlunosView.abreEdita(item);
         abreFormularioModoEditaAluno(alunoEscolhido);
      }
      return super.onContextItemSelected(item);
   }

   private void abreFormularioModoEditaAluno(Aluno alunoEscolhido) {
      Intent vaiParaFormularioActivity = new Intent(
              ListaAlunosActivity.this, FormularioAlunoActivity.class);
      vaiParaFormularioActivity.putExtra(CHAVE_ALUNO, alunoEscolhido);
      startActivity(vaiParaFormularioActivity);
   }

   @Override
   protected void onResume() {
      super.onResume();
      listaAlunosView.atualizaAlunos();
   }

//   private void configuraItem(final MenuItem item) {
//      ListaAlunosAdapter adapter = new ListaAlunosAdapter(this);
//      AdapterView.AdapterContextMenuInfo menuInfo =
//              (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//      Aluno alunoEscolhido = adapter.getItem(menuInfo.position);
//      abreFormularioModoEditaAluno(alunoEscolhido);
//   }


   private void configuraListenerDeCliquePorItem(ListView listaDeAlunos) {
      listaDeAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
            Aluno alunoEscolhido = (Aluno) adapterView.getItemAtPosition(posicao);
            abreNotas(alunoEscolhido);
         }
      });
   }

   private void abreNotas(Aluno aluno) {
      Intent vaiParaNotasActivity = new Intent(ListaAlunosActivity.this,
              NotasActivity.class);
      vaiParaNotasActivity.putExtra(CHAVE_ALUNO, aluno);
      startActivity(vaiParaNotasActivity);
   }

   private void configuraFabNovoAluno() {
      FloatingActionButton botaoNovoAluno = findViewById(
              R.id.activity_lista_alunos_fab_novo_aluno);
      botaoNovoAluno.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            abreFormularioModoInsereAluno();
         }
      });
   }

   private void configuraLista() {
      ListView listaDeAlunos = findViewById(R.id.activity_lista_alunos_listview);
      listaAlunosView.configuraAdapter(listaDeAlunos);
      configuraListenerDeCliquePorItem(listaDeAlunos);
      registerForContextMenu(listaDeAlunos);
   }


   private void abreFormularioModoInsereAluno() {
      startActivity(new Intent(this, FormularioAlunoActivity.class));
   }

}
