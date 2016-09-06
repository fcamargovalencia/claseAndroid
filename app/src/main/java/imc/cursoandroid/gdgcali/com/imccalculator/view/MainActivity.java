package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.adapter.ResultAdapter;
import imc.cursoandroid.gdgcali.com.imccalculator.adapter.ResultRecyclerAdapter;
import imc.cursoandroid.gdgcali.com.imccalculator.api.Server;
import imc.cursoandroid.gdgcali.com.imccalculator.model.ResultModel;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.ObligationsModel;
import imc.cursoandroid.gdgcali.com.imccalculator.model.wp.RecentPostAnswer;
import imc.cursoandroid.gdgcali.com.imccalculator.util.EnvironmentFields;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {
    Context context;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_rv);
        database = FirebaseDatabase.getInstance().getReference();
        myRef = database.child("condition");

        ButterKnife.bind(this);
        context = this;
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
        lstResult.add(resultModel);
        //adapter = new ResultAdapter(lstResult, context);
        //lvResults.setAdapter(adapter);

        adapterRecycler = new ResultRecyclerAdapter(lstResult, context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterRecycler);


        peso.setText("");
        altura.setText("");

        uploadFirebase(resultModel);
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
                Toast.makeText(context, "Nueva info...", Toast.LENGTH_LONG).show();
                ResultModel firebaseRM = dataSnapshot.getValue(ResultModel.class);


                adapterRecycler = new ResultRecyclerAdapter(lstResult, context);
                recyclerView.setAdapter(adapterRecycler);

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
}
