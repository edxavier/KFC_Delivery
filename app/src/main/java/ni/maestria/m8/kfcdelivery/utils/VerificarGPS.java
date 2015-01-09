package ni.maestria.m8.kfcdelivery.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class VerificarGPS  {

    private LocationManager locManager;
    private LocationListener locListener;
    private AlertDialog alert = null;
    private boolean valor;
    private Location loc;
    Context context;

    public VerificarGPS(Context context) {
        this.context = context;
    }

    public Location getLoc() {
        return loc;
    }

    public boolean isGpsActive(){

        //Si el GPS no está habilitado
        locManager = (LocationManager)  context.getSystemService(Context.LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this,"El GPS no se encuentra activado",Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void comenzarLocalizacion()
    {
        //Obtenemos una referencia al LocationManager
        locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        //Obtenemos la ultima posicion conocida
        Location loc =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Mostramos la ultima posicion conocida
        mostrarPosicion(loc);
        //Nos registramos para recibir actualizaciones de la posici�n
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }
            public void onProviderDisabled(String provider){
            }
            public void onProviderEnabled(String provider){
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i("", "Provider Status: " + status);
            }
        };
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    public LatLng getLocation()
    {
        LatLng position = new LatLng(12.1297372,-86.2629238);
        //Obtenemos una referencia al LocationManager
        locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        //Obtenemos la ultima posicion conocida
        loc =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(loc!=null)
            position = new LatLng(loc.getLatitude(),loc.getLongitude());
        else
            position= null;

        return position;
    }


    public void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            String lati = String.valueOf(loc.getLatitude());
            String logt = String.valueOf(loc.getLongitude());
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
    }

    public void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }
}