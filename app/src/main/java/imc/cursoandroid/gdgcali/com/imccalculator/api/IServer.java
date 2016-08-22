package imc.cursoandroid.gdgcali.com.imccalculator.api;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import imc.cursoandroid.gdgcali.com.imccalculator.api.response.JSONResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.api.response.LoginResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.api.response.TokenResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.ObligationsModel;
import imc.cursoandroid.gdgcali.com.imccalculator.model.wp.RecentPostAnswer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by joseberna on 16/08/16.
 */
public interface IServer {
    @GET("/")
    void GetRecentPost(@Query("json") String json, retrofit.Callback<ArrayList<RecentPostAnswer>> resp);


    @POST("/iAgree/rest/AcuerdosComWS/obtenerObligacionesiAgree")
    void getObligations(@Body String body, retrofit.Callback<List<ObligationsModel>> response);


    //Retrofit 2
    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("iAgree/rest/AcuerdosComWS/obtenerObligacionesiAgree")
    Call<JSONResponse> getJSONObligaciones(@Body String string);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("iAgree/rest/AcuerdosComWS/loginResult")
    Call<TokenResponse> getTokenResponse(@Body String json);

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @POST("iAgree/rest/AcuerdosComWS/login")
    Call<LoginResponse> getTokenLogin(@Body String json);

}
