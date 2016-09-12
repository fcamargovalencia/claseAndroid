package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.access.IagreeDBHandler;
import imc.cursoandroid.gdgcali.com.imccalculator.adapter.ResultAdapter;
import imc.cursoandroid.gdgcali.com.imccalculator.adapter.ResultRecyclerAdapter;
import imc.cursoandroid.gdgcali.com.imccalculator.api.Server;
import imc.cursoandroid.gdgcali.com.imccalculator.model.ResultModel;
import imc.cursoandroid.gdgcali.com.imccalculator.model.dao.ResultModelDAO;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.ObligationsModel;
import imc.cursoandroid.gdgcali.com.imccalculator.model.wp.RecentPostAnswer;
import imc.cursoandroid.gdgcali.com.imccalculator.util.EnvironmentFields;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {
    static Context context;
    DatabaseReference database;
    DatabaseReference myRef;


    double dPeso, dAltura, dIMC;
    ResultRecyclerAdapter adapterRecycler;
    List<ResultModel> lstResult;


    @BindView(R.id.id_et_peso)
    EditText peso;

    @BindView(R.id.id_et_altura)
    EditText altura;

//    @BindView(R.id.id_lstview)
//    ListView lvResults;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    IagreeDBHandler dbHandler;
    static ResultModelDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rv);
        database = FirebaseDatabase.getInstance().getReference();
        //myRef = database.child("condition");

        ButterKnife.bind(this);
        context = this;
        dbHandler = new IagreeDBHandler(context,22);
        lstResult = new ArrayList<>();
        //loadRecentPost();
        //loadObligations();

    }

    @OnClick(R.id.id_btn_camara)
    public void takePicture() {
        try {
            Intent intCamara = new Intent(this, UploadPictureActivity.class);
            startActivity(intCamara);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @OnClick(R.id.id_btn_calcular)
    public void calcular() {
        Toast.makeText(context, "Calculando...", Toast.LENGTH_LONG).show();
        dPeso = Double.parseDouble(peso.getText().toString());
        dAltura = Double.parseDouble(altura.getText().toString());
        dIMC = dPeso / (dAltura * dAltura);
        ResultModel resultModel = new ResultModel(dPeso, dAltura, dIMC);
        //lstResult.add(resultModel);
        //adapter = new ResultAdapter(lstResult, context);
        //lvResults.setAdapter(adapter);
        uploadFirebase(resultModel);
        try {
            saveRecordDB(resultModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        getRecord();
        IagreeDBHandler.closeConnection();
        //adapterRecycler = new ResultRecyclerAdapter(lstResult, context);
        peso.setText("");
        altura.setText("");


    }

    public static void getRecord() {
        try {
            dao = new ResultModelDAO(context);
            Cursor cursor = dao.getAllResultModels();
            ArrayList<ResultModel> mArrayList = new ArrayList<ResultModel>();
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                // The Cursor is now set to the right position
                Log.i("DB", cursor.getString(cursor.getColumnIndex("imc")));

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean saveRecordDB(ResultModel resultModel) {
        boolean save = false;
        try {
            dao = new ResultModelDAO(context);

            dao.saveRecord(resultModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return save;
    }

    private void uploadFirebase(Object resultModel) {
        try {
            ResultModel result = (ResultModel) resultModel;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();

            databaseReference.child(EnvironmentFields.PREF_IMC_SP).child(result.getId() + "").setValue(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void listenerFirebase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(EnvironmentFields.PREF_IMC_SP);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lstResult.clear();
                Toast.makeText(context, "Nueva info...", Toast.LENGTH_LONG).show();
                //Map<String, ResultModel> lstFirebaseModel = (Map<String, ResultModel>) dataSnapshot.getValue();
                if (dataSnapshot != null || dataSnapshot.getChildrenCount() > 0) {

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        ResultModel result = data.getValue(ResultModel.class);
                        lstResult.add(result);
                        adapterRecycler = new ResultRecyclerAdapter(lstResult, context);
                    }
                    cargarAdaptador();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.id_howisicm)
    public void howIs() {
        Toast.makeText(context, "Abriendo otro activity...", Toast.LENGTH_LONG).show();
        Intent intNext = new Intent(this, ChatActivity.class);
//        Bundle bdParams = new Bundle();
//        bdParams.putString(EnvironmentFields.K_PARAMS_NAME, "Tu Nombre");
//        bdParams.putDouble(EnvironmentFields.K_PARAMS_IMC, dIMC);
//        intNext.putExtras(bdParams);
//
//        intNext.putExtra(EnvironmentFields.K_PARAMS_NAME, "Hola");
        startActivity(intNext);

    }

    @OnClick(R.id.id_ws)
    public void webService() {
        Intent intent = new Intent(this, ObligationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        listenerFirebase();

    }

    public void cargarAdaptador() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterRecycler);
    }

    @OnClick(R.id.id_btn_maps)
    public void irAMaps(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}
