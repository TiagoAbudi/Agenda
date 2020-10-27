package com.example.agenda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.database.BD;
import com.example.agenda.domain.Address;
import com.example.agenda.domain.Util;
import com.example.agenda.domain.ZipCodeListener;
import com.example.agenda.formatter.FormataCep;
import com.example.agenda.formatter.FormataData;
import com.example.agenda.formatter.FormataTelefoneComDdd;
import com.example.agenda.model.Aluno;
import com.example.agenda.valida.ValidaCep;
import com.example.agenda.valida.ValidaData;
import com.example.agenda.valida.ValidaEmail;
import com.example.agenda.valida.ValidaTelefoneComDdd;
import com.example.agenda.valida.ValidacaoPadrao;
import com.example.agenda.valida.Validador;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.agenda.ui.activity.ContantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

   public static final int CODIGO_CAMERA = 567;
   private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo Aluno";
   private static final String TITULO_APPBAR_EDITA_ALUNO = "Editar Aluno";
   private final List<Validador> validadores = new ArrayList<>();
   private final Intent abreCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
   private Aluno aluno;
   private EditText campoCep;
   private TextInputLayout textInputNome;
   private TextInputLayout textInputSobrenome;
   private TextInputLayout textInputData;
   private TextInputLayout textInputTelefoneComDdd;
   private TextInputLayout textInputCep;
   private TextInputLayout textInputBairro;
   private TextInputLayout textInputRua;
   private TextInputLayout textInputNumero;
   private TextInputLayout textInputEstado;
   private TextInputLayout textInputCidade;
   private TextInputLayout textInputEmail;
   private String caminhoFoto;
   private Util util;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_formulario_aluno);
      inicializacaoDosCampos();
      carregaAluno();
      StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
      StrictMode.setVmPolicy(builder.build());
      enderecoAutomatico();
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater()
              .inflate(R.menu.activity_fomulario_aluno_menu, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      int itemId = item.getItemId();
      if (itemId == R.id.activity_formulario_aluno_menu_salvar) {
         if (validaTodosCampos()) {
            finalizaFormulario();
         }
      }
      if (itemId == R.id.activity_formulario_aluno_menu_cancelar) {
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

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (resultCode == Activity.RESULT_OK) {
         if (requestCode == CODIGO_CAMERA) {
            ImageView foto = findViewById(R.id.imagem_de_perfil);
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 90, 90, true);
            Matrix matrix = new Matrix();
            matrix.postRotate(270);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapReduzido,
                    0,
                    0,
                    bitmapReduzido.getWidth(),
                    bitmapReduzido.getHeight(),
                    matrix,
                    true);
            foto.setImageBitmap(rotatedBitmap);
         }
         if (requestCode == Address.REQUEST_ZIP_CODE_CODE) {
            campoCep.setText(data.getStringExtra(Address.ZIP_CODE_KEY));
         }
      }
   }

   private void inicializacaoDosCampos() {
      setaViews();
      iniciaCampoNome();
      iniciaCampoSobremome();
      iniciaCampoData();
      iniciaCampoTelefone();
      iniciaCampoCep();
      iniciaCampoBairro();
      iniciaCampoRua();
      iniciaCampoNumero();
      iniciaCampoEstado();
      iniciaCampoCidade();
      iniciaCampoEmail();
      configuraBotaoFoto();
//        configuraBotaoEscolheFoto();
   }

   private void setField(int id, String data) {
      ((EditText) findViewById(id)).setText(data);
   }

   private void carregaAluno() {
      Intent dados = getIntent();
      if (dados.hasExtra(CHAVE_ALUNO)) {
         setTitle(TITULO_APPBAR_EDITA_ALUNO);
         aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
         preencheCampos();
      } else {
         setTitle(TITULO_APPBAR_NOVO_ALUNO);
         aluno = new Aluno();
      }
   }

   private void finalizaFormulario() {
      preencheAluno();
      final BD bd;
      bd = new BD(this);
      if (aluno.temIdValido()) {
         bd.atualizar(aluno);
         Toast toast = Toast.makeText(this, "Cadastro atualizado!", Toast.LENGTH_SHORT);
         toast.show();
      }
      if (aluno.naoTemIdValido()) {
         bd.inserir(aluno);
         Toast toast = Toast.makeText(this, "Cadastro concluído!", Toast.LENGTH_SHORT);
         toast.show();
      }
      voltaParaListaDeAlunos();
   }

   private void preencheCampos() {
      Objects.requireNonNull(textInputNome.getEditText()).setText(aluno.getNome());
      Objects.requireNonNull(textInputSobrenome.getEditText()).setText(aluno.getSobrenome());
      Objects.requireNonNull(textInputData.getEditText()).setText(aluno.getData());
      Objects.requireNonNull(textInputTelefoneComDdd.getEditText()).setText(aluno.getTelefone());
      Objects.requireNonNull(textInputCep.getEditText()).setText(aluno.getCep());
      Objects.requireNonNull(textInputBairro.getEditText()).setText(aluno.getBairro());
      Objects.requireNonNull(textInputRua.getEditText()).setText(aluno.getRua());
      Objects.requireNonNull(textInputNumero.getEditText()).setText(aluno.getNumero());
      Objects.requireNonNull(textInputEstado.getEditText()).setText(aluno.getEstado());
      Objects.requireNonNull(textInputCidade.getEditText()).setText(aluno.getCidade());
      Objects.requireNonNull(textInputEmail.getEditText()).setText(aluno.getEmail());
      preencheCampoFoto();
   }

   private void preencheCampoFoto() {
      if (!aluno.getFoto().equals("")) {
         ImageView foto = findViewById(R.id.imagem_de_perfil);
         Bitmap bitmap = BitmapFactory.decodeFile(aluno.getFoto());
         caminhoFoto = aluno.getFoto();
         Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 90, 90, true);
         Matrix matrix = new Matrix();
         matrix.postRotate(270);
         Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapReduzido,
                 0,
                 0,
                 bitmapReduzido.getWidth(),
                 bitmapReduzido.getHeight(),
                 matrix,
                 true);
         foto.setImageBitmap(rotatedBitmap);
      }
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
      boolean formularioEstaValido = true;
      for (Validador validador :
              validadores) {
         if (!validador.estaValido()) {
            formularioEstaValido = false;
         }
      }
      return formularioEstaValido;
   }

   private void setaViews() {
      textInputNome = findViewById(R.id.activity_formulario_aluno_nome);
      textInputSobrenome = findViewById(R.id.activity_formulario_aluno_sobrenome);
      textInputData = findViewById(R.id.activity_formulario_data_de_nascimento);
      textInputTelefoneComDdd = findViewById(R.id.activity_formulario_aluno_telefone);
      textInputCep = findViewById(R.id.activity_formulario_aluno_cep);
      textInputBairro = findViewById(R.id.activity_formulario_aluno_bairro);
      textInputRua = findViewById(R.id.activity_formulario_aluno_rua);
      textInputNumero = findViewById(R.id.activity_formulario_aluno_numero);
      textInputEstado = findViewById(R.id.activity_formulario_aluno_estado);
      textInputCidade = findViewById(R.id.activity_formulario_aluno_cidade);
      textInputEmail = findViewById(R.id.activity_formulario_aluno_email);
   }

   private void iniciaCampoNome() {
      adicionaValidacaoPadrao(textInputNome);
   }

   private void iniciaCampoSobremome() {
      adicionaValidacaoPadrao(textInputSobrenome);
   }

   private void iniciaCampoData() {
      TextInputLayout textInputData = findViewById(R.id.activity_formulario_data_de_nascimento);
      EditText campoData = textInputData.getEditText();
      ValidaData validadorData = new ValidaData(textInputData);
      validadores.add(validadorData);
      FormataData formatadorData = new FormataData();
      assert campoData != null;
      campoData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            String dataComFormatacao = campoData.getText().toString();
            if (hasFocus) {
               String dataSemFormatacao = formatadorData.remove(dataComFormatacao);
               campoData.setText(dataSemFormatacao);
            } else {
               validadorData.estaValido();
            }
         }
      });
   }

   private void iniciaCampoTelefone() {
      EditText campoTelefoneComDdd = textInputTelefoneComDdd.getEditText();
      ValidaTelefoneComDdd validadorTelefone = new ValidaTelefoneComDdd(textInputTelefoneComDdd);
      validadores.add(validadorTelefone);
      FormataTelefoneComDdd formatadorTelefone = new FormataTelefoneComDdd();
      assert campoTelefoneComDdd != null;
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

   private void iniciaCampoCep() {
      TextInputLayout textInputCep = findViewById(R.id.activity_formulario_aluno_cep);
      EditText campoCep = textInputCep.getEditText();
      ValidaCep validadorCep = new ValidaCep(textInputCep);
      validadores.add(validadorCep);
      FormataCep formatadorCep = new FormataCep();
      assert campoCep != null;
      campoCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            String cepComFormatacao = campoCep.getText().toString();
            if (hasFocus) {
               String cepSemFormatacao = formatadorCep.remove(cepComFormatacao);
               campoCep.setText(cepSemFormatacao);
            } else {
               validadorCep.estaValido();
            }
         }
      });
   }

   private void iniciaCampoBairro() {
      adicionaValidacaoPadrao(textInputBairro);
   }

   private void iniciaCampoRua() {
      adicionaValidacaoPadrao(textInputRua);
   }

   private void iniciaCampoNumero() {
      adicionaValidacaoPadrao(textInputNumero);
   }

   private void iniciaCampoEstado() {
      adicionaValidacaoPadrao(textInputEstado);
   }

   private void iniciaCampoCidade() {
      adicionaValidacaoPadrao(textInputCidade);
   }

   private void iniciaCampoEmail() {
      EditText campoEmail = textInputEmail.getEditText();
      ValidaEmail validadorEmail = new ValidaEmail(textInputEmail);
      validadores.add(validadorEmail);
      assert campoEmail != null;
      campoEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
               validadorEmail.estaValido();
            }
         }
      });
   }

   private void preencheAluno() {
      String nome = Objects.requireNonNull(textInputNome.getEditText()).getText().toString();
      String sobrenome = Objects.requireNonNull(textInputSobrenome.getEditText()).getText().toString();
      String data = Objects.requireNonNull(textInputData.getEditText()).getText().toString();
      String telefone = Objects.requireNonNull(textInputTelefoneComDdd.getEditText()).getText().toString();
      String cep = Objects.requireNonNull(textInputCep.getEditText()).getText().toString();
      String bairro = Objects.requireNonNull(textInputBairro.getEditText()).getText().toString();
      String rua = Objects.requireNonNull(textInputRua.getEditText()).getText().toString();
      String numero = Objects.requireNonNull(textInputNumero.getEditText()).getText().toString();
      String estado = Objects.requireNonNull(textInputEstado.getEditText()).getText().toString();
      String cidade = Objects.requireNonNull(textInputCidade.getEditText()).getText().toString();
      String email = Objects.requireNonNull(textInputEmail.getEditText()).getText().toString();
      String foto = caminhoFoto;

      aluno.setNome(nome);
      aluno.setSobrenome(sobrenome);
      aluno.setData(data);
      aluno.setTelefone(telefone);
      aluno.setCep(cep);
      aluno.setBairro(bairro);
      aluno.setRua(rua);
      aluno.setNumero(numero);
      aluno.setEstado(estado);
      aluno.setCidade(cidade);
      aluno.setEmail(email);
      aluno.setFoto(foto);
   }

   private void configuraBotaoFoto() {
      Button botaoFoto = findViewById(R.id.botao_foto);
      botaoFoto.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpeg";
            File arquivoFoto = new File(caminhoFoto);
            abreCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
            startActivityForResult(abreCamera, CODIGO_CAMERA);
         }
      });
   }

   private void enderecoAutomatico() {
      campoCep = findViewById(R.id.activity_formulario_aluno_edit_text_cep);
      campoCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
         @Override
         public void onFocusChange(View v, boolean hasFocus) {
            campoCep.addTextChangedListener(new ZipCodeListener(FormularioAlunoActivity.this));
            util = new Util(FormularioAlunoActivity.this,
                    R.id.activity_formulario_aluno_edit_text_cep,
                    R.id.activity_formulario_aluno_edit_text_bairro,
                    R.id.activity_formulario_aluno_edit_text_rua,
                    R.id.activity_formulario_aluno_edit_text_numero,
                    R.id.activity_formulario_aluno_edit_text_estado,
                    R.id.activity_formulario_aluno_edit_text_cidade);
         }
      });

   }

   public String getUriZipCode() {
      return "https://viacep.com.br/ws/" + campoCep.getText() + "/json/";
   }

   public void lockFields(boolean isToLock) {
      util.lockFields(isToLock);
   }

   public void setDataViews(Address address) {
      setField(R.id.activity_formulario_aluno_edit_text_rua, address.getLogradouro());
      setField(R.id.activity_formulario_aluno_edit_text_bairro, address.getBairro());
      setField(R.id.activity_formulario_aluno_edit_text_estado, address.getUf());
      setField(R.id.activity_formulario_aluno_edit_text_cidade, address.getLocalidade());
   }

   public void searchZipCode(View view) {
      Intent intent = new Intent(this, ProcuraCepActivity.class);
      startActivityForResult(intent, Address.REQUEST_ZIP_CODE_CODE);
   }

   private void voltaParaListaDeAlunos() {
      startActivity(new Intent(this, ListaAlunosActivity.class));
   }

//   private void configuraBotaoEscolheFoto() {
//        Button botaoEscolheFoto = findViewById(R.id.botao_escolher_foto);
//        botaoEscolheFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpeg";
//                File arquivoFoto = new File(caminhoFoto);
//                abreCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
//                startActivityForResult(abreCamera, CODIGO_CAMERA);
//            }
//        });
//    }

}
