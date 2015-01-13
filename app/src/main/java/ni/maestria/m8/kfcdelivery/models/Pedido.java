package ni.maestria.m8.kfcdelivery.models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by Eder Xavier Rojas on 23/12/2014.
 */
public class Pedido {


    String telefonoEnvio;
    String direccionEnvio;
    String estado;

    public static String API_POST_URL = "http://192.168.137.99:8000/api/pedido/";

    public Pedido() {
    }

    public String getTelefonoEnvio() {
        return telefonoEnvio;
    }

    public void setTelefonoEnvio(String telefonoEnvio) {
        this.telefonoEnvio = telefonoEnvio;
    }

    public String getDireccionEnvio() {
        return direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static ArrayList<Pedido> parseJson(JSONArray response){
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();
        for(int i = 0; i<response.length();i++){
            Pedido pedido = new Pedido();
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);
                pedido.setDireccionEnvio(jsonObject.getString("direccion"));
                pedido.setTelefonoEnvio(jsonObject.getString("telefono"));
                pedido.setEstado(jsonObject.getString("estado"));
                pedidos.add(pedido);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return pedidos;
    }

    public static String GET_API_GET_URL(Context context, String socialId){
        return  DataSourceSingleton.getServer(context)+"/api/pedido/?format=json&social_id="+socialId;
    }

}
