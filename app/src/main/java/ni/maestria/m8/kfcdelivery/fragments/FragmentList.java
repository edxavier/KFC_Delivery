package ni.maestria.m8.kfcdelivery.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ni.maestria.m8.kfcdelivery.KfcService;
import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.adapters.AdapterSucursal;
import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmentList extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterSucursal adapterSucursal;

    KfcBroadcastReceiver receiver;
    IntentFilter filter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(receiver,filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
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
        receiver = new KfcBroadcastReceiver();
        filter = new IntentFilter(KfcService.KFC_SUCURSALS_FILTER);

        loadList();
    }

    public void loadList(){
            adapterSucursal = new AdapterSucursal(
                    DataSourceSingleton.getInstance(getActivity()).getSucursalsArray(),
                    R.layout.row_list);
            mRecyclerView.setAdapter(adapterSucursal);
    }


    class KfcBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            loadList();
        }
    }
}
