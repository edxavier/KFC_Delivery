package ni.maestria.m8.kfcdelivery.models;

/**
 * Created by Eder Xavier Rojas on 25/12/2014.
 */
public class DetallePedido {
    String menu;
    int cantidad;
    float precio;
    float subTotal;

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

    public void setSubTotal(float subTotal) {
        this.subTotal = subTotal;
    }
}
