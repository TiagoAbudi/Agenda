package com.example.agenda.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.agenda.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

   private static final String TITULO_APPBAR = "Menu";

   @Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.menu);
      setTitle(TITULO_APPBAR);
      validaPermissoes();
      botaoVaiParaALista();
      botaoVaiParaChat();
      vaiParaOFormulario();
      vaiPraOSite();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater()
              .inflate(R.menu.desconecta_conta, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.desconecta_conta) {
         desconectaConta();
      }
      return super.onOptionsItemSelected(item);
   }

   public void desconectaConta() {
      FirebaseAuth.getInstance().signOut();
      voltaParaLogin();
   }

   private void voltaParaLogin() {
      startActivity(new Intent(this, LoginActivity.class));
   }

   private void botaoVaiParaALista() {
      Button botaoVaiParaALista = findViewById(R.id.botao_lista_de_alunos);
      botaoVaiParaALista.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            abreListaDeAlunos();
         }
      });
   }

   private void abreListaDeAlunos() {
      startActivity(new Intent(this, ListaAlunosActivity.class));
   }

   private void botaoVaiParaChat() {
      Button botaoVaiParaALista = findViewById(R.id.botao_chat);
      botaoVaiParaALista.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            abreChat();
         }
      });
   }

   private void abreChat() {
      startActivity(new Intent(this, ChatActivity.class));
   }


   private void vaiParaOFormulario() {
      Button botaoVaiParaOFormulario = findViewById(
              R.id.botao_novo_aluno);
      botaoVaiParaOFormulario.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            abreFormularioModoInsereAluno();
         }
      });
   }

   private void vaiPraOSite() {
      TextView text = (TextView) findViewById(R.id.software_By_Solumobi);
      text.setMovementMethod(LinkMovementMethod.getInstance());
   }

   private void abreFormularioModoInsereAluno() {
      startActivity(new Intent(this, FormularioAlunoActivity.class));
   }

   public boolean validaPermissoes() {
      int PERMISSOES_CAMERA = 0;
      int PERMISSOES_LER = 0;
      int PERMISSOES_ESCREVER = 0;
      if (ContextCompat.checkSelfPermission(MenuActivity.this,
              Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
         ActivityCompat.requestPermissions(MenuActivity.this,
                 new String[]{Manifest.permission.CAMERA},
                 PERMISSOES_CAMERA);
         return false;
      }
      if (ContextCompat.checkSelfPermission(MenuActivity.this,
              Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
         ActivityCompat.requestPermissions(MenuActivity.this,
                 new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                 PERMISSOES_LER);
         return false;
      }
      if (ContextCompat.checkSelfPermission(MenuActivity.this,
              Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
         ActivityCompat.requestPermissions(MenuActivity.this,
                 new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                 PERMISSOES_ESCREVER);
         return false;
      }
      return true;
   }

}
