package com.example.agenda.ui.activity;


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
import java.util.Objects;

import static com.example.agenda.ui.activity.ContantesActivities.CHAVE_ALUNO;


public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo Aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Editar Aluno";
    private final List<Validador> validadores = new ArrayList<>();
    private EditText campoNome;
    private EditText campoSobrenome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private EditText campoData;
    private Aluno aluno;
    private TextInputLayout textInputNome;
    private TextInputLayout textInputSobrenome;
    private TextInputLayout textInputTelefoneComDdd;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputData;
    private TextInputLayout textInputCampo;
    private EditText campo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializacaoDosCampos();
        carregaAluno();
        setaViews();
    }

    private void setaViews(){
        textInputNome = findViewById(R.id.activity_formulario_aluno_nome);
        textInputSobrenome = findViewById(R.id.activity_formulario_aluno_sobrenome);
        textInputTelefoneComDdd = findViewById(R.id.activity_formulario_aluno_telefone);
        textInputEmail = findViewById(R.id.activity_formulario_aluno_email);
        textInputData = findViewById(R.id.activity_formulario_data_de_nascimento);
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

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoSobrenome.setText(aluno.getSobrenome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
        campoData.setText(aluno.getData());
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

    private void voltaParaListaDeAlunos() {
        startActivity(new Intent(this, ListaAlunosActivity.class));
    }

    private void inicializacaoDosCampos() {
        adicionaValidacaoPadrao(textInputNome);
        adicionaValidacaoPadrao(textInputSobrenome);

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

    private void preencheAluno() {
        String nome = Objects.requireNonNull(textInputNome.getEditText()).toString();
        String sobrenome = Objects.requireNonNull(textInputSobrenome.getEditText()).toString();
        String telefone = Objects.requireNonNull(textInputTelefoneComDdd.getEditText()).toString();
        String email = Objects.requireNonNull(textInputEmail.getEditText()).toString();
        String data = Objects.requireNonNull(textInputData.getEditText()).toString();

        aluno.setNome(nome);
        aluno.setSobrenome(sobrenome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
        aluno.setData(data);
    }

    private void adicionaValidacaoPadrao(final TextInputLayout textInputCampo) {
        final ValidacaoPadrao validador = new ValidacaoPadrao(textInputCampo);
        this.textInputCampo = textInputCampo;
        this.campo = this.textInputCampo.getEditText();
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

}