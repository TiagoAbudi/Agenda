package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.valida.ValidaEmail;
import com.example.agenda.valida.ValidacaoPadrao;
import com.example.agenda.valida.Validador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

   private final String APP_BAR = "Login";
   private final List<Validador> validadores = new ArrayList<>();
   private TextInputLayout textInputEmail;
   private EditText campoEmail;
   private TextInputLayout textInputSenha;
   private EditText campoSenha;
   private TextView botaoVaiParaCadastro;
   private TextView botaoEsqueceuSenha;
   private Button botaoLogin;
   private FirebaseAuth mAuth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      mAuth = FirebaseAuth.getInstance();
//      geraToken();
      setaViews();
      setTitle(APP_BAR);
      configuraBotaoLogin();
   }

   @Override
   public void onStart() {
      super.onStart();
      if (isUsuarioConectado()) {
         abreMenu();
      }
   }

   private void loginUsuario(String email, String password) {
      mAuth.signInWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                       Log.d("Sucesso", "sigInWithEmail:success");
                       FirebaseUser user = mAuth.getCurrentUser();
                       Toast.makeText(getApplicationContext(), "Sucesso no login", Toast.LENGTH_SHORT).show();
                       abreMenu();
                    } else {
                       Log.w("Falha", "sigInWithEmail:failure", task.getException());
                       Toast.makeText(getApplicationContext(), "Email e/ou senha inválido(os)",
                               Toast.LENGTH_SHORT).show();
                    }

                 }
              });
   }

   private void abreMenu() {
      Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
   }

   private boolean isUsuarioConectado() {
      FirebaseUser currentUser = mAuth.getCurrentUser();
      return currentUser != null;
   }

   private void setaViews() {
      textInputEmail = findViewById(R.id.activity_login_email);
      textInputSenha = findViewById(R.id.activity_login_senha);
      botaoVaiParaCadastro = findViewById(R.id.botao_cadastrar);
      botaoEsqueceuSenha = findViewById(R.id.botao_redefinir_senha);
      botaoLogin = findViewById(R.id.botao_login);
   }

   private void adicionaValidacaoPadrao(final TextInputLayout textInputCampo) {
      final EditText campo = textInputCampo.getEditText();
      final ValidacaoPadrao validador = new ValidacaoPadrao(textInputCampo);
      validadores.add(validador);
      assert campo != null;
      campo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
               validador.estaValido();
            }
         }
      });
   }

   private boolean validaTodosCampos() {
      boolean cadastroEstaValido = true;
      for (Validador validador :
              validadores) {
         if (!validador.estaValido()) {
            cadastroEstaValido = false;
         }
      }
      return cadastroEstaValido;
   }

   private void validaEmailSenha() {
      ValidaEmail validadorEmail = new ValidaEmail(textInputEmail);
      validadores.add(validadorEmail);
      campoEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
               validadorEmail.estaValido();
            }
         }
      });
      adicionaValidacaoPadrao(textInputSenha);
   }

   private void configuraBotaoLogin() {
      campoEmail = textInputEmail.getEditText();
      campoSenha = textInputSenha.getEditText();
      validaEmailSenha();
      botaoLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (validaTodosCampos()) {
               loginUsuario(campoEmail.getText().toString(), campoSenha.getText().toString());
            }
         }
      });
   }

   public void vaiParaRedefinicaoDeSenha(View view) {
      startActivity(new Intent(this, RedefineSenhaActivity.class));
   }

   public void vaiParaCadastro(View view) {
      startActivity(new Intent(LoginActivity.this, CadastroActivity.class));
   }

//   private void geraToken(){
//      FirebaseMessaging.getInstance().getToken()
//              .addOnCompleteListener(new OnCompleteListener<String>() {
//                 @Override
//                 public void onComplete(@NonNull Task<String> task) {
//                    if (!task.isSuccessful()) {
//                       Log.w("FalhaTOKEN",
//                               "Fetching FCM registration token failed",
//                               task.getException());
//                       return;
//                    }
//                    String token = task.getResult();
//                    String msg = "Token recebido" + token;
//                    Log.d("SucessoTOKEN", msg);
//                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                 }
//              });
//   }

}