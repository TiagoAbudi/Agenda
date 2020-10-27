package com.example.agenda.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.formatter.FormataTelefoneComDdd;
import com.example.agenda.model.Usuario;
import com.example.agenda.valida.ValidaEmail;
import com.example.agenda.valida.ValidaTelefoneComDdd;
import com.example.agenda.valida.ValidacaoPadrao;
import com.example.agenda.valida.Validador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CadastroActivity extends AppCompatActivity {

   private static final String TITULO_APPBAR = "Cadastro";
   private final List<Validador> validadores = new ArrayList<>();
   private TextInputLayout textInputNome;
   private EditText campoNome;
   private TextInputLayout textInputSobrenome;
   private EditText campoSobrenome;
   private TextInputLayout textInputTelefone;
   private EditText campoTelefone;
   private TextInputLayout textInputEmail;
   private EditText campoEmail;
   private TextInputLayout textInputSenha;
   private EditText campoSenha;
   private Button botaoCadastrar;
   private Button botaoFoto;
   private Uri uri;
   private ImageView fotoDePerfil;
   private FirebaseAuth mAuth;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cadastro);
      iniciaCampos();
      setTitle(TITULO_APPBAR);
      mAuth = FirebaseAuth.getInstance();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater()
              .inflate(R.menu.volta_para_o_login, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      int itemId = item.getItemId();
      if (itemId == R.id.activity_cadastro_menu_cancelar) {
         new AlertDialog
                 .Builder(this)
                 .setTitle("Cancelando cadastro")
                 .setMessage("Tem certeza que deseja cancelar o cadastro? Todas as alterções não salvas serão descartadas")
                 .setPositiveButton("Sim", (dialogInterface, i) -> finish())
                 .setNegativeButton("Não", null)
                 .show();
      }
      return super.onOptionsItemSelected(item);
   }

   private void criarUsuario(String email, String password) {
      mAuth.createUserWithEmailAndPassword(email, password)
              .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                 @Override
                 public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                       Log.d("Sucess create", "createUserWithEmail:success");
                       saveUserInFirebase();
                       Toast.makeText(getApplicationContext(), "Usuário criado", Toast.LENGTH_SHORT).show();
                       vaiParaLogin();
                    } else {
                       Log.w("Fail create", "createUserWithEmail:failure", task.getException());
                       Toast.makeText(getApplicationContext(), "Authentication failed.",
                               Toast.LENGTH_SHORT).show();
                    }
                 }
              });
   }

   private void saveUserInFirebase() {
      String fileName = UUID.randomUUID().toString();
      final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + fileName);
      ref.putFile(uri)
              .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                 @Override
                 public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                          Log.e("Teste", uri.toString());

                          String uid = FirebaseAuth.getInstance().getUid();
                          String nome = campoNome.getText().toString();
                          String sobrenome = campoSobrenome.getText().toString();
                          String telefone = campoTelefone.getText().toString();
                          String foto = uri.toString();
                          Usuario usuario = new Usuario(uid, nome, sobrenome, telefone, foto);

                          FirebaseFirestore.getInstance().collection("usuarios")
                                  .add(usuario)
                                  .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                     @Override
                                     public void onSuccess(DocumentReference documentReference) {
                                        Log.e("Teste", documentReference.getId());
                                     }
                                  })
                                  .addOnFailureListener(new OnFailureListener() {
                                     @Override
                                     public void onFailure(@NonNull Exception e) {
                                        Log.e("Teste", e.getMessage());
                                     }
                                  });
                       }
                    });
                 }
              })
              .addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {
                    Log.e("Teste", e.getMessage(), e);
                 }
              });
   }

   private void configuraBotaoCadastrar() {
      campoNome = textInputNome.getEditText();
      campoSobrenome = textInputSobrenome.getEditText();
      campoTelefone = textInputTelefone.getEditText();
      campoEmail = textInputEmail.getEditText();
      campoSenha = textInputSenha.getEditText();
      validaEmailSenha();
      botaoCadastrar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (validaTodosCampos()) {
               criarUsuario(campoEmail.getText().toString(), campoSenha.getText().toString());
            }
         }
      });
   }

   private void configuraBotaoFoto() {
      botaoFoto.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            selectFoto();
         }
      });
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == 0) {
         uri = data.getData();

         Bitmap bitmap = null;
         try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            Matrix matrix = new Matrix();
            matrix.postRotate(0);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap,
                    0,
                    0,
                    bitmap.getWidth(),
                    bitmap.getHeight(),
                    matrix,
                    true);
            fotoDePerfil.setImageBitmap(rotatedBitmap);
            botaoFoto.setAlpha(0);
         } catch (IOException e) {

         }
      }

   }

   private void selectFoto() {
      Intent intent = new Intent(Intent.ACTION_PICK);
      intent.setType("image/*");
      startActivityForResult(intent, 0);
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

   private void vaiParaLogin() {
      startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
   }

   private void setaViews() {
      textInputNome = findViewById(R.id.activity_cadastro_nome);
      textInputSobrenome = findViewById(R.id.activity_cadastro_sobrenome);
      textInputTelefone = findViewById(R.id.activity_cadastro_telefone);
      textInputEmail = findViewById(R.id.activity_cadastro_email);
      textInputSenha = findViewById(R.id.activity_cadastro_senha);
      botaoCadastrar = findViewById(R.id.botao_cadastro);
      botaoFoto = findViewById(R.id.botao_foto);
      fotoDePerfil = findViewById(R.id.activity_cadastro_foto_de_perfil);
   }

   private void iniciaCampos() {
      setaViews();
      iniciaCampoNome();
      iniciaCampoSobremome();
      iniciaCampoTelefone();
      iniciaCampoEmail();
      iniciaCampoSenha();
      configuraBotaoCadastrar();
      configuraBotaoFoto();
   }

   private void adicionaValidacaoPadrao(final TextInputLayout textInputCampo) {
      final EditText campo = textInputCampo.getEditText();
      final ValidacaoPadrao validador = new ValidacaoPadrao(textInputCampo);
      validadores.add(validador);
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

   private void iniciaCampoNome() {
      adicionaValidacaoPadrao(textInputNome);
   }

   private void iniciaCampoSobremome() {
      adicionaValidacaoPadrao(textInputSobrenome);
   }

   private void iniciaCampoTelefone() {
      EditText campoTelefoneComDdd = textInputTelefone.getEditText();
      ValidaTelefoneComDdd validadorTelefone = new ValidaTelefoneComDdd(textInputTelefone);
      validadores.add(validadorTelefone);
      FormataTelefoneComDdd formatadorTelefone = new FormataTelefoneComDdd();
      campoTelefoneComDdd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            String telefoneComDdd = campoTelefoneComDdd.getText().toString();
            if (hasFocus) {
               String telefoneComDddSemFormatacao = formatadorTelefone.remove(telefoneComDdd);
               campoTelefoneComDdd.setText(telefoneComDddSemFormatacao);
            } else {
               validadorTelefone.estaValido();
            }
         }
      });
   }

   private void iniciaCampoEmail() {
      campoEmail = textInputEmail.getEditText();
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
   }

   private void iniciaCampoSenha() {
      adicionaValidacaoPadrao(textInputSenha);
   }

}