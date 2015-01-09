package ni.maestria.m8.kfcdelivery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Eder Xavier Rojas on 07/01/2015.
 */
public class SettingsKFC {

    String server;
    String port;
    String phone;
    String address;

    public SettingsKFC(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        this.server = sharedPref.getString("server","");
        this.phone = sharedPref.getString("phone","");
        this.port = sharedPref.getString("port","");
        this.address = sharedPref.getString("address","");
    }

    public String getServer() {
        return server;
    }

    public String getPort() {
        return port;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
