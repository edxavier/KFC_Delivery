package ni.maestria.m8.kfcdelivery.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.models.Comment;

/**
 * Created by Eder Xavier Rojas on 18/12/2014.
 */
public class AdapterComentarios extends  RecyclerView.Adapter<AdapterComentarios.ViewHolder> implements View.OnClickListener {

    int view;
    ArrayList<Comment> comments ;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtNombre;
        TextView txtComentario;
        ImageView imgCliente;

        public ViewHolder(View viewLayout) {
            super(viewLayout);
            txtNombre = (TextView) viewLayout.findViewById(R.id.txt_nombre_client);
            txtComentario = (TextView) viewLayout.findViewById(R.id.txt_comment);
            imgCliente = (ImageView) viewLayout.findViewById(R.id.img_client);
        }
    }


    public AdapterComentarios(int view, ArrayList<Comment> comments) {
        this.view = view;
        this.comments = comments;
    }

    @Override
    public AdapterComentarios.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(view, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterComentarios.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.txtNombre.setText(comment.getClient());
        holder.txtComentario.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        if(comments!=null)
            return comments.size();
        else
            return 0;
    }

    @Override
    public void onClick(View v) {

    }
}
