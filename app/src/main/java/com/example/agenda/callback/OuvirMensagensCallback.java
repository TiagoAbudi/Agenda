package com.example.agenda.callback;

import com.example.agenda.model.Mensagem;
import com.example.agenda.ui.activity.ChatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OuvirMensagensCallback implements Callback<Mensagem> {

   private ChatActivity activity;

   public OuvirMensagensCallback(ChatActivity activity) {
      this.activity = activity;
   }

   @Override
   public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
      if (response.isSuccessful()) {
         Mensagem mensagem = response.body();
         activity.colocaNaLista(mensagem);
      }
   }

   @Override
   public void onFailure(Call<Mensagem> call, Throwable t) {
      activity.ouvirMensagem();
   }

}
