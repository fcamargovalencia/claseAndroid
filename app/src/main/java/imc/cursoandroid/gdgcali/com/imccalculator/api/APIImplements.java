package imc.cursoandroid.gdgcali.com.imccalculator.api;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import imc.cursoandroid.gdgcali.com.imccalculator.api.response.JSONResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.api.response.LoginResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.api.response.TokenResponse;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.Obligacion;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.TokenResult;
import imc.cursoandroid.gdgcali.com.imccalculator.util.EnvironmentFields;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by joseberna on 22/08/16.
 */
public class APIImplements {


    TokenResult tokenResult;
    String sbToken = "";

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


    /**
     * getRetrofitService
     * Metodo encargado de crear la instancia del retrofit2
     * @return APIImplementService
     * @author fv@iagree.co
     */
    public APIImplementService serviceRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EnvironmentFields.SERVER_IAGREE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        APIImplementService service = retrofit.create(APIImplementService.class);
        return service;
    }

    /**
     * getTokenResponse
     * Metodo encargado de traer devolver el token de autenticación
     * @param @Body String json
     * @return Call<TokenResponse>
     * @author josefbernam@gmail.com
     */
    public String getTokenAuth(String body) throws IOException {
        //Adicionar con ssl

        final APIImplementService service = serviceRetrofit();

        Call<TokenResponse> token = service.getTokenResponse(body);

        TokenResponse tokenResponse = token.execute().body();

        tokenResult = tokenResponse.getToken();
        sbToken = tokenResult.getToken().toString();


        return sbToken;
    }


    /**
     * getListaObligacionService
     * @param consulta se recibe los parametros de consulta de servicio
     * @return retorna el listado de obligaciones
     * @author fv@iagree.co
     */
    public List<Obligacion> getListaObligacionService(String consulta) {
        List<Obligacion> lstObligacions;
        lstObligacions = new ArrayList<>();
        try {
            final APIImplementService service = serviceRetrofit();
            Call<JSONResponse> responseCall = service.getJSONObligaciones(consulta);
            JSONResponse response = null;
            response = responseCall.execute().body();
            lstObligacions = new ArrayList<>(Arrays.asList(response.getResult_array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lstObligacions;
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
    public List<Obligacion> getObligationsByClient(String bodytoken) {
        try {
            return new LongOperation().execute(bodytoken).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }


    private class LongOperation extends AsyncTask<String, Void, List<Obligacion>> {

        List<Obligacion> lstObligacions;

        /**
         * doInBackground implementa los metodos getTokenAuth y gettListaObligacionService de
         * manera asincrona
         * @param params valores para las consultas del token y de las obligaciones
         * @return List<Obligacion> retorna el listado de obligaciones encontrado
         */
        @Override
        protected List<Obligacion> doInBackground(String... params) {

            lstObligacions = new ArrayList<>();
            try {

                sbToken = getTokenAuth(params[0]);


                if (sbToken != null) {//TODO: Validaciones del token

                    String armarQuery = "{";
                    armarQuery = armarQuery + "'persistenceName': '" + EnvironmentFields.PERSISTENCE_NAME + "',";
                    armarQuery = armarQuery + "'token': '" + sbToken + "',";
                    armarQuery = armarQuery + "'document_number_user': '" + EnvironmentFields.DOCUMENT_NUMBER_USER + "',";
                    armarQuery = armarQuery + "'document_type_user': '" + EnvironmentFields.DOCUMENT_TYPE_USER + "'}";

                    lstObligacions = getListaObligacionService(armarQuery);
                }

            } catch (Exception e) {
                Thread.interrupted();
            }
            return lstObligacions;
        }

        /**
         * onPostExecute ejecucion despues del doInBackground
         * @param result listado de obligaciones retornado en doInBackground
         */
        @Override
        protected void onPostExecute(List<Obligacion> result) {
            lstObligacions = result;

        }
    }
}
