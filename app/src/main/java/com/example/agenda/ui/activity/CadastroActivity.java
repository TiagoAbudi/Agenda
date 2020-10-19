package com.example.agenda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.formatter.FormataTelefoneComDdd;
import com.example.agenda.valida.ValidaEmail;
import com.example.agenda.valida.ValidaTelefoneComDdd;
import com.example.agenda.valida.ValidacaoPadrao;
import com.example.agenda.valida.Validador;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    private final List<Validador> validadores = new ArrayList<>();
    private TextInputLayout textInputNome;
    private TextInputLayout textInputSobrenome;
    private TextInputLayout textInputTelefone;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputSenha;
    private TextInputLayout textInputConfirmacaoSenha;
    private Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        iniciaCampos();
    }

    private void iniciaCampos() {
        setaViews();
        iniciaCampoNome();
        iniciaCampoSobremome();
        iniciaCampoTelefone();
        iniciaCampoEmail();
        iniciaCampoSenha();
        iniciaCampoConfirmacaoSenha();
        configuraBotaoCadastro();
    }

    private void setaViews() {
        textInputNome = findViewById(R.id.activity_cadastro_nome);
        textInputSobrenome = findViewById(R.id.activity_cadastro_sobrenome);
        textInputTelefone = findViewById(R.id.activity_cadastro_telefone);
        textInputEmail = findViewById(R.id.activity_cadastro_email);
        textInputSenha = findViewById(R.id.activity_cadastro_senha);
        textInputConfirmacaoSenha = findViewById(R.id.activity_cadastro_confirmar_senha);
        botaoCadastrar = findViewById(R.id.botao_cadastro_login);
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
        boolean cadastroEstaValido = true;
        for (Validador validador :
                validadores) {
            if (!validador.estaValido()) {
                cadastroEstaValido = false;
            }
            if (iniciaCampoConfirmacaoSenha()) {
                cadastroEstaValido = true;
            }
        }
        return cadastroEstaValido;
    }

    private void iniciaCampoNome() {
        adicionaValidacaoPadrao(textInputNome);
    }

    private void iniciaCampoSobremome() {
        adicionaValidacaoPadrao(textInputSobrenome);
    }

    private void iniciaCampoTelefone() {
        EditText campoTelefoneComDdd = textInputTelefone.getEditText();
        ValidaTelefoneComDdd validadorTelefone = new ValidaTelefoneComDdd(textInputTelefone);
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

    private void iniciaCampoSenha() {
        adicionaValidacaoPadrao(textInputSenha);
    }

    private boolean iniciaCampoConfirmacaoSenha() {

        textInputConfirmacaoSenha.getEditText();
        textInputSenha.getEditText();

        adicionaValidacaoPadrao(textInputConfirmacaoSenha);

        validacaoSenha();
        return true;
    }

    private void validacaoSenha() {
        if (textInputConfirmacaoSenha.equals(textInputSenha)) {
            textInputConfirmacaoSenha.setError(null);
            textInputConfirmacaoSenha.setErrorEnabled(false);
        } else {
            textInputConfirmacaoSenha.setError("Os campos não batem");
        }
    }

    private void configuraBotaoCadastro() {
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validaTodosCampos();
            }
        });
    }

}