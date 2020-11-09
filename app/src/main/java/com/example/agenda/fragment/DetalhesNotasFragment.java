package com.example.agenda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.agenda.R;
import com.example.agenda.model.Nota;

public class DetalhesNotasFragment extends Fragment {

   private TextView campoMateria;
   private TextView campoNota;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_detalhes_notas, container, false);

      campoMateria = view.findViewById(R.id.detalhes_nota_materia);
      campoNota = view.findViewById(R.id.detalhes_nota_resultado);

      Bundle parametros = getArguments();
      if (parametros != null) {
         Nota nota = (Nota) parametros.getSerializable("nota");
         populaCamposCom(nota);
      }

      return view;
   }

   public void populaCamposCom(Nota nota) {
      campoMateria.setText(nota.getMateria());
      campoNota.setText(nota.getNota());
   }
}