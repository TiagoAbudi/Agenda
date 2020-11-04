package com.example.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class ContatosActivity extends AppCompatActivity {

   private GroupAdapter adapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_contatos);

      RecyclerView rv = findViewById(R.id.activity_contatos_recycler);

      adapter = new GroupAdapter();
      rv.setAdapter(adapter);
      rv.setLayoutManager(new LinearLayoutManager(this));

      adapter.setOnItemClickListener(new OnItemClickListener() {
         @Override
         public void onItemClick(@NonNull Item item, @NonNull View view) {
            Intent intent = new Intent(ContatosActivity.this, ChatActivity.class);

            UserItem userItem = (UserItem) item;
            intent.putExtra("user", userItem.user);

            startActivity(intent);
         }
      });

      buscaUsuarios();
   }

   private void buscaUsuarios() {
      FirebaseFirestore.getInstance().collection("/usuarios")
              .addSnapshotListener(new EventListener<QuerySnapshot>() {
                 @Override
                 public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                       Log.e("Teste", e.getMessage(), e);
                       return;
                    }

                    List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                    adapter.clear();
                    for (DocumentSnapshot doc : docs) {
                       User user = doc.toObject(User.class);
                       String uid = FirebaseAuth.getInstance().getUid();
                       if (user.getUid().equals(uid))
                          continue;
                       adapter.add(new UserItem(user));
                       adapter.notifyDataSetChanged();
                    }
                 }
              });
   }

   private class UserItem extends Item<ViewHolder> {

      private final User user;

      private UserItem(User user) {
         this.user = user;
      }

      @Override
      public void bind(@NonNull ViewHolder viewHolder, int position) {
         TextView nome = viewHolder.itemView.findViewById(R.id.item_usuario_nome);
         ImageView foto = viewHolder.itemView.findViewById(R.id.item_usuario_foto);
         String nomeCompleto = user.getNome() + " " + user.getSobrenome();

         nome.setText(nomeCompleto);

         Picasso.get()
                 .load(user.getFoto())
                 .into(foto);
      }

      @Override
      public int getLayout() {
         return R.layout.item_usuario;
      }
   }

}