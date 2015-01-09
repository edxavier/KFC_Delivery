package ni.maestria.m8.kfcdelivery.models;

import android.content.Context;

import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by Eder Xavier Rojas on 23/12/2014.
 */
public class Pedido {
    String cliente;
    String socialId;
    String telefonoEnvio;
    String direccionEnvio;
    String menu;
    String estado;
    float total;
    int cantidad;
    public static String API_POST_URL = "http://192.168.137.99:8000/api/pedido/";

    public Pedido() {
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
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

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    public static String GET_API_POST_URL(Context context){
        return  DataSourceSingleton.getServer(context)+"/api/pedido/";
    }

}
