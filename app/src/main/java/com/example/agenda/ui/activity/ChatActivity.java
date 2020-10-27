package com.example.agenda.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.callback.EnviarMensagemCallback;
import com.example.agenda.callback.OuvirMensagensCallback;
import com.example.agenda.model.Mensagem;
import com.example.agenda.service.ChatService;
import com.example.agenda.ui.adapter.MensagemAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

   private static final String TITULO_APPBAR = "Chat";
   private final int idDoCliente = 1;
   private EditText editText;
   private Button button;
   private ListView listaDeMensagens;
   private List<Mensagem> mensagens;
   private ChatService chatService;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_chat);
      setaCampos();
      setTitle(TITULO_APPBAR);
      Retrofit retrofit = new Retrofit.Builder()
              .baseUrl("http://192.168.237.68:8080/")
              .addConverterFactory(GsonConverterFactory.create())
              .build();
      ouveMensagens(retrofit);
   }

   private void setaViews() {
      editText = findViewById(R.id.activity_chat_edit_text_campo_menssagem);
      listaDeMensagens = findViewById(R.id.activity_chat_list_view);
      button = findViewById(R.id.activity_chat_botao_menssagem);
   }

   private void setaCampos() {
      setaViews();
      configuraListaDeMensagens();
      configuraBotaoEnviaMensagem();
   }

   private void configuraListaDeMensagens() {
      mensagens = new ArrayList<>();
      MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
      listaDeMensagens.setAdapter(adapter);
   }

   private void ouveMensagens(Retrofit retrofit) {
      chatService = retrofit.create(ChatService.class);
      Call<Mensagem> call = chatService.ouvirMensagens();
      call.enqueue(new OuvirMensagensCallback(this));
   }

   private void configuraBotaoEnviaMensagem() {
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            chatService.enviar(new Mensagem(idDoCliente, editText.getText().toString())).enqueue(new EnviarMensagemCallback());
         }
      });
   }

   public void colocaNaLista(Mensagem mensagem) {
      mensagens.add(mensagem);
      MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
      listaDeMensagens.setAdapter(adapter);
      ouvirMensagem();
   }

   public void ouvirMensagem() {
      Call<Mensagem> call = chatService.ouvirMensagens();
      call.enqueue(new OuvirMensagensCallback(this));
   }

}