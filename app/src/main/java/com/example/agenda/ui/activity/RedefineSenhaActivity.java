package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RedefineSenhaActivity extends AppCompatActivity {

   private static final String TITULO_APPBAR = "Recuperação de senha";
   private AutoCompleteTextView email;
   private FirebaseAuth firebaseAuth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_redefine_senha);
      firebaseAuth = FirebaseAuth.getInstance();
      setTitle(TITULO_APPBAR);
   }

   @Override
   protected void onResume() {
      super.onResume();
      init();
   }

   private void init() {
      email = (AutoCompleteTextView) findViewById(R.id.email);
   }

   public void reset(View view) {
      firebaseAuth
              .sendPasswordResetEmail(email.getText().toString())
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                       email.setEnabled(false);
                       Toast.makeText(
                               RedefineSenhaActivity.this,
                               "Email para a recuperação de senha enviado, verifique sua caixa de email.",
                               Toast.LENGTH_LONG
                       ).show();
                       voltaParaLogin();
                    } else {
                       Toast.makeText(
                               RedefineSenhaActivity.this,
                               "Email inválido, informe um email válido.",
                               Toast.LENGTH_LONG
                       ).show();
                    }
                 }
              });
   }

   private void voltaParaLogin() {
      startActivity(new Intent(this, LoginActivity.class));
   }

}