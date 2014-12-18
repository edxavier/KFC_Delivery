package ni.maestria.m8.kfcdelivery.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cura on 11/12/2014.
 */
public class Sucursal {

    private int id;

    private String nombre;
    private String telefono;
    private String direccion;
    private Double distancia;

    private String coordenadas;



    public static String LIST_URL = "http://192.168.137.20:8000/api/restaurantes/?format=json&lat=12.097659&lon=-86.2584606";


    public Sucursal() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }


    public static  ArrayList<Sucursal> getParseSucursalesJson(JSONArray response)
    {
        ArrayList<Sucursal> sucursals = new ArrayList<Sucursal>();
        for(int i = 0; i<response.length();i++){
            Sucursal sucursal = new Sucursal();
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);
                sucursal.setNombre(jsonObject.getString("nombre"));
                sucursal.setDireccion(jsonObject.getString("direccion"));
                sucursal.setDistancia(jsonObject.getDouble("distancia_km"));
                sucursal.setTelefono(jsonObject.getString("telefono"));
                sucursal.setCoordenadas(jsonObject.getString("coordenadas"));
                sucursal.setId(jsonObject.getInt("id"));
                sucursals.add(sucursal);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return sucursals;
    }

}
