package com.example.agenda.ui.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.model.Mensagem;

import java.util.List;

public class MensagemAdapter extends BaseAdapter {

    private final List<Mensagem> mensagens;
    private final Activity activity;
    private final int idDoCliente;

    public MensagemAdapter(int idDoCliente, List<Mensagem> mensagens, Activity activity) {
        this.mensagens = mensagens;
        this.activity = activity;
        this.idDoCliente = idDoCliente;
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Mensagem getItem(int i) {
        return mensagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = activity.getLayoutInflater().inflate(R.layout.mensagem, viewGroup, false);

        TextView texto = linha.findViewById(R.id.text_view_texto);

        Mensagem mensagem = getItem(i);

        if (idDoCliente != mensagem.getId()) {
            linha.setBackgroundColor(Color.DKGRAY);
        }

        texto.setText(mensagem.getTexto());

        return linha;
    }
}
