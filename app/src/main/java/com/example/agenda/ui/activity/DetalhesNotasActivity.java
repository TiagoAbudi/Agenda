package com.example.agenda.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.model.Nota;

public class DetalhesNotasActivity extends AppCompatActivity {

   private static final String APP_BAR = "Notas";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setTitle(APP_BAR);
      setContentView(R.layout.activity_detalhes_notas);

      Intent intent = getIntent();
      Nota nota = (Nota) intent.getSerializableExtra("nota");

      TextView materia = findViewById(R.id.detalhes_nota_materia);
      TextView notas = findViewById(R.id.detalhes_nota_resultado);
      ListView listaNotas = findViewById(R.id.detalhes_notas);

      materia.setText(nota.getMateria());
      notas.setText(nota.getNota());

      ArrayAdapter<String> adapter =
              new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nota.getProvas());
      listaNotas.setAdapter(adapter);
   }
}