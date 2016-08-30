package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import imc.cursoandroid.gdgcali.com.imccalculator.R;

public class AdminUserFBActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_fb);
        mAuth = FirebaseAuth.getInstance();
    }
}
