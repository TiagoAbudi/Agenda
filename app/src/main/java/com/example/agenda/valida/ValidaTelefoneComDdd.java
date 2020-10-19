package com.example.agenda.valida;

import android.widget.EditText;

import com.example.agenda.formatter.FormataTelefoneComDdd;
import com.google.android.material.textfield.TextInputLayout;

public class ValidaTelefoneComDdd implements Validador {

    private static final String ERRO = "Telefone inválido";
    private final TextInputLayout textInputTelefoneComDdd;
    private final EditText campoTelefoneComDdd;
    private final ValidacaoPadrao validacaoPadrao;
    private final FormataTelefoneComDdd formatador = new FormataTelefoneComDdd();

    public ValidaTelefoneComDdd(TextInputLayout textInputTelefoneComDdd) {
        this.textInputTelefoneComDdd = textInputTelefoneComDdd;
        this.campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputTelefoneComDdd);
    }

    private boolean validaEntreDezOuOnzeDigitos(String telefoneComDdd) {
        int digitos = telefoneComDdd.length();
        if (digitos < 10 || digitos > 11) {
            textInputTelefoneComDdd.setError(ERRO);
            return false;
        }
        return true;
    }

    @Override
    public boolean estaValido() {
        if (!validacaoPadrao.estaValido()) return false;
        String telefoneComDdd = campoTelefoneComDdd.getText().toString();
        String telefoneComDddSemFormatacao = formatador.remove(telefoneComDdd);
        if (!validaEntreDezOuOnzeDigitos(telefoneComDddSemFormatacao)) return false;
        adicionaFormatacao(telefoneComDddSemFormatacao);
        return true;
    }

    private void adicionaFormatacao(String telefoneComDdd) {
        String telefoneComDddFormatado = formatador.formata(telefoneComDdd);
        campoTelefoneComDdd.setText(telefoneComDddFormatado);
    }

}