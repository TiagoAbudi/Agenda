package com.example.agenda.ui.activity;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.database.BD;
import com.example.agenda.formatter.FormataData;
import com.example.agenda.formatter.FormataTelefoneComDdd;
import com.example.agenda.model.Aluno;
import com.example.agenda.valida.ValidaData;
import com.example.agenda.valida.ValidaEmail;
import com.example.agenda.valida.ValidaTelefoneComDdd;
import com.example.agenda.valida.ValidacaoPadrao;
import com.example.agenda.valida.Validador;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.agenda.ui.activity.ContantesActivities.CHAVE_ALUNO;


public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo Aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Editar Aluno";
    private final List<Validador> validadores = new ArrayList<>();
    private Aluno aluno;
    private TextInputLayout textInputNome;
    private TextInputLayout textInputSobrenome;
    private TextInputLayout textInputData;
    private TextInputLayout textInputTelefoneComDdd;
    private TextInputLayout textInputEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializacaoDosCampos();
        carregaAluno();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_fomulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
            if (validaTodosCampos()) {
                finalizaFormulario();
            }
        }
        if (itemId == R.id.activity_formulario_aluno_menu_cancelar) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializacaoDosCampos() {
        setaViews();
        iniciaCampoNome();
        iniciaCampoSobremome();
        iniciaCampoData();
        iniciaCampoTelefone();
        iniciaCampoEmail();
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void finalizaFormulario() {
        preencheAluno();
        final BD bd;
        bd = new BD(this);
        if (aluno.temIdValido()) {
            bd.atualizar(aluno);
        }
        if (aluno.naoTemIdValido()) {
            bd.inserir(aluno);
        }
        voltaParaListaDeAlunos();
    }

    private void preencheCampos() {
        textInputNome.getEditText().setText(aluno.getNome());
        textInputSobrenome.getEditText().setText(aluno.getSobrenome());
        textInputData.getEditText().setText(aluno.getData());
        textInputTelefoneComDdd.getEditText().setText(aluno.getTelefone());
        textInputEmail.getEditText().setText(aluno.getEmail());
    }

    private void adicionaValidacaoPadrao(final TextInputLayout textInputCampo) {
        final EditText campo = textInputCampo.getEditText();
        final ValidacaoPadrao validador = new ValidacaoPadrao(textInputCampo);
        validadores.add(validador);
        campo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validador.estaValido();
                }
            }
        });
    }

    private boolean validaTodosCampos() {
        boolean formularioEstaValido = true;
        for (Validador validador :
                validadores) {
            if (!validador.estaValido()) {
                formularioEstaValido = false;
            }
        }
        return formularioEstaValido;
    }

    private void voltaParaListaDeAlunos() {
        startActivity(new Intent(this, ListaAlunosActivity.class));
    }

    private void iniciaCampoNome() {
        adicionaValidacaoPadrao(textInputNome);
    }

    private void iniciaCampoSobremome() {
        adicionaValidacaoPadrao(textInputSobrenome);
    }

    private void iniciaCampoData() {
        TextInputLayout textInputData = findViewById(R.id.activity_formulario_data_de_nascimento);
        EditText campoData = textInputData.getEditText();
        ValidaData validadorData = new ValidaData(textInputData);
        validadores.add(validadorData);
        FormataData formatadorData = new FormataData();
        campoData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String dataComFormatacao = campoData.getText().toString();
                if (hasFocus) {
                    String dataSemFormatacao = formatadorData.remove(dataComFormatacao);
                    campoData.setText(dataSemFormatacao);
                } else {
                    validadorData.estaValido();
                }
            }
        });
    }

    private void iniciaCampoTelefone() {
        EditText campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
        ValidaTelefoneComDdd validadorTelefone = new ValidaTelefoneComDdd(textInputTelefoneComDdd);
        validadores.add(validadorTelefone);
        FormataTelefoneComDdd formatadorTelefone = new FormataTelefoneComDdd();
        campoTelefoneComDdd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String telefoneComDdd = campoTelefoneComDdd.getText().toString();
                if (hasFocus) {
                    String telefoneComDddSemFormatacao = formatadorTelefone.remove(telefoneComDdd);
                    campoTelefoneComDdd.setText(telefoneComDddSemFormatacao);
                } else {
                    validadorTelefone.estaValido();
                }
            }
        });
    }

    private void iniciaCampoEmail() {
        EditText campoEmail = textInputEmail.getEditText();
        ValidaEmail validadorEmail = new ValidaEmail(textInputEmail);
        validadores.add(validadorEmail);
        campoEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validadorEmail.estaValido();
                }
            }
        });
    }

    private void preencheAluno() {
        String nome = textInputNome.getEditText().getText().toString();
        String sobrenome = textInputSobrenome.getEditText().getText().toString();
        String data = textInputData.getEditText().getText().toString();
        String telefone = textInputTelefoneComDdd.getEditText().getText().toString();
        String email = textInputEmail.getEditText().getText().toString();

        aluno.setNome(nome);
        aluno.setSobrenome(sobrenome);
        aluno.setData(data);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }

    private void setaViews() {
        textInputNome = findViewById(R.id.activity_formulario_aluno_nome);
        textInputSobrenome = findViewById(R.id.activity_formulario_aluno_sobrenome);
        textInputData = findViewById(R.id.activity_formulario_data_de_nascimento);
        textInputTelefoneComDdd = findViewById(R.id.activity_formulario_aluno_telefone);
        textInputEmail = findViewById(R.id.activity_formulario_aluno_email);
    }

}