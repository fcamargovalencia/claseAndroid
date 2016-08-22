package imc.cursoandroid.gdgcali.com.imccalculator.util;

import android.app.Application;

import imc.cursoandroid.gdgcali.com.imccalculator.api.Server;

/**
 * Created by joseberna on 16/08/16.
 */
public class IMCCalculatorAplication extends Application {
    @Override
    public void onCreate() {
        Server.getInstance().init(EnvironmentFields.SERVER_IAGREE, this);

    }
}
