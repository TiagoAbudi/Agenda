package com.example.agenda.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.agenda.R;
import com.example.agenda.model.Prova;
import com.example.agenda.ui.activity.DetalhesProvaActivity;

import java.util.Arrays;
import java.util.List;

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPort = Arrays.asList("Sujeito",
                "Objeto direto",
                "Obejto indireto");
        Prova provaPortugues = new Prova("Português",
                "16/10/2020",
                topicosPort);

        List<String> topicosMat = Arrays.asList("Equações de segundo grau",
                "Bhaskara",
                "Trigonometria");
        Prova provaMatematica = new Prova("Matemática",
                "17/10/2020",
                topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                provas);

        ListView lista = view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova de " + prova, Toast.LENGTH_SHORT).show();
                Intent vaiParaDetalhes = new Intent(getContext(), DetalhesProvaActivity.class);
                vaiParaDetalhes.putExtra("prova", prova);

                startActivity(vaiParaDetalhes);
            }
        });

        return view;
    }
}
