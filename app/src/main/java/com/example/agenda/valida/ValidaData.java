package com.example.agenda.valida;

import android.widget.EditText;

import com.example.agenda.formatter.FormataData;
import com.google.android.material.textfield.TextInputLayout;

public class ValidaData implements Validador {

    private final TextInputLayout textInputData;
    private final EditText campoData;
    private final ValidacaoPadrao validacaoPadrao;
    private final FormataData formatador = new FormataData();
    private static final String ERRO = "Data inválida";

    public ValidaData(TextInputLayout textInputDataComFormatacao) {
        this.textInputData = textInputDataComFormatacao;
        this.campoData = textInputDataComFormatacao.getEditText();
        this.validacaoPadrao = new ValidacaoPadrao(textInputDataComFormatacao);
    }

    private boolean validaData(String dataComFormatacao) {
        int digitos = dataComFormatacao.length();
        if (digitos < 7 || digitos > 8) {
            textInputData.setError(ERRO);
            return false;
        }
        return true;
    }

    public boolean estaValido() {
        if (!validacaoPadrao.estaValido()) return false;
        String dataComFormatacao = campoData.getText().toString();
        String dataSemFormatacao = formatador.remove(dataComFormatacao);
        if (!validaData(dataSemFormatacao)) return false;
        adicionaFormatacao(dataSemFormatacao);
        return true;
    }

    private void adicionaFormatacao(String dataFormatada) {
        String dataComFormatacao = formatador.formata(dataFormatada);
        campoData.setText(dataComFormatacao);
    }

}
