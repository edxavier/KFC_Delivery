package ni.maestria.m8.kfcdelivery.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.models.Sucursal;

/**
 * Created by cura on 11/12/2014.
 */
public class AdapterSucursal extends  RecyclerView.Adapter<AdapterSucursal.ViewHolder> implements View.OnClickListener {

    ArrayList<Sucursal> sucursals;
    int row_view;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView sucursal;
        TextView direccion;
        TextView distancia;
        String telefono;
        ImageButton call;
        ImageButton order;
        ImageButton share;

        public ViewHolder(View viewLayout) {
            super(viewLayout);
            sucursal = (TextView) viewLayout.findViewById(R.id.txt_name_sucursal);
            direccion = (TextView) viewLayout.findViewById(R.id.txt_direccion);
            distancia = (TextView) viewLayout.findViewById(R.id.txt_distance);
            call = (ImageButton) viewLayout.findViewById(R.id.callButton);
            order = (ImageButton) viewLayout.findViewById(R.id.orderButton);
            share = (ImageButton) viewLayout.findViewById(R.id.shareButton);

        }
    }

    public AdapterSucursal(ArrayList<Sucursal> sucursals, int view) {
        this.sucursals = sucursals;
        this.row_view = view;
    }
    /*
    public AdapterSucursal(Context context) {
       super(context);
    }*/


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(row_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.call.setOnClickListener(AdapterSucursal.this);
        viewHolder.order.setOnClickListener(AdapterSucursal.this);
        viewHolder.share.setOnClickListener(AdapterSucursal.this);
        viewHolder.call.setTag(viewHolder);
        viewHolder.order.setTag(viewHolder);
        viewHolder.share.setTag(viewHolder);
        return viewHolder;
    }


    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();

        if (view.getId() == holder.call.getId()) {
            String numero = (String) holder.sucursal.getTag();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+numero));
            view.getContext().startActivity(intent);
        }
        else if (view.getId() == holder.order.getId()) {
            Toast.makeText(view.getContext(), "hacer pedido", Toast.LENGTH_SHORT).show();
        }
        else if (view.getId() == holder.share.getId()) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Te comparto y recomiendo el restaurante KFC de "+holder.sucursal.getText()+" - Telef.: "+holder.telefono);
            sendIntent.setType("text/plain");
            Uri img = Uri.parse("android.resource://" + view.getContext().getPackageName() + "/drawable/"+  R.drawable.restaurants );
            sendIntent.putExtra(Intent.EXTRA_STREAM, img);
            sendIntent.setType("image/jpeg");


            view.getContext().startActivity(Intent.createChooser(sendIntent,"Compartir usando..."));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Sucursal sucursal= sucursals.get(i);
        viewHolder.sucursal.setText(sucursal.getNombre());
        viewHolder.sucursal.setTag(sucursal.getTelefono());
        viewHolder.telefono= sucursal.getTelefono();

        viewHolder.direccion.setText(sucursal.getDireccion());
        String dist = "A un radio de "+Double.toString(sucursal.getDistancia())+" km de ti";
        viewHolder.distancia.setText(dist);
    }

    @Override
    public int getItemCount() {
        return sucursals.size();
    }
}
