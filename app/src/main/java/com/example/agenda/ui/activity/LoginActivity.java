package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Login";
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputSenha;
    private TextView botaoVaiParaCadastro;
    private TextView botaoEsqueceuSenha;
    private Button botaoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(TITULO_APPBAR);
        setaViews();
        vaiParaCadastro();
        vaiParaMenu();
        esqueceuSenha();
    }

    private void setaViews() {
        textInputEmail = findViewById(R.id.activity_login_email);
        textInputSenha = findViewById(R.id.activity_login_senha);
        botaoVaiParaCadastro = findViewById(R.id.botao_cadastro);
        botaoEsqueceuSenha = findViewById(R.id.botao_senha);
        botaoLogin = findViewById(R.id.botao_redefinir_login);
    }

    private void vaiParaCadastro() {
        botaoVaiParaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastro();
            }
        });
    }

    private void vaiParaMenu() {
        botaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreMenu();
            }
        });
    }

    private void esqueceuSenha() {
        botaoEsqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vaiParaRedefinicaoDeSenha();
            }
        });
    }

    private void vaiParaRedefinicaoDeSenha() {
        startActivity(new Intent(LoginActivity.this, RedefineSenhaActivity.class));
    }

    private void abreMenu() {
        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
    }

    private void abreCadastro() {
        startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
    }

}