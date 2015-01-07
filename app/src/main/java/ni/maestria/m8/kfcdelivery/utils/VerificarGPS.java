package ni.maestria.m8.kfcdelivery.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class VerificarGPS extends Activity {

    private LocationManager locManager;
    private LocationListener locListener;
    private AlertDialog alert = null;
    private boolean valor;
    private Location loc;

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public boolean verGPS(){

        //Si el GPS no está habilitado
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
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
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
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

    public void mostrarPosicion(Location loc) {
        if(loc != null)
        {
            String lati = String.valueOf(loc.getLatitude());
            String logt = String.valueOf(loc.getLongitude());
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
    }

    public void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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