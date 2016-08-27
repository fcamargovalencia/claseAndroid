package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class LoginFbActivity extends AppCompatActivity {

    Context context;
    private FirebaseAuth mFireBaseAuth;

    @BindView(R.id.id_email)
    EditText email;

    @BindView(R.id.id_pass)
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fb);
        mFireBaseAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        context = this;
    }

    @OnClick(R.id.id_buttonLogin)
    public void loginFbAction(){

        String emailResult = email.getText().toString();
        String passResult = pass.getText().toString();
        mFireBaseAuth.signInWithEmailAndPassword(emailResult, passResult).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(LoginFbActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginFbActivity.this, "Authentication Success.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginFbActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });
    }
}
