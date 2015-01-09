package ni.maestria.m8.kfcdelivery.models;

import android.content.Context;

import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by Eder Xavier Rojas on 25/12/2014.
 */
public class DetallePedido {
    String menu;
    int cantidad;
    float precio;
    float subTotal;
    public static String API_POST_URL = "http://192.168.137.99:8000/api/detalle_pedido/";

    public DetallePedido(String menu, int cantidad, float subTotal) {
        this.menu = menu;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public DetallePedido() {
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubTotal() {
        return subTotal;
    }


    public static String GET_API_POST_URL(Context context){
        return DataSourceSingleton.getServer(context)+"/api/detalle_pedido/";
    }

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }
}
