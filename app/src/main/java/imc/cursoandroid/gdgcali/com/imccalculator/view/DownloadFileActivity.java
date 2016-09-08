package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import imc.cursoandroid.gdgcali.com.imccalculator.R;

public class DownloadFileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fcmclaseiagree.appspot.com");



    }

}
