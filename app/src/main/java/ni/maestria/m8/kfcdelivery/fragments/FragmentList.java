package ni.maestria.m8.kfcdelivery.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.adapters.AdapterSucursal;
import ni.maestria.m8.kfcdelivery.models.Sucursal;
import ni.maestria.m8.kfcdelivery.utils.VolleySingleton;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmentList extends Fragment {

    private RecyclerView mRecyclerView;
    private AdapterSucursal adapterSucursal;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_list);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        final ProgressDialog pgd = new ProgressDialog(getActivity());
        pgd.setMessage("Cargango lista de restaurantes...");
        pgd.show();

        RequestQueue requestQueue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        JsonArrayRequest req = new JsonArrayRequest(Sucursal.LIST_URL,new Response.Listener<JSONArray>() {
            @Override

            public void onResponse(JSONArray response) {
                pgd.dismiss();
                adapterSucursal = new AdapterSucursal(Sucursal.getParseSucursalesJson(response),R.layout.row_list);
                mRecyclerView.setAdapter(adapterSucursal);

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pgd.dismiss();
                Toast.makeText(getActivity(),"Ocurrio un error inesperado!",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(req);

    }
}
