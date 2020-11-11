package com.example.agenda.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.agenda.R;
import com.example.agenda.fragment.DetalhesNotasFragment;
import com.example.agenda.fragment.ListaNotasFragment;
import com.example.agenda.model.Nota;

public class NotasActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_notas);

      FragmentManager fragmentManager = getSupportFragmentManager();
      FragmentTransaction tx = fragmentManager.beginTransaction();

      tx.replace(R.id.frame_principal, new ListaNotasFragment());
      if (estaNoModoPaisagem()) {
         tx.replace(R.id.frame_secundario, new DetalhesNotasFragment());
      }

      tx.commit();
   }

   private boolean estaNoModoPaisagem() {
      boolean modoPaisagem;
      Configuration configuration = getResources().getConfiguration();
      if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
         modoPaisagem = true;
         return true;
      } else {
         modoPaisagem = false;
         return false;
      }
   }

   public void selecionaNota(Nota nota) {
      FragmentManager manager = getSupportFragmentManager();
      if (!estaNoModoPaisagem()) {
         FragmentTransaction tx = manager.beginTransaction();

         DetalhesNotasFragment detalhesFragment = new DetalhesNotasFragment();
         Bundle parametros = new Bundle();
         parametros.putSerializable("nota", nota);
         detalhesFragment.setArguments(parametros);

         tx.replace(R.id.frame_principal, detalhesFragment);
         tx.addToBackStack(null);

         tx.commit();
      } else {
         DetalhesNotasFragment detalhesFragment =
                 (DetalhesNotasFragment) manager.findFragmentById(R.id.frame_secundario);
         detalhesFragment.populaCamposCom(nota);
      }
   }
}