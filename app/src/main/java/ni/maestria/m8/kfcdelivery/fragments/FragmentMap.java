package ni.maestria.m8.kfcdelivery.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.models.Sucursal;
import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmentMap extends Fragment {

    LatLng managua = new LatLng(12.1297372,-86.2629238);

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map  , container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        GoogleMap mapa = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(managua)//Centramos el mapa en Managua
                .zoom(10).build();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mapa.moveCamera(cameraUpdate);
        mapa.setMyLocationEnabled(true);

        setMapa(mapa);
        setMarkers(mapa);

    }

    private void setMapa(GoogleMap mapa){

    }

    private void setMarkers(GoogleMap mapa){

        ArrayList<Sucursal> sucursals = DataSourceSingleton.getInstance(getActivity()).getSucursalsArray();
        for (Sucursal sucursal: sucursals){
            String[] coordenadas = sucursal.getCoordenadas().split(",");
            LatLng pos = new LatLng(Double.parseDouble(coordenadas[0]),Double.parseDouble(coordenadas[1]));

            mapa.addMarker(new MarkerOptions()
                    .position(pos)
                    .title(sucursal.getNombre()));
        }
    }
}
