package ni.maestria.m8.kfcdelivery.fragments;

import android.graphics.Color;
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
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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

        setMarkers(mapa);

    }


    private void setMarkers(GoogleMap mapa){

        ArrayList<Sucursal> sucursals = DataSourceSingleton.getInstance(getActivity()).getSucursalsArray();
        for (Sucursal sucursal: sucursals){
            String[] coordenadas = sucursal.getCoordenadas().split(",");
            LatLng pos = new LatLng(Double.parseDouble(coordenadas[0]),Double.parseDouble(coordenadas[1]));

            mapa.addMarker(new MarkerOptions()
                    .position(pos)
                    .snippet("A un radio de " + sucursal.getDistancia().toString() + " Km de ti")
                    .title(sucursal.getNombre()));

            mapa.addPolyline(new PolylineOptions().geodesic(true)
                    .add(pos)  // Sydney
                    .add(managua)  // Fiji
                    .width(3)
                    .color(Color.rgb(1,102,94))
            );
            CircleOptions circleOptions = new CircleOptions()
                    .fillColor(Color.argb(50,184,225,134))
                    .strokeWidth(3)
                    .strokeColor(Color.rgb(77,146,33))
                    .center(pos)
                    .radius(6000); // In meters

// Get back the mutable Circle
            mapa.addCircle(circleOptions);

        }
    }
}
