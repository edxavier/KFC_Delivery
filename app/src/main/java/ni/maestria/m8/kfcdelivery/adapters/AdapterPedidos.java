package ni.maestria.m8.kfcdelivery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.models.Pedido;

/**
 * Created by Eder Xavier Rojas on 13/01/2015.
 */
public class AdapterPedidos extends RecyclerView.Adapter<AdapterPedidos.ViewHolder> {

    int view;
    ArrayList<Pedido> misPedidos ;

    public AdapterPedidos(int view, ArrayList<Pedido> misPedidos) {
        this.view = view;
        this.misPedidos = misPedidos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtDireccionPedido;
        TextView txtTelefonoPedido;
        TextView txtEstadoPedido;

        public ViewHolder(View itemView) {
            super(itemView);
            txtDireccionPedido = (TextView) itemView.findViewById(R.id.txt_direccion_pedido);
            txtTelefonoPedido = (TextView) itemView.findViewById(R.id.txt_telefono_pedido);
            txtEstadoPedido = (TextView) itemView.findViewById(R.id.txt_estado_pedido);
        }
    }

    @Override
    public AdapterPedidos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterPedidos.ViewHolder holder, int position) {
        Pedido pedido = misPedidos.get(position);
        holder.txtDireccionPedido.setText(pedido.getDireccionEnvio());
        holder.txtTelefonoPedido.setText(pedido.getTelefonoEnvio());
        holder.txtEstadoPedido.setText(pedido.getEstado());
    }

    @Override
    public int getItemCount() {
        if(misPedidos!=null)
            return misPedidos.size();
        else
            return 0;
    }
}
