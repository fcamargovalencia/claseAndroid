package imc.cursoandroid.gdgcali.com.imccalculator.util;

import android.app.Application;
import android.content.SharedPreferences;
import imc.cursoandroid.gdgcali.com.imccalculator.api.Server;
import retrofit.RestAdapter;

/**
 * Created by joseberna on 16/08/16.
 */
public class IMCCalculatorAplication extends Application {
    private static RestAdapter restAdapter;
    private IMCCalculatorAplication aplication;

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        Server.getInstance().init(EnvironmentFields.SERVER_IAGREE, this);
//        saveFieldConstants();
        sharedPreferences = getApplicationContext().getSharedPreferences(EnvironmentFields.PREF_SP, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(EnvironmentFields.SP_LOGIN, EnvironmentFields.SP_LOGIN_CONSTANTE);
        editor.putString(EnvironmentFields.SP_PASS, EnvironmentFields.SP_PASS_CONSTANTE);

        editor.commit();

    }

    private void saveFieldConstants() {

    }
}
