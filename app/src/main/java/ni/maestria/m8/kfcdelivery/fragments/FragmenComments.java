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
import ni.maestria.m8.kfcdelivery.adapters.AdapterComentarios;
import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmenComments extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterComentarios adapterComentarios;
    KfcBroadcastReceiver receiver;
    IntentFilter filter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
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
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_comments);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        receiver = new KfcBroadcastReceiver();
        filter = new IntentFilter(KfcService.KFC_COMMENTS_FILTER);
        loadComments();

    }

    public void loadComments(){
        adapterComentarios = new AdapterComentarios(R.layout.row_comentario,
                DataSourceSingleton.getInstance(getActivity()).getCommentArrayList());
        mRecyclerView.setAdapter(adapterComentarios);
    }

    class KfcBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadComments();
        }
    }

}
