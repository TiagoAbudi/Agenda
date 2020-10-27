package com.example.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.agenda.R;
import com.example.agenda.domain.Address;

import java.util.List;

public class AddressAdapter extends BaseAdapter {

   private LayoutInflater inflater;
   private List<Address> addresses;

   public AddressAdapter(Context context, List<Address> addresses) {
      inflater = LayoutInflater.from(context);
      this.addresses = addresses;
   }

   @Override
   public int getCount() {
      return addresses.size();
   }

   @Override
   public Object getItem(int i) {
      return addresses.get(i);
   }

   @Override
   public long getItemId(int i) {
      return i;
   }

   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {
      ViewHolder holder;

      if (view == null) {
         view = inflater.inflate(R.layout.address_item, null);
         holder = new ViewHolder();
         view.setTag(holder);
         holder.setViews(view);
      } else {
         holder = (ViewHolder) view.getTag();
      }

      holder.setData(addresses.get(i));

      return view;
   }


   private static class ViewHolder {
      TextView cep;
      TextView rua;
      TextView bairro;

      private void setViews(View view) {
         cep = view.findViewById(R.id.address_item_text_view_cep);
         rua = view.findViewById(R.id.address_item_text_view_rua);
         bairro = view.findViewById(R.id.address_item_text_view_bairro);
      }

      private void setData(Address address) {
         cep.setText("CEP: " + address.getCep());
         rua.setText("Rua: " + address.getLogradouro());
         bairro.setText("Bairro: " + address.getBairro());
      }
   }

}