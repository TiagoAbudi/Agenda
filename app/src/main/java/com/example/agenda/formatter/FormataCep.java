package com.example.agenda.formatter;

public class FormataCep {

    public String formata(String cepComFormatacao) {
        return cepComFormatacao
                .replaceAll("([0-9]{5})([0-9]{3})", "$1-$2");
    }

    public String remove(String dataComFormatacao) {
        return dataComFormatacao
                .replace("-", "");
    }


}
