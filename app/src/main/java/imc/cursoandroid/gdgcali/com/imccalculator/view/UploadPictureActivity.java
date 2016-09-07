package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.util.EnvironmentFields;

public class UploadPictureActivity extends Activity {
    private Context context;
    private static final int CAMERA_REQUEST = 1888;
    // @BindView(R.id.imageView1)
    ImageView imgPicture;

    File sdImageMainDirectory;
    Uri outputFileUri;
    Bitmap image;
    StorageReference picturesRef;
    int cont = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);
        ButterKnife.bind(this);

        final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fcmclaseiagree.appspot.com");


        imgPicture = (ImageView) findViewById(R.id.imageView1);
        Button button = (Button) findViewById(R.id.button1);
        Button buttonGalery = (Button) findViewById(R.id.buttongalery);
        Button buttonUpload = (Button) findViewById(R.id.button_upload);
        Button download = (Button) findViewById(R.id.button_download);
        context = this;
        File root = new File(Environment
                .getExternalStorageDirectory()
                + File.separator + "myDir" + File.separator);
        root.mkdirs();
        sdImageMainDirectory = new File(root, "myPicName");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputFileUri = Uri.fromFile(sdImageMainDirectory);
                    Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                        takePictureIntent.putExtra("return-data", true);
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                        startActivityForResult(takePictureIntent, CAMERA_REQUEST);

                    }
                } catch (Exception ex) {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            }
        });

        buttonGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, EnvironmentFields.PIC_PICK_FROM_FILE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent, EnvironmentFields.PIC_PICK_FROM_FILE);
                    }
                } catch (Exception ex) {
                    ex.getMessage();
                }
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    picturesRef = storageRef.child("images/" + cont);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap bitmap = imgPicture.getDrawingCache();
//                    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] dataByte = baos.toByteArray();
//                    UploadTask uploadTask = storageRef.putBytes(dataByte);
                    UploadTask uploadTask = picturesRef.putBytes(dataByte);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            cont++;
                            Toast.makeText(context, downloadUrl.toString(), Toast.LENGTH_LONG).show();
                        }
                    });


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intNext = new Intent(context, DownloadFileActivity.class);
                startActivity(intNext);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case CAMERA_REQUEST:
                    try {
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        image = imageBitmap;
                        imgPicture.setImageBitmap(imageBitmap);
                        imgPicture.setDrawingCacheEnabled(true);
                        imgPicture.buildDrawingCache();
                        image = imgPicture.getDrawingCache();
                        Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        ex.getMessage();
                    }
                    break;
                case EnvironmentFields.PIC_PICK_FROM_FILE:
                    try {
                        Uri originalUri = data.getData();
                        if (originalUri != null) {
                            Picasso.with(context).load(originalUri).into(imgPicture);
                            //Utilities.startCropActivity(originalUri, outputUri, this);
                            Log.i("crop", "llamando activity crop desde PIC_PICK_FROM_FILE");
//                            new Crop(originalUri).output(outputUri).asSquare().start(this);
                        }
                    } catch (Exception ex) {
                        ex.getMessage();
                    }
                    break;
            }


        }
    }


}
