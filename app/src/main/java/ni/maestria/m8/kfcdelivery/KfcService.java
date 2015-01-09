package ni.maestria.m8.kfcdelivery;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by Eder Xavier Rojas on 08/01/2015.
 */
public class KfcService extends Service {

    static int DELAY = 30*1000;//1 min
    public static String KFC_SUCURSALS_FILTER = "ni.maestria.m8.kfcdelivery.sucursals";
    public static String KFC_COMMENTS_FILTER = "ni.maestria.m8.kfcdelivery.comments";

    private boolean isRunning = false;
    UpdaterThread updaterThread ;
    LocationListener locListener;
    LatLng position;

    @Override
    public IBinder onBind(Intent intent) {
        //Como se usara un servicio desligado queda retornado null
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       updaterThread = new UpdaterThread();
       trackGPS();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        updaterThread.interrupt();
        updaterThread = null;
        Log.d("TAG", "onDestroy");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        isRunning = true;
        try {
            updaterThread.start();
        }catch (Exception e){

        }
        return START_STICKY;
    }

    public void trackGPS(){
        final DataSourceSingleton ds = DataSourceSingleton.getInstance(getApplicationContext());
        LocationManager locManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //Nos registramos para recibir actualizaciones de la posici�n
        locListener = new LocationListener() {
            public void onLocationChanged(Location loc) {
                position = new LatLng(loc.getLatitude(), loc.getLongitude());
                ds.setUserPosition(position);
            }
            public void onProviderDisabled(String provider){
                DataSourceSingleton.getInstance(getApplicationContext())
                        .sendBroadcast(getApplicationContext(),KFC_SUCURSALS_FILTER);
            }
            public void onProviderEnabled(String provider){
                DataSourceSingleton.getInstance(getApplicationContext())
                        .sendBroadcast(getApplicationContext(),KFC_SUCURSALS_FILTER);
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                if(status == LocationProvider.OUT_OF_SERVICE)
                    Toast.makeText(getApplicationContext(), "GPS fuera de servicio", Toast.LENGTH_SHORT).show();
                else if(status == LocationProvider.TEMPORARILY_UNAVAILABLE)
                    Toast.makeText(getApplicationContext(), "GPS temporalmente fuera de servicio", Toast.LENGTH_SHORT).show();
            }
        };
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 10000, 0, locListener);
    }


    //******************************************************
    class UpdaterThread extends Thread{

        UpdaterThread() {
            super("KfcService-UpdaterThread");
        }


        @Override
        public void run() {
            super.run();
            Context cntx = getApplicationContext();
            KfcService kfcService = KfcService.this;
            LocationManager locManager;
            Location loc;
            LatLng position;
            DataSourceSingleton ds = DataSourceSingleton.getInstance(cntx);

            while (kfcService.isRunning){
                try {
                    //Obtenemos una referencia al LocationManager
                    locManager = (LocationManager)cntx.getSystemService(Context.LOCATION_SERVICE);
                    //Obtenemos la ultima posicion conocida
                    loc =  locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(loc!=null) {
                        position = new LatLng(loc.getLatitude(), loc.getLongitude());
                     //   Log.d("TAG-Hilo", "Se obtuvo la pos");

                    }
                    else {
                        position = null;
                        //Log.d("TAG-Hilo", "No se obtuvo la pos");
                    }

                    ds.setUserPosition(position);
                    ds.getSucursalsArrayListFromServer(cntx);
                    ds.getCommentsArrayListFromServer(cntx);
                    ds.getMenusArrayListFromServer(cntx);

                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    kfcService.isRunning = false;
                }
            }

        }
    }
}
