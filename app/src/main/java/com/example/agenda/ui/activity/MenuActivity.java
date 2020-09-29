package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;

public class MenuActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Menu";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        setTitle(TITULO_APPBAR);
        botaoVaiParaALista();
        vaiParaOFormulario();
    }

    private void botaoVaiParaALista() {
        Button botaoVaiParaALista = findViewById(R.id.lista_de_alunos);
        botaoVaiParaALista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreListaDeAlunos();
            }
        });
    }

    private void abreListaDeAlunos() {
        startActivity(new Intent(this, ListaAlunosActivity.class));
    }


    private void vaiParaOFormulario() {
        Button botaoVaiParaOFormulario = findViewById(
                R.id.novo_aluno);
        botaoVaiParaOFormulario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreFormularioModoInsereAluno();
            }
        });
    }

    private void abreFormularioModoInsereAluno() {
        startActivity(new Intent(this, FormularioAlunoActivity.class));
    }

}
