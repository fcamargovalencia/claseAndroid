package imc.cursoandroid.gdgcali.com.imccalculator.api.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.Obligacion;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.TokenResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by joseberna on 22/08/16.
 */
public class APIImplements {
    public interface APIImplementService {
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

    APIImplementService service;
    List<Obligacion> lstObligacions;

    /**
     * getTokenResponse
     * Metodo encargado de traer devolver el token de autenticación
     *
     * @param @Body String json
     * @return Call<TokenResponse>
     * @author josefbernam@gmail.com
     */
    TokenResult tokenResult;
    String sbToken = "";

    public String getTokenAuth(String body) {


        Call<TokenResponse> token = service.getTokenResponse(body);
        token.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                TokenResponse tokenResponse = response.body();
                tokenResult = tokenResponse.getToken();
                sbToken = tokenResult.getToken().toString();
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });
        return sbToken;
    }


    /**
     * getJSONObligaciones
     * Metodo encargado de traer todas las obligaciones activas de un cliente
     *
     * @param @Body String bodyToken
     * @param @Body String body
     * @return Call<JSONResponse>
     * @author josefbernam@gmail.com
     */
    public List<Obligacion> getObligationsByClient(String bodytoken, String body) {
        try {
            String token = getTokenAuth(bodytoken);
            if (token != null) {//TODO: Validaciones del token
                Call<JSONResponse> responseCall = service.getJSONObligaciones(body);
                responseCall.enqueue(new Callback<JSONResponse>() {
                    @Override
                    public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                        JSONResponse resp = response.body();
                        lstObligacions = new ArrayList<Obligacion>(Arrays.asList(resp.getResult_array()));

                    }

                    @Override
                    public void onFailure(Call<JSONResponse> call, Throwable t) {

                    }
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lstObligacions;
    }


}
