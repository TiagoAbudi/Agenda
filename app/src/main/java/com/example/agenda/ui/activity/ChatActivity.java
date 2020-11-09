package com.example.agenda.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agenda.R;
import com.example.agenda.model.Contact;
import com.example.agenda.model.Message;
import com.example.agenda.model.Notification;
import com.example.agenda.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import java.util.List;

public class ChatActivity extends AppCompatActivity {

   private GroupAdapter adapter;
   private String nomeCompleto;
   private User user;
   private User me;
   private EditText campoChat;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_chat);

      user = getIntent().getExtras().getParcelable("user");

      nomeCompleto = user.getNome() + " " + user.getSobrenome();

      getSupportActionBar().setTitle(nomeCompleto);

      RecyclerView rv = findViewById(R.id.activity_chat_recycler);

      campoChat = findViewById(R.id.activity_chat_campo_mensagem);
      Button botaoEnviar = findViewById(R.id.activity_chat_botao_enviar);
      botaoEnviar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            sendMessage();
         }
      });

      adapter = new GroupAdapter();
      rv.setLayoutManager(new LinearLayoutManager(this));
      rv.setAdapter(adapter);

      FirebaseFirestore.getInstance().collection("/usuarios")
              .document(FirebaseAuth.getInstance().getUid())
              .get()
              .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot documentSnapshot) {
                    me = documentSnapshot.toObject(User.class);
                    fetchMessages();
                 }
              });

   }

   private void fetchMessages() {
      if (me != null) {

         String fromId = me.getUid();
         String toId = user.getUid();

         FirebaseFirestore.getInstance().collection("/conversations")
                 .document(fromId)
                 .collection(toId)
                 .orderBy("timestap", Query.Direction.ASCENDING)
                 .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                       List<DocumentChange> documentChanges = queryDocumentSnapshots.getDocumentChanges();
                       if (documentChanges != null) {
                          for (DocumentChange doc : documentChanges) {
                             if (doc.getType() == DocumentChange.Type.ADDED) {
                                Message message = doc.getDocument().toObject(Message.class);
                                adapter.add(new MessageItem(message));
                             }
                          }
                       }
                    }
                 });
      }
   }

   private void sendMessage() {
      String texto = campoChat.getText().toString();

      campoChat.setText(null);

      final String fromId = FirebaseAuth.getInstance().getUid();
      final String toId = user.getUid();
      long timestap = System.currentTimeMillis();

      final Message message = new Message();
      message.setFromId(fromId);
      message.setToId(toId);
      message.setTimestap(timestap);
      message.setText(texto);

      if (!message.getText().isEmpty()) {
         FirebaseFirestore.getInstance().collection("/conversations")
                 .document(fromId)
                 .collection(toId)
                 .add(message)
                 .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       Log.d("Teste", documentReference.getId());

                       Contact contact = new Contact();
                       contact.setUid(toId);
                       contact.setNome(nomeCompleto);
                       contact.setFoto(user.getFoto());
                       contact.setTimestamp(message.getTimestap());
                       contact.setUltimaMensagem(message.getText());

                       FirebaseFirestore.getInstance().collection("/last-messages")
                               .document(fromId)
                               .collection("contacts")
                               .document(toId)
                               .set(contact);

                       if (!user.isOnline()) {
                          Notification notification = new Notification();
                          notification.setFromId(message.getFromId());
                          notification.setToId(message.getToId());
                          notification.setTimestap(message.getTimestap());
                          notification.setText(message.getText());
                          notification.setFromName(me.getNome());

                          FirebaseFirestore.getInstance().collection("/notifications")
                                  .document(user.getToken())
                                  .set(notification);
                       }

                    }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Log.d("Teste", e.getMessage(), e);
                    }
                 });

         FirebaseFirestore.getInstance().collection("/conversations")
                 .document(toId)
                 .collection(fromId)
                 .add(message)
                 .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                       Log.d("Teste", documentReference.getId());

                       Contact contact = new Contact();
                       contact.setUid(toId);
                       contact.setNome(nomeCompleto);
                       contact.setFoto(user.getFoto());
                       contact.setTimestamp(message.getTimestap());
                       contact.setUltimaMensagem(message.getText());

                       FirebaseFirestore.getInstance().collection("/last-messages")
                               .document(toId)
                               .collection("contacts")
                               .document(fromId)
                               .set(contact);

                    }
                 })
                 .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Log.d("Teste", e.getMessage(), e);
                    }
                 });
      }
   }

   private class MessageItem extends Item<ViewHolder> {

      private final Message message;

      private MessageItem(Message message) {
         this.message = message;
      }

      @Override
      public void bind(@NonNull ViewHolder viewHolder, int position) {
         TextView mensagem = viewHolder.itemView.findViewById(R.id.texto_message);
         ImageView foto = viewHolder.itemView.findViewById(R.id.image_message_chat_user);

         mensagem.setText(message.getText());

         Picasso.get()
                 .load(message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                         ? me.getFoto()
                         : user.getFoto())
                 .into(foto);
      }

      @Override
      public int getLayout() {
         return message.getFromId().equals(FirebaseAuth.getInstance().getUid())
                 ? R.layout.item_from_message
                 : R.layout.item_to_message;
      }
   }

}