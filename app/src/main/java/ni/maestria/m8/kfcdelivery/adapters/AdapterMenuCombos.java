package ni.maestria.m8.kfcdelivery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterMenuCombos.ViewHolder holder, int position) {
        MenuCombos menuCombo = menuCombosArrayList.get(position);
        holder.txtPrice.setText(menuCombo.getPrecio());
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

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMenu;
        TextView txtDescription;
        TextView txtPrice;
        ImageView imgMenu;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMenu = (TextView) itemView.findViewById(R.id.txt_menu);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_description);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_precio);
            imgMenu = (ImageView) itemView.findViewById(R.id.img_menu);
        }
    }
}
