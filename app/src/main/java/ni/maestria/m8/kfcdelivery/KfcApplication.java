package ni.maestria.m8.kfcdelivery;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Eder Xavier Rojas on 08/01/2015.
 */
public class KfcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this,KfcService.class));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(new Intent(this,KfcService.class));
        Log.d("TAG", "onTerminate");
    }
}
