package ni.maestria.m8.kfcdelivery.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ni.maestria.m8.kfcdelivery.R;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmentMap extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map  , container, false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  GoogleMap mapa = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        //if(mapa==null)
          //  Toast.makeText(getActivity(),"mapa null",Toast.LENGTH_LONG).show();
    }
}
