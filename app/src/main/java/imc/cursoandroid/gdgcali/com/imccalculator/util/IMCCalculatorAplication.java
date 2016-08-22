package imc.cursoandroid.gdgcali.com.imccalculator.util;

import android.app.Application;

import imc.cursoandroid.gdgcali.com.imccalculator.api.Server;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by joseberna on 16/08/16.
 */
public class IMCCalculatorAplication extends Application {
    private static RestAdapter restAdapter;
    private IMCCalculatorAplication aplication;

    @Override
    public void onCreate() {
        Server.getInstance().init(EnvironmentFields.SERVER_IAGREE, this);

    }
}
