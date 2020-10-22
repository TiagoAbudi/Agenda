package com.example.agenda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.model.Mensagem;
import com.example.agenda.service.ChatService;
import com.example.agenda.ui.adapter.MensagemAdapter;

import java.util.Arrays;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private int idDoCliente = 1;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ListView listaDeMensagens = findViewById(R.id.activity_chat_list_view);

        List<Mensagem> mensagens = Arrays.asList(new Mensagem(1, "Olá alunos de android"), new Mensagem(2, "Oi"));

        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);

        listaDeMensagens.setAdapter(adapter);

        editText = findViewById(R.id.activity_chat_edit_text_campo_menssagem);

        button = findViewById(R.id.activity_chat_botao_menssagem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChatService().enviar(new Mensagem(idDoCliente, editText.getText().toString()));
            }
        });

    }
}