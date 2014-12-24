package ni.maestria.m8.kfcdelivery.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.models.MenuCombos;

/**
 * Created by cura on m 20/12/2014.
 */
public class AdapterMenuCombos extends RecyclerView.Adapter<AdapterMenuCombos.ViewHolder> implements View.OnClickListener {

    int view;
    ArrayList<MenuCombos> menuCombosArrayList;

    public AdapterMenuCombos(int view, ArrayList<MenuCombos> menuCombosArrayList) {
        this.view = view;
        this.menuCombosArrayList = menuCombosArrayList;
    }

    @Override
    public AdapterMenuCombos.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.addOrder.setOnClickListener(AdapterMenuCombos.this);
        viewHolder.removeOrder.setOnClickListener(AdapterMenuCombos.this);
        viewHolder.share.setOnClickListener(AdapterMenuCombos.this);
        viewHolder.addOrder.setTag(viewHolder);
        viewHolder.removeOrder.setTag(viewHolder);
        viewHolder.share.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterMenuCombos.ViewHolder holder, int position) {
        MenuCombos menuCombo = menuCombosArrayList.get(position);
        holder.txtPrice.setText("C$"+menuCombo.getPrecio());
        holder.txtDescription.setText(menuCombo.getDescripcion());
        holder.txtMenu.setText(menuCombo.getNombre());
    }

    @Override
    public int getItemCount() {
        if(menuCombosArrayList!=null)
            return menuCombosArrayList.size();
        else
            return 0;
    }

    @Override
    public void onClick(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (view.getId() == holder.addOrder.getId()) {
            Toast.makeText(view.getContext(), "add pedido", Toast.LENGTH_SHORT).show();
        }
        else if (view.getId() == holder.removeOrder.getId()) {
            Toast.makeText(view.getContext(), "renm pedido", Toast.LENGTH_SHORT).show();

        }
        else if (view.getId() == holder.share.getId()) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Te comparto y recomiendo este sabroso  "+holder.txtMenu.getText()+" de KFC a tan solo C$"+holder.txtPrice.getText());
            sendIntent.setType("text/plain");
            Uri img = Uri.parse("android.resource://" + view.getContext().getPackageName() + "/drawable/"+  R.drawable.restaurants );

            sendIntent.putExtra(Intent.EXTRA_STREAM, img);
            sendIntent.setType("image/jpeg");


            view.getContext().startActivity(Intent.createChooser(sendIntent,"Compartir usando..."));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMenu;
        TextView txtDescription;
        TextView txtPrice;
        ImageView imgMenu;
        ImageButton addOrder;
        ImageButton removeOrder;
        ImageButton share;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMenu = (TextView) itemView.findViewById(R.id.txt_menu);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_description);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_precio);
            imgMenu = (ImageView) itemView.findViewById(R.id.img_menu);
            addOrder = (ImageButton) itemView.findViewById(R.id.addButton);
            removeOrder = (ImageButton) itemView.findViewById(R.id.minusButton);
            share = (ImageButton) itemView.findViewById(R.id.shareButton);
        }
    }
}
