package imc.cursoandroid.gdgcali.com.imccalculator.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.model.ResultModel;
import imc.cursoandroid.gdgcali.com.imccalculator.view.DetailActivity;

/**
 * Created by joseberna on 29/07/16.
 */
public class ResultAdapter extends BaseAdapter {
    List<ResultModel> lstResults;
    int layoutResources;
    Context context;
    ResultModel model;

    public ResultAdapter(List<ResultModel> lstResults, Context context) {
        this.lstResults = lstResults;
        this.layoutResources = R.layout.item_result;
        this.context = context;
    }

    @Override
    public int getCount() {
        return lstResults.size();
    }

    @Override
    public Object getItem(int position) {
        return lstResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder item = new ItemViewHolder();
        model = new ResultModel();
        model = lstResults.get(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(layoutResources, null, true);

            item.peso = (TextView) convertView.findViewById(R.id.id_item_peso);
            item.altura = (TextView) convertView.findViewById(R.id.id_item_altura);
            item.imc = (TextView) convertView.findViewById(R.id.id_item_imc);


            item.imc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            item.altura.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = Integer.parseInt(v.getTag().toString());
                    ResultModel result = lstResults.get(pos);
                    Toast.makeText(context, "Altura, " + result.getAltura(), Toast.LENGTH_LONG).show();
                    Intent intActionView = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
                    context.startActivity(intActionView);
                }
            });

            item.peso.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = Integer.parseInt(v.getTag().toString());
                    ResultModel result = lstResults.get(pos);
                    Toast.makeText(context, "Peso, " + result.getPeso(), Toast.LENGTH_LONG).show();
                    Intent intNextPage = new Intent(context, DetailActivity.class);
                    context.startActivity(intNextPage);
                }
            });

            item.imc.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    int pos = Integer.parseInt(v.getTag().toString());
                    Toast.makeText(context, "IMC = Peso/Altura * Altura ", Toast.LENGTH_LONG).show();
                    return false;
                }
            });


            convertView.setTag(item);


            item.altura.setTag(position);
            item.peso.setTag(position);
            item.imc.setTag(position);
        } else {
            item = (ItemViewHolder) convertView.getTag();
        }
        item.altura.setText("Altura: " + model.getAltura());
        item.peso.setText("Peso: " + model.getPeso());
        item.imc.setText("IMC: " + model.getImc());

        return convertView;
    }


    private static class ItemViewHolder {
        TextView peso;
        TextView altura;
        TextView imc;

    }
}
