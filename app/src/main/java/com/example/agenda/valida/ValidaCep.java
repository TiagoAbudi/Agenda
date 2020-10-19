package com.example.agenda.valida;

import android.widget.EditText;

import com.example.agenda.formatter.FormataCep;
import com.google.android.material.textfield.TextInputLayout;

public class ValidaCep implements Validador {

    private static final String ERRO = "CEP inválido";
    private final TextInputLayout textInputCep;
    private final EditText campoCep;
    private final ValidacaoPadrao validacaoPadrao;
    private final FormataCep formatador = new FormataCep();

    public ValidaCep(TextInputLayout textInputCepComFormatacao) {
        this.textInputCep = textInputCepComFormatacao;
        this.campoCep = textInputCepComFormatacao.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputCepComFormatacao);
    }

    private boolean validaCep(String cepComFormatacao) {
        int digitos = cepComFormatacao.length();
        if (digitos < 8 || digitos > 9) {
            textInputCep.setError(ERRO);
            return false;
        }
        return true;
    }

    public boolean estaValido() {
        if (!validacaoPadrao.estaValido()) return false;
        String cepComFormatacao = campoCep.getText().toString();
        String cepSemFormatacao = formatador.remove(cepComFormatacao);
        if (!validaCep(cepSemFormatacao)) return false;
        adicionaFormatacao(cepSemFormatacao);
        return true;
    }

    private void adicionaFormatacao(String cepFormatado) {
        String cepComFormatacao = formatador.formata(cepFormatado);
        campoCep.setText(cepComFormatacao);
    }

}
