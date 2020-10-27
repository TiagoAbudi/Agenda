package com.example.agenda.service;

import com.example.agenda.model.Mensagem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ChatService {

   @POST("polling")
   Call<Void> enviar(@Body Mensagem mensagem);

   @GET("polling")
   Call<Mensagem> ouvirMensagens();

}
