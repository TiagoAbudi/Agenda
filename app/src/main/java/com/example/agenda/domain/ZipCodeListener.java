package com.example.agenda.domain;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.example.agenda.ui.activity.FormularioAlunoActivity;

public class ZipCodeListener implements TextWatcher {
    private Context context;

    public ZipCodeListener(Context context) {
        this.context = context;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String zipCode = editable.toString();

        if (editable.length() == 8) {
            new AddressRequest((FormularioAlunoActivity) context).execute();
        }
    }
}
