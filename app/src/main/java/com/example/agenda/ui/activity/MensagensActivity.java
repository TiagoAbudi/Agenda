package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.ChatApplication;
import com.example.agenda.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class MensagensActivity extends AppCompatActivity {

   private GroupAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_mensagens);

      ChatApplication application = (ChatApplication) getApplication();

      getApplication().registerActivityLifecycleCallbacks(application);

      RecyclerView rv = findViewById(R.id.recycler_contact);
      rv.setLayoutManager(new LinearLayoutManager(this));

      adapter = new GroupAdapter();
      rv.setAdapter(adapter);

      updateToken();

      fetchLastMessage();
   }

   private void updateToken() {
      String token = FirebaseInstanceId.getInstance().getToken();
      String uid = FirebaseAuth.getInstance().getUid();

      if (uid != null) {
         FirebaseFirestore.getInstance().collection("usuarios")
                 .document(uid)
                 .update("token", token);
      }
   }

   private void fetchLastMessage() {
      String uid = FirebaseAuth.getInstance().getUid();

      FirebaseFirestore.getInstance().collection("/last-messages")
              .document(uid)
              .collection("contacts")
              .addSnapshotListener(new EventListener<QuerySnapshot>() {
                 @Override
                 public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                    List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();

                    if (documentChanges != null) {
                       for (DocumentChange doc : documentChanges) {
                          if (doc.getType() == DocumentChange.Type.ADDED) {
                             Contact contact = doc.getDocument().toObject(Contact.class);

                             adapter.add(new ContactItem(contact));

                          }
                       }
                    }
                 }
              });
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.activity_mensagens_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.mensagens_contatos) {
         Intent intent = new Intent(MensagensActivity.this, ContatosActivity.class);
         startActivity(intent);
      }
      return super.onOptionsItemSelected(item);
   }

   private class ContactItem extends Item<ViewHolder> {

      private final Contact contact;

      private ContactItem(Contact contact) {
         this.contact = contact;
      }

      @Override
      public void bind(@NonNull ViewHolder viewHolder, int position) {
         TextView nome = viewHolder.itemView.findViewById(R.id.item_usuario_nome);
         TextView ultimaMensagem = viewHolder.itemView.findViewById(R.id.item_usuario_ultima_mensagem);
         ImageView foto = viewHolder.itemView.findViewById(R.id.item_usuario_foto);

         nome.setText(contact.getNome());
         ultimaMensagem.setText(contact.getUltimaMensagem());
         Picasso.get()
                 .load(contact.getFoto())
                 .into(foto);
      }

      @Override
      public int getLayout() {
         return R.layout.item_usuario_mensagem;
      }
   }

}