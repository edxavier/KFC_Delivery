package ni.maestria.m8.kfcdelivery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.models.DetallePedido;

/**
 * Created by Eder Xavier Rojas on 03/01/2015.
 */
public class AdapterDetallePedido extends  RecyclerView.Adapter<AdapterDetallePedido.ViewHolder> {

    int view;
    ArrayList<DetallePedido> detallePedidos ;

    public AdapterDetallePedido(int view, ArrayList<DetallePedido> detallePedidos) {
        this.view = view;
        this.detallePedidos = detallePedidos;
    }


    @Override
    public AdapterDetallePedido.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterDetallePedido.ViewHolder holder, int position) {
        DetallePedido detallePedido = detallePedidos.get(position);
        holder.txtCombo.setText(detallePedido.getMenu());
        holder.txtCantidad.setText(Integer.toString(detallePedido.getCantidad()));
        holder.txtSubtotal.setText(Float.toString(detallePedido.getSubTotal()));
    }

    @Override
    public int getItemCount() {
        if(detallePedidos!=null)
            return detallePedidos.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtCombo;
        TextView txtCantidad;
        TextView txtSubtotal;


        public ViewHolder(View viewLayout) {
            super(viewLayout);
            txtCombo = (TextView) viewLayout.findViewById(R.id.txt_nombre_combo);
            txtCantidad = (TextView) viewLayout.findViewById(R.id.txt_cantidad_combo_detalle);
            txtSubtotal = (TextView) viewLayout.findViewById(R.id.txt_subtotal);

        }
    }
}
