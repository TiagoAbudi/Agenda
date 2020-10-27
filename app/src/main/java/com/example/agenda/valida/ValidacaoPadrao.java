package com.example.agenda.valida;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class ValidacaoPadrao implements Validador {

   private static final String CAMPO_OBRIGATORIO = "Campo obrigatório";
   private final TextInputLayout textInputCampo;
   private final EditText campo;

   public ValidacaoPadrao(TextInputLayout textInputCampo) {
      this.textInputCampo = textInputCampo;
      this.campo = this.textInputCampo.getEditText();
   }

   protected boolean validaCampoObrigatorio() {
      String texto = campo.getText().toString();
      if (texto.isEmpty()) {
         textInputCampo.setError(CAMPO_OBRIGATORIO);
         return false;

      }
      return true;
   }

   public boolean estaValido() {
      if (!validaCampoObrigatorio()) return false;
      removeErro();
      return true;
   }

   private void removeErro() {
      textInputCampo.setError(null);
      textInputCampo.setErrorEnabled(false);
   }
}