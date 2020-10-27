package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agenda.R;
import com.example.agenda.domain.Address;
import com.example.agenda.domain.Util;
import com.example.agenda.domain.ZipCodeRequest;
import com.example.agenda.ui.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.agenda.domain.Address.ZIP_CODE_KEY;

public class ProcuraCepActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

   private static final String TITULO_APPBAR = "Procura CEP";
   private Spinner spinnerStates;
   private ListView listViewAddress;
   private List<Address> addresses;
   private Util util;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_procura_cep);
      setTitle(TITULO_APPBAR);

      addresses = new ArrayList<>();
      listViewAddress = (ListView) findViewById(R.id.activity_procura_cep_list_view_address);
      AddressAdapter adapter = new AddressAdapter(this, addresses);
      listViewAddress.setAdapter(adapter);
      listViewAddress.setOnItemClickListener(this);

      spinnerStates = (Spinner) findViewById(R.id.activity_procura_cep_spinner);
      spinnerStates.setAdapter(ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item));

      util = new Util(this,
              R.id.activity_procura_cep_edit_text_rua,
              R.id.activity_procura_cep_edit_text_cidade,
              R.id.activity_procura_cep_spinner);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater()
              .inflate(R.menu.volta_para_o_menu, menu);
      return super.onCreateOptionsMenu(menu);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.volta_menu) {
         finish();
      }
      return super.onOptionsItemSelected(item);
   }

   @Override
   public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
      String[] zipCodeArray = addresses.get(i).getCep().split("-");
      String zipCode = zipCodeArray[0] + zipCodeArray[1];

      Intent intent = new Intent();
      intent.putExtra(ZIP_CODE_KEY, zipCode);
      setResult(RESULT_OK, intent);
      finish();
   }

   public void lockFields(boolean isToLock) {
      util.lockFields(isToLock);
   }

   private String getState() {
      String[] stateArray = ((String) spinnerStates.getSelectedItem()).split("\\(");

      if (stateArray.length == 2) {
         stateArray = stateArray[1].split("\\)");
         return stateArray[0];
      }
      return "";
   }

   private String getCity() {
      return ((EditText) findViewById(R.id.activity_procura_cep_edit_text_cidade)).getText().toString();
   }

   private String getStreet() {
      return ((EditText) findViewById(R.id.activity_procura_cep_edit_text_rua)).getText().toString();
   }

   public String getUriZipCode() {
      String uri = "https://viacep.com.br/ws/";
      uri += getState() + "/";
      uri += getCity() + "/";
      uri += getStreet() + "/json/";
      return uri;
   }

   public List<Address> getAddresses() {
      return addresses;
   }

   public void updateListView() {
      ((AddressAdapter) listViewAddress.getAdapter()).notifyDataSetChanged();
   }

   public void searchAddress(View view) {
      new ZipCodeRequest(this).execute();
   }

}