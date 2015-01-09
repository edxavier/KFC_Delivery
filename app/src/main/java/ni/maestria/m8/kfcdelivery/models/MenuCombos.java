package ni.maestria.m8.kfcdelivery.models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by cura on 20/12/2014.
 */
public class MenuCombos {
    String nombre;
    String descripcion;
    String precio;
    String imgUrl;
    public static String API_URL= "http://192.168.137.99:8000/api/menu/?format=json";

    public MenuCombos() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public static ArrayList<MenuCombos> getParsedJson(JSONArray response)
    {
        ArrayList<MenuCombos> menuComboses = new ArrayList<>();

        for(int i = 0; i<response.length();i++){
            MenuCombos combo = new MenuCombos();
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);
                combo.setNombre(jsonObject.getString("nombre"));
                combo.setDescripcion(jsonObject.getString("descripcion"));
                combo.setPrecio(jsonObject.getString("precio"));
                combo.setImgUrl(jsonObject.getString("imagen"));

                menuComboses.add(combo);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return menuComboses;
    }
    public static String GET_API_GET_URL(Context context){
        return  DataSourceSingleton.getServer(context)+"/api/menu/?format=json";
    }

}
