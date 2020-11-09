package com.example.agenda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.agenda.R;
import com.example.agenda.model.Nota;
import com.example.agenda.ui.activity.NotasActivity;

import java.util.Arrays;
import java.util.List;

public class ListaNotasFragment extends Fragment {

   @Nullable
   @Override
   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_lista_notas, container, false);

      List<String> provasPortugues = Arrays.asList("Prova 1° trimestre = 2,0",
              "Prova 2° trimestre = 2,0",
              "Prova 3° trimestre = 2,0",
              "Trabalho 1° semsetre = 2,0",
              "Trabalho 2° semestre = 2,0");
      Nota notaPortugues = new Nota("Portugues", "10,0", provasPortugues);

      List<String> provasMatematica = Arrays.asList("Prova 1° trimestre = 2,0",
              "Prova 2° trimestre = 2,0",
              "Prova 3° trimestre = 2,0",
              "Trabalho 1° semsetre = 2,0",
              "Trabalho 2° semestre = 2,0");
      Nota notaMatematica = new Nota("Matematica", "27/05/2016", provasMatematica);

      List<Nota> notas = Arrays.asList(notaPortugues, notaMatematica);

      ArrayAdapter<Nota> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, notas);

      ListView lista = view.findViewById(R.id.notas_lista);
      lista.setAdapter(adapter);

      lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Nota nota = (Nota) parent.getItemAtPosition(position);
            Toast.makeText(getContext(), "Clicou nas notas de " + nota, Toast.LENGTH_SHORT).show();

            NotasActivity notasActivity = (NotasActivity) getActivity();
            notasActivity.selecionaNota(nota);
         }
      });

      return view;
   }
}
