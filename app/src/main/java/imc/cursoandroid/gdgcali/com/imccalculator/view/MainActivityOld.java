package imc.cursoandroid.gdgcali.com.imccalculator.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import imc.cursoandroid.gdgcali.com.imccalculator.R;

import imc.cursoandroid.gdgcali.com.imccalculator.adapter.ResultAdapter;
import imc.cursoandroid.gdgcali.com.imccalculator.model.ResultModel;

public class MainActivityOld extends Activity implements View.OnClickListener {
    Context context;

    double dPeso, dAltura, dIMC;
    ResultAdapter adapter;
    List<ResultModel> lstResult;

    EditText edPeso;
    EditText edAlto;

    Button btnCalcular;
    Button btnCancelar;
    Button btnQueEsIMC;

    Resources RES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        edPeso = (EditText) findViewById(R.id.id_et_peso);
        edAlto = (EditText) findViewById(R.id.id_et_altura);

        btnCalcular = (Button) findViewById(R.id.id_btn_calcular);
        btnCancelar = (Button) findViewById(R.id.id_btn_cancelar);
        btnQueEsIMC = (Button) findViewById(R.id.id_howisicm);

        btnCalcular.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);


        edPeso.setText("98Kgo");
        edAlto.setText("1.67m®");

        RES = getResources();

        RES.getString(R.string.app_name);


        context = this;
        lstResult = new ArrayList<ResultModel>();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.id_btn_calcular:
                Toast.makeText(context, "Peso: " + edPeso.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Alto: " + edAlto.getText().toString(), Toast.LENGTH_SHORT).show();
                break;

            case R.id.id_btn_cancelar:
                edPeso.setText(" ");
                edAlto.setText(" ");
                break;


        }

    }
}
