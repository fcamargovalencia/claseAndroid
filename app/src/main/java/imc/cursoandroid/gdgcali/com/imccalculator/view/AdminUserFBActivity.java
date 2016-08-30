package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imc.cursoandroid.gdgcali.com.imccalculator.R;

public class AdminUserFBActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @BindView(R.id.id_nombre_singup)
    EditText nombre;

    @BindView(R.id.id_pass_singup)
    EditText pass;

//    @BindView(R.id.create_user)
//    Button btnCreate;

    Context context;
    Resources RES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        RES = getResources();
        setContentView(R.layout.activity_admin_user_fb);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.create_user)
    public void createUserFirebase() {
        mAuth.createUserWithEmailAndPassword(nombre.getText().toString(), pass.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(context, "Ingresó usuario " + task.isSuccessful(), Toast.LENGTH_LONG).show();
            }
        });


    }
}
