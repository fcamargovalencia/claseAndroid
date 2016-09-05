package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.adapter.ChatListAdapter;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.Chat;
import imc.cursoandroid.gdgcali.com.imccalculator.util.EnvironmentFields;

public class ChatActivity extends Activity {
    private FirebaseAuth mAuth;
    private Firebase firebase;
    private ChatListAdapter adapterChat;
    private ValueEventListener eventListener;



    @BindView(R.id.id_list_chat)
    ListView lstMensajes;

    @BindView(R.id.id_input_msj)
    EditText mensaje;

//    @BindView(R.id.create_user)
//    Button btnCreate;

    Context context;
    Resources RES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        firebase = new Firebase(EnvironmentFields.URL_FIREBASE_CHAT).child(EnvironmentFields.SUB_FIREBASE_CHAT);

        mensaje.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    enviarMensaje();
                }
                return false;
            }
        });

        context = this;
        RES = getResources();
    }


    @OnClick(R.id.id_send_button)
    public void enviarChat() {
        enviarMensaje();
    }

    public void enviarMensaje() {
        if (!mensaje.getText().toString().equals("")) {
            Chat chat = new Chat(mensaje.getText().toString(), "User XXX");
            firebase.push().setValue(chat);
            mensaje.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterChat = new ChatListAdapter(firebase.limit(50), this, R.layout.activity_message, "User XXX");
        lstMensajes.setAdapter(adapterChat);

        adapterChat.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                lstMensajes.setSelection(adapterChat.getCount() - 1);
            }
        });
        eventListener = firebase.getRoot().child(".info/conected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isConect = (boolean) dataSnapshot.getValue();
                if (isConect) {
                    Toast.makeText(context, "Conectado a firebase chat", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(context, "NO Conectado a firebase chat", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {


            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.getRoot().child(".info/conected").removeEventListener(eventListener);
        adapterChat.cleanup();
    }
}
