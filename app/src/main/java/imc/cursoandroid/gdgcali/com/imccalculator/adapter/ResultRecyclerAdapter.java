package imc.cursoandroid.gdgcali.com.imccalculator.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.model.ResultModel;
import imc.cursoandroid.gdgcali.com.imccalculator.view.DetailActivity;

/**
 * Created by joseberna on 10/08/16.
 */
public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.MyViewHolder> {
    List<ResultModel> lstResultModels;
    Context context;

    public ResultRecyclerAdapter(List<ResultModel> lstResultModels, Context context) {
        this.lstResultModels = lstResultModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ResultModel model = lstResultModels.get(position);
        holder.peso.setText("Peso: " + model.getPeso());
        holder.altura.setText("Altura: " + model.getAltura());
        holder.imc.setText("IMC: " + model.getImc());


        holder.altura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultModel result = lstResultModels.get(position);
                Toast.makeText(context, "Altura, " + result.getAltura(), Toast.LENGTH_LONG).show();
                Intent intActionView = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.gmail.com"));
                context.startActivity(intActionView);
            }
        });

        holder.peso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultModel result = lstResultModels.get(position);
                Toast.makeText(context, "Peso, " + result.getPeso(), Toast.LENGTH_LONG).show();
                Intent intNextPage = new Intent(context, DetailActivity.class);
                context.startActivity(intNextPage);
            }
        });

        holder.imc.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "IMC = Peso/Altura * Altura ", Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }


    @Override
    public int getItemCount() {
        return lstResultModels.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView peso, altura, imc;

        public MyViewHolder(View view) {
            super(view);
            peso = (TextView) view.findViewById(R.id.id_item_peso);
            altura = (TextView) view.findViewById(R.id.id_item_altura);
            imc = (TextView) view.findViewById(R.id.id_item_imc);

        }
    }
}
