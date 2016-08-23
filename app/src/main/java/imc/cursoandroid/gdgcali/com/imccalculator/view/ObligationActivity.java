package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.adapter.ObligacionDataAdapter;
import imc.cursoandroid.gdgcali.com.imccalculator.api.APIImplements;
import imc.cursoandroid.gdgcali.com.imccalculator.api.Server;
import imc.cursoandroid.gdgcali.com.imccalculator.api.response.JSONResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.api.response.LoginResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.api.response.TokenResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.Obligacion;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.TokenResult;
import imc.cursoandroid.gdgcali.com.imccalculator.util.EnvironmentFields;
import retrofit2.Call;

public class ObligationActivity extends Activity {

    Context context;
    APIImplements apiImplements;

    private List<Obligacion> listObligacion;
    private ObligacionDataAdapter obligacionAdapter;
    String loginResult = "";
    TokenResult tokenResult;
    String result = "";


    @BindView(R.id.card_recycler_view)
    RecyclerView recyclerCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card__layout);
        ButterKnife.bind(this);
        context = this;
        apiImplements = new APIImplements();
        initViews();
    }

    public List<Obligacion> getListObligacion() {
        return listObligacion;
    }

    public void setListObligacion(List<Obligacion> listObligacion) {
        this.listObligacion = listObligacion;
    }

    public RecyclerView getRecyclerCardView() {
        return recyclerCardView;
    }

    public void setRecyclerCardView(RecyclerView recyclerCardView) {
        this.recyclerCardView = recyclerCardView;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void initViews() {
        //recyclerCardView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerCardView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerCardView.setLayoutManager(layoutManager);
        cargarObligaciones();
    }

    public void cargarObligaciones() {


        String consulta = "{'login':'acuerdos.com','password':'aUFncmVlMUBjb20='}";



            listObligacion = new ArrayList<>();
            listObligacion = apiImplements.getObligationsByClient(consulta);
            if (listObligacion != null && listObligacion.size() > 0) {
                obligacionAdapter = new ObligacionDataAdapter(listObligacion, context);
                recyclerCardView.setAdapter(obligacionAdapter);
            }
        }



    public void cargarObligaciones2() {


        String consulta = "{'login':'acuerdos.com','password':'aUFncmVlMUBjb20='}";
        Call<LoginResponse> token = Server.getSingleton().getTokenLogin(consulta);
        token.enqueue(new retrofit2.Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                LoginResponse jsonResponse = response.body();
                loginResult = jsonResponse.getToken();
                if (tokenResult != null) {


                    String armarQuery = "{";
                    armarQuery = armarQuery + "'persistenceName': '" + EnvironmentFields.PERSISTENCE_NAME + "',";
                    armarQuery = armarQuery + "'token': '" + tokenResult + "',";
                    armarQuery = armarQuery + "'document_number_user': '" + EnvironmentFields.DOCUMENT_NUMBER_USER + "',";
                    armarQuery = armarQuery + "'document_type_user': '" + EnvironmentFields.DOCUMENT_TYPE_USER + "'}";


                    Call<JSONResponse> callObli = Server.getSingleton().getJSONObligaciones(armarQuery);
                    callObli.enqueue(new retrofit2.Callback<JSONResponse>() {
                        @Override
                        public void onResponse(Call<JSONResponse> call, retrofit2.Response<JSONResponse> response) {
                            JSONResponse jsonResponse = response.body();
                            listObligacion = new ArrayList<>(Arrays.asList(jsonResponse.getResult_array()));
                            if (listObligacion != null && listObligacion.size() > 0) {
                                obligacionAdapter = new ObligacionDataAdapter(listObligacion, context);
                                recyclerCardView.setAdapter(obligacionAdapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<JSONResponse> call, Throwable t) {
                            Log.d("Error", t.getMessage());
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });


    }
}