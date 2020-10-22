package com.example.agenda.service;

import com.example.agenda.model.Mensagem;

import org.json.JSONStringer;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatService {

    public void enviar(Mensagem mensagem) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String texto = mensagem.getTexto();

                try {
                    HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://192.168.237.68:8080/polling").openConnection();
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("content-type", "application/json");

                    JSONStringer json = new JSONStringer()
                            .object()
                            .key("text")
                            .value(texto)
                            .key("id")
                            .value(mensagem.getId()).endObject();
                    OutputStream saida = httpConnection.getOutputStream();
                    PrintStream ps = new PrintStream(saida);
                    ps.println(json.toString());

                    httpConnection.connect();
                    httpConnection.getInputStream();

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
