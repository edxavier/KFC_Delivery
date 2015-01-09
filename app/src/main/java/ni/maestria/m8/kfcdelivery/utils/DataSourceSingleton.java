package ni.maestria.m8.kfcdelivery.utils;

import android.content.Context;
import android.content.Intent;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.KfcService;
import ni.maestria.m8.kfcdelivery.db.OperationsCommentRestaurants;
import ni.maestria.m8.kfcdelivery.models.Comment;
import ni.maestria.m8.kfcdelivery.models.MenuCombos;
import ni.maestria.m8.kfcdelivery.models.Sucursal;

/**
 * Created by Eder Xavier Rojas on 17/12/2014.
 */
public class DataSourceSingleton {

    private ArrayList<Sucursal> sucursalsArray = new ArrayList<>();

    Intent intent;
    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private ArrayList<MenuCombos> menuComboses = new ArrayList<>();
    private static DataSourceSingleton instance;
    LatLng userPosition = null;
    OperationsCommentRestaurants operationsCommentRestaurants = null;
    private int dataRequests;//variable para almacenar el numero de intentos de conexion


    public DataSourceSingleton(final Context context) {
        operationsCommentRestaurants = new OperationsCommentRestaurants(context);
    }

    public LatLng getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(LatLng userPosition) {
        this.userPosition = userPosition;
    }

    public ArrayList<Sucursal> getSucursalsArray() {
        return sucursalsArray;
    }

    public ArrayList<Comment> getCommentArrayList() {
        return commentArrayList;
    }
    public ArrayList<MenuCombos> getMenuComboses() {
        return menuComboses;
    }

    public static DataSourceSingleton getInstance(Context context) {
        if(instance == null)
            instance = new DataSourceSingleton(context);
        return instance;
    }

    public static String getServer(Context context){
        SettingsKFC setting = new SettingsKFC(context);
        return "http://"+setting.getServer()+":"+setting.getPort();
    }

    //Funcion para consumo de API REST del servidor para la lista de restaurantes
    public void getSucursalsArrayListFromServer(final Context context){
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        dataRequests++;
        String  apiUrl = Sucursal.GET_API_GET_URL(context,this.userPosition);

        JsonArrayRequest req = new JsonArrayRequest(apiUrl,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //pgd.dismiss();
                dataRequests=0;
                sucursalsArray = Sucursal.getParseSucursalesJson(response);
                //dataReadyListener.OnSucursalesDataReady(sucursalsArray);
                operationsCommentRestaurants.deleteSucursales();
                for(Sucursal sucursal : sucursalsArray)
                    operationsCommentRestaurants.insert(sucursal);

                sendBroadcast(context,KfcService.KFC_SUCURSALS_FILTER);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pgd.dismiss();
                if(dataRequests<2)
                    getSucursalsArrayListFromServer(context);
                else {
                    dataRequests=0;
                    //cargar lo q se tenga en SQLITE
                    sucursalsArray = operationsCommentRestaurants.getSucursalesCache();
                    sendBroadcast(context,KfcService.KFC_SUCURSALS_FILTER);
                   // pgd.dismiss();
                }
            }
        });
        requestQueue.add(req);
    }


    public void getCommentsArrayListFromServer(final Context context){

        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonArrayRequest req = new JsonArrayRequest(Comment.API_GET_URL,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                commentArrayList = Comment.getParsedJson(response);
                operationsCommentRestaurants.deleteComments();
                for(Comment comment : commentArrayList)
                    operationsCommentRestaurants.insert(comment);
                sendBroadcast(context,KfcService.KFC_COMMENTS_FILTER);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pgd.dismiss();
                   // getCommentsArrayListFromServer(context);
                commentArrayList = operationsCommentRestaurants.getComentariosCache();
                sendBroadcast(context,KfcService.KFC_COMMENTS_FILTER);
            }
        });
        requestQueue.add(req);
    }

    public void getMenusArrayListFromServer(final Context context){

        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonArrayRequest req = new JsonArrayRequest(MenuCombos.API_URL,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                menuComboses = MenuCombos.getParsedJson(response);
                operationsCommentRestaurants.deleteCombos();
                for(MenuCombos combos : menuComboses)
                    operationsCommentRestaurants.insert(combos);
               // sendBroadcast(context);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pgd.dismiss();
               // getMenusArrayListFromServer(context);
                menuComboses = operationsCommentRestaurants.getCombosCache();
              //  sendBroadcast(context);
            }
        });
        requestQueue.add(req);
    }


    public void sendBroadcast(Context context, String filter)
    {
        intent = new Intent();
        intent.setAction(filter);
        context.sendBroadcast(intent);
         //sendBroadcast(intent);
    }
}

