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

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.models.Sucursal;
import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmentMap extends Fragment {

    LatLng managua = new LatLng(12.1297372,-86.2629238);
    GoogleMap mapa;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map  , container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapa = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
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
        DataSourceSingleton ds = DataSourceSingleton.getInstance(getActivity());
        LatLng pos = null;
        for (Sucursal sucursal: sucursals){
            String[] coordenadas = sucursal.getCoordenadas().split(",");
            pos = new LatLng(Double.parseDouble(coordenadas[0]),Double.parseDouble(coordenadas[1]));

                mapa.addMarker(new MarkerOptions()
                        .position(pos)
                        .snippet("A un radio de " + sucursal.getDistancia().toString() + " Km de ti")
                        .title(sucursal.getNombre()));

            /*if(ds.getUserPosition()!=null) {
                    mapa.addMarker(new MarkerOptions()
                            .position(ds.getUserPosition())
                            .snippet("Esta es la ultima posicion conocida...")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pin))
                            );
            }*/

            CircleOptions circleOptions = new CircleOptions()
                    .strokeWidth(3)
                    .center(pos)
                    .radius(6000); // In meters
            if(sucursal.getDistancia()==0 || sucursal.getDistancia()>6) {
                circleOptions.fillColor(Color.argb(50, 255, 170, 0));
                circleOptions.strokeColor(Color.rgb(255, 170, 0));
            }else {
                circleOptions.fillColor(Color.argb(50, 184, 225, 134));
                circleOptions.strokeColor(Color.rgb(77, 146, 33));
            }
            mapa.addCircle(circleOptions);

           /* if(ds.getUserPosition()!=null) {
                mapa.addPolyline(new PolylineOptions().geodesic(true)
                                .add(DataSourceSingleton.getInstance(getActivity()).getUserPosition())  // Sydney
                                .add(pos)  // Fiji
                                .width(3)
                                .color(Color.rgb(1, 102, 94))
                );
            }*/
        }



    }



}