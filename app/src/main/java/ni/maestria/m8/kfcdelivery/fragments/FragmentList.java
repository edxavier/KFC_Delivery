package ni.maestria.m8.kfcdelivery.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.adapters.AdapterSucursal;
import ni.maestria.m8.kfcdelivery.models.Comment;
import ni.maestria.m8.kfcdelivery.models.Sucursal;
import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmentList extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterSucursal adapterSucursal;

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

        DataSourceSingleton.getInstance(getActivity()).setDataReadyListener(new DataSourceSingleton.DataReadyListener() {

            @Override
            public void OnSucursalesDataReady(ArrayList<Sucursal> sucursals) {
                adapterSucursal = new AdapterSucursal(sucursals,R.layout.row_list);
                mRecyclerView.setAdapter(adapterSucursal);
            }
            @Override
            public void OnComentariosDataReady(ArrayList<Comment> comments) {
            }
        });

        adapterSucursal = new AdapterSucursal(
                DataSourceSingleton.getInstance(getActivity()).getSucursalsArray(),
                R.layout.row_list);
        mRecyclerView.setAdapter(adapterSucursal);


    }



}
