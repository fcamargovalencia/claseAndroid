package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
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

public class LoginFbActivity extends Activity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    Context context;
    private FirebaseAuth mFireBaseAuth;

    @BindView(R.id.id_email)
    EditText email;

    @BindView(R.id.id_pass)
    EditText pass;

    @BindView(R.id.id_buttonLogin)
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fb);
        mFireBaseAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
        context = this;
    }


    @OnClick(R.id.id_buttonLogin)
    public void login() {

        // TODO: Implement your own authentication logic here.

        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        buttonLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginFbActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String correo = email.getText().toString();
        String password = pass.getText().toString();

        mFireBaseAuth.signInWithEmailAndPassword(correo, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Toast.makeText(LoginFbActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    onLoginFailed();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(LoginFbActivity.this, "Authentication Success.", Toast.LENGTH_SHORT).show();
                    onLoginSuccess();
                    // onLoginFailed();
                    progressDialog.dismiss();
                }
            }

        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        buttonLogin.setEnabled(true);
        Intent intent = new Intent(LoginFbActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginFbActivity.this, AdminUserFBActivity.class);
        startActivity(intent);
//        buttonLogin.setEnabled(true);
        finish();

    }

    public boolean validate() {
        boolean valid = true;

        String correo = email.getText().toString();
        String password = pass.getText().toString();

        if (correo.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 12) {
            pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            pass.setError(null);
        }

        return valid;
    }
}
