package com.example.agenda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.agenda.R;
import com.example.agenda.model.Nota;

public class DetalhesNotasFragment extends Fragment {

   private TextView campoMateria;
   private TextView campoData;
   private ListView listaTopicos;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_detalhes_notas,
              container,
              false);

      campoMateria = view.findViewById(R.id.detalhes_nota_materia);
      campoData = view.findViewById(R.id.detalhes_nota_resultado);
      listaTopicos = view.findViewById(R.id.detalhes_notas);

      Bundle parametros = getArguments();
      if (parametros != null) {
         Nota nota = (Nota) parametros.getSerializable("nota");
         populaCamposCom(nota);
      }

      return view;
   }

   public void populaCamposCom(Nota nota) {
      campoMateria.setText(nota.getMateria());
      campoData.setText(nota.getNota());

      ArrayAdapter<String> adapterTopicos =
              new ArrayAdapter<String>(getContext(),
                      android.R.layout.simple_list_item_1,
                      nota.getProvas());
      listaTopicos.setAdapter(adapterTopicos);
   }
}