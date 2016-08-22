package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.util.BarcodeScannerActivityController;
import imc.cursoandroid.gdgcali.com.imccalculator.util.CheckPermissionActivityController;
import imc.cursoandroid.gdgcali.com.imccalculator.util.EnvironmentFields;

public class DetailActivity extends Activity {

    @BindView(R.id.id_titulo)
    TextView titulo;

    @BindView(R.id.id_img)
    ImageView img;

    @BindView(R.id.btn_start_scann)
    Button scanner;

    Context context;
    Resources RES;
    String url_img = "http://square.github.io/picasso/static/sample.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        context = this;
        RES = getResources();

        Picasso.with(context).load(url_img).centerInside()
                .resize(200, 300).into(img);

        if (getIntent().getExtras() != null) {
            titulo.setText(getIntent().getExtras().getString(EnvironmentFields.K_PARAMS_NAME, ""));
        }
//        if (getIntent().hasExtra(EnvironmentFields.K_PARAMS_NAME)) {
//            titulo.setText(getIntent().getExtras().getString(EnvironmentFields.K_PARAMS_NAME, ""));
//        }

    }


    @OnClick(R.id.btn_start_scann)
    public void scannCam(View view) {
        try {
            if (CheckPermissionActivityController.checkPermission(context, Manifest.permission.CAMERA, null)) {
                BarcodeScannerActivityController.startScanner(this);
            } else {
                CheckPermissionActivityController.checkPermission(context, Manifest.permission.CAMERA,
                        new CheckPermissionActivityController.CheckingPermissionListener() {
                            @Override
                            public void onResult(boolean result) {
                                if (result) {
//                                    onClick(v);
                                } else {
//                                    Utilities.Toast(context, R.string.camera_permission_revoke);
                                }
                            }
                        });
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case IntentIntegrator.REQUEST_CODE:
                    boolean fine = false;
                    if (resultCode == Activity.RESULT_OK) {
                        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                        if (scanResult != null) {
                            String resultContents = scanResult.getContents();
                            if (resultContents != null) {
                                Toast.makeText(context, "Escaneado: " + resultContents, Toast.LENGTH_LONG).show();
                                Log.d("Escann", resultContents);
                            }
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
