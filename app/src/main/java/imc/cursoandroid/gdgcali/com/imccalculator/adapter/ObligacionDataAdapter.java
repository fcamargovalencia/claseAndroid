package imc.cursoandroid.gdgcali.com.imccalculator.adapter;

/**
 * Created by desarrollo04 on 19/08/2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import imc.cursoandroid.gdgcali.com.imccalculator.R;
import imc.cursoandroid.gdgcali.com.imccalculator.model.iagree.Obligacion;


/**
 * Created by desarrollo04 on 17/08/2016.
 */
public class ObligacionDataAdapter extends RecyclerView.Adapter<ObligacionDataAdapter.ViewHolder> {
    private List<Obligacion> listaObligaciones;
    Context context;

    public ObligacionDataAdapter(List<Obligacion> listaObligaciones, Context context) {
        this.listaObligaciones = listaObligaciones;
        this.context = context;
    }

    @Override
    public ObligacionDataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ObligacionDataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_name.setText(listaObligaciones.get(i).getClient());
        viewHolder.tv_obligacion.setText(listaObligaciones.get(i).getObligation_number());
        viewHolder.tv_saldo.setText(listaObligaciones.get(i).getOverdue_balance() + "");
    }

    @Override
    public int getItemCount() {
        return listaObligaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_obligacion)
        TextView tv_obligacion;
        @BindView(R.id.tv_saldo)
        TextView tv_saldo;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}

