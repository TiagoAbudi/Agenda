package com.example.agenda.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.agenda.R;
import com.example.agenda.database.BD;
import com.example.agenda.formatter.FormataData;
import com.example.agenda.formatter.FormataTelefoneComDdd;
import com.example.agenda.model.Aluno;
import com.example.agenda.valida.ValidaData;
import com.example.agenda.valida.ValidaEmail;
import com.example.agenda.valida.ValidaTelefoneComDdd;
import com.example.agenda.valida.ValidacaoPadrao;
import com.example.agenda.valida.Validador;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.agenda.ui.activity.ContantesActivities.CHAVE_ALUNO;


public class FormularioAlunoActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo Aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Editar Aluno";
    private final List<Validador> validadores = new ArrayList<>();
    private Aluno aluno;
    private TextInputLayout textInputNome;
    private TextInputLayout textInputSobrenome;
    private TextInputLayout textInputData;
    private TextInputLayout textInputTelefoneComDdd;
    private TextInputLayout textInputEmail;
    private ImageView fotoDePerfil;
    private String caminhoFoto;
    private Intent abreCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializacaoDosCampos();
        carregaAluno();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializacaoDosCampos() {
        setaViews();
        iniciaCampoNome();
        iniciaCampoSobremome();
        iniciaCampoData();
        iniciaCampoTelefone();
        iniciaCampoEmail();
        configuraBotaoFoto();
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
        }
        if (aluno.naoTemIdValido()) {
            bd.inserir(aluno);
        }
        voltaParaListaDeAlunos();
    }

    private void preencheCampos() {
        textInputNome.getEditText().setText(aluno.getNome());
        textInputSobrenome.getEditText().setText(aluno.getSobrenome());
        textInputData.getEditText().setText(aluno.getData());
        textInputTelefoneComDdd.getEditText().setText(aluno.getTelefone());
        textInputEmail.getEditText().setText(aluno.getEmail());
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
        boolean formularioEstaValido = true;
        for (Validador validador :
                validadores) {
            if (!validador.estaValido()) {
                formularioEstaValido = false;
            }
        }
        return formularioEstaValido;
    }

    private void voltaParaListaDeAlunos() {
        startActivity(new Intent(this, ListaAlunosActivity.class));
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
        EditText campoEmail = textInputEmail.getEditText();
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

    private void iniciaFotoDePerfil() {
        ImageView campoFoto = fotoDePerfil;
    }

    private void preencheAluno() {
        String nome = textInputNome.getEditText().getText().toString();
        String sobrenome = textInputSobrenome.getEditText().getText().toString();
        String data = textInputData.getEditText().getText().toString();
        String telefone = textInputTelefoneComDdd.getEditText().getText().toString();
        String email = textInputEmail.getEditText().getText().toString();

        aluno.setNome(nome);
        aluno.setSobrenome(sobrenome);
        aluno.setData(data);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }


    private void setaViews() {
        textInputNome = findViewById(R.id.activity_formulario_aluno_nome);
        textInputSobrenome = findViewById(R.id.activity_formulario_aluno_sobrenome);
        textInputData = findViewById(R.id.activity_formulario_data_de_nascimento);
        textInputTelefoneComDdd = findViewById(R.id.activity_formulario_aluno_telefone);
        textInputEmail = findViewById(R.id.activity_formulario_aluno_email);
        fotoDePerfil = findViewById(R.id.imagem_de_perfil);
    }

    private void configuraBotaoFoto() {
        Button botaoFoto = findViewById(R.id.botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int MY_PERMISSIONS_REQUEST_CAMERA = 0;
                if (validaPermissao()) {
                    caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpeg";
                    File arquivoFoto = new File(caminhoFoto);
                    abreCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                    startActivityForResult(abreCamera, CODIGO_CAMERA);
                } else {
                    ActivityCompat.requestPermissions(FormularioAlunoActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CODIGO_CAMERA) {
            caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpeg";
            File arquivoFoto = new File(caminhoFoto);
            abreCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
            ImageView foto = findViewById(R.id.imagem_de_perfil);
            int larguraFoto;
            int alturaFoto;
            larguraFoto = foto.getWidth();
            alturaFoto = foto.getHeight();
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, larguraFoto, alturaFoto, true);
            foto.setImageBitmap(bitmapReduzido);
            startActivityForResult(abreCamera, CODIGO_CAMERA);

        }
    }

    public boolean validaPermissao() {
        if (ContextCompat.checkSelfPermission(FormularioAlunoActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}
