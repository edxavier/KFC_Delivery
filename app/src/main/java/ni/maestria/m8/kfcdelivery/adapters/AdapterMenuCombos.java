package ni.maestria.m8.kfcdelivery.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.MakeOrder;
import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.db.OperationsTempDetalle;
import ni.maestria.m8.kfcdelivery.models.DetallePedido;
import ni.maestria.m8.kfcdelivery.models.MenuCombos;
import ni.maestria.m8.kfcdelivery.utils.VolleySingleton;

/**
 * Created by cura on m 20/12/2014.
 */
public class AdapterMenuCombos extends RecyclerView.Adapter<AdapterMenuCombos.ViewHolder> implements View.OnClickListener {

    int view;
    ArrayList<MenuCombos> menuCombosArrayList;
    ArrayList<MenuCombos> orderArrayList = new ArrayList<>();
    ArrayList<DetallePedido> detallePedidos = new ArrayList<>();
    OperationsTempDetalle dbOperations = null;

    int cantidad;
    float total;


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
        holder.comboAvatar.setImageUrl(menuCombo.getImgUrl(), VolleySingleton.getInstance(holder.comboAvatar.getContext()).getImageLoader());


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
        TextView tv ;
        tv = new TextView(view.getContext());
        tv.setTextColor(Color.WHITE);
        tv.setPadding(5, 0, 5, 0);
        tv.setTextSize(12);
        Menu theMenu = ((MakeOrder)view.getContext()).getTheMenu();
        MenuCombos menu = menuCombosArrayList.get(holder.getPosition());
        if(dbOperations==null)
            dbOperations = new OperationsTempDetalle(view.getContext());

        if (view.getId() == holder.addOrder.getId()) {
            if(orderArrayList.contains(menu)) {
                cantidad += 1;
                total += Float.parseFloat(menu.getPrecio());//Acutlizar actionBar textview
                DetallePedido dt = dbOperations.getMenuTemp(menu.getNombre());//Obteber el registro de SQLite y actulizarlo
                dt.setCantidad(dt.getCantidad() + 1);
                float subTotal = (dt.getSubTotal()) + (Float.parseFloat(menu.getPrecio()));
                dt.setSubTotal(subTotal);
                dbOperations.update(dt);
                Toast.makeText(view.getContext(),dt.getMenu() + "\nCantidad: " + dt.getCantidad()
                            + "\nSub Total " + dt.getSubTotal(), Toast.LENGTH_SHORT).show();
                dt=null;
            }else {
                orderArrayList.add(menu);
                dbOperations.insert( new DetallePedido(
                                menu.getNombre(),1,
                                Float.parseFloat(menu.getPrecio())
                        )
                );
                cantidad += 1;
                total += Float.parseFloat(menu.getPrecio());
            }
            tv.setText("Orden:("+cantidad+") "  +total);
            theMenu.findItem(3).setActionView(tv);
        }
        else if (view.getId() == holder.removeOrder.getId()) {
            if(orderArrayList.contains(menu)) {
                cantidad -= 1;
                total -= Float.parseFloat(menu.getPrecio());//Acutlizar actionBar textview
                DetallePedido dt = dbOperations.getMenuTemp(menu.getNombre());//Obteber el registro de SQLite y actulizarlo
                dt.setCantidad(dt.getCantidad() - 1);
                float subTotal = (dt.getSubTotal()) - (Float.parseFloat(menu.getPrecio()));
                dt.setSubTotal(subTotal);
                dbOperations.update(dt);
                Toast.makeText(view.getContext(),dt.getMenu() + "\nCantidad: " + dt.getCantidad()
                        + "\nSub Total " + dt.getSubTotal(), Toast.LENGTH_SHORT).show();
                if(dt.getCantidad() < 1 ) {
                    orderArrayList.remove(menu);
                    dbOperations.delete(dt.getMenu());
                }
                dt=null;
            }
            tv.setText("Orden:("+cantidad+") "  +total);
            theMenu.findItem(3).setActionView(tv);

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
        NetworkImageView comboAvatar;
        ImageButton addOrder;
        ImageButton removeOrder;
        ImageButton share;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMenu = (TextView) itemView.findViewById(R.id.txt_menu);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_description);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_precio);
            addOrder = (ImageButton) itemView.findViewById(R.id.addButton);
            removeOrder = (ImageButton) itemView.findViewById(R.id.minusButton);
            share = (ImageButton) itemView.findViewById(R.id.shareButton);
            comboAvatar = (NetworkImageView) itemView.findViewById(R.id.combo_avatar);
        }
    }
}
