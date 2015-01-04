package ni.maestria.m8.kfcdelivery.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.R;
import ni.maestria.m8.kfcdelivery.db.OperationsCommentRestaurants;
import ni.maestria.m8.kfcdelivery.models.Comment;
import ni.maestria.m8.kfcdelivery.models.MenuCombos;
import ni.maestria.m8.kfcdelivery.models.Sucursal;

/**
 * Created by Eder Xavier Rojas on 17/12/2014.
 */
public class DataSourceSingleton {

    private ArrayList<Sucursal> sucursalsArray = new ArrayList<>();

    private ArrayList<Comment> commentArrayList = new ArrayList<>();
    private ArrayList<MenuCombos> menuComboses = new ArrayList<>();
    private static DataSourceSingleton instance;
    ProgressDialog pgd;
    OperationsCommentRestaurants operationsCommentRestaurants = null;
    private int dataRequests;//variable para almacenar el numero de intentos de conexion

    private DataReadyListener dataReadyListener;

    public DataSourceSingleton(final Context context) {
        pgd = new ProgressDialog(context);
        pgd.setMessage("Inicializando datos...");
        pgd.setCancelable(false);
        pgd.setTitle("Espere por favor...");
        pgd.setIcon(R.drawable.ic_action_info_outline);

        operationsCommentRestaurants = new OperationsCommentRestaurants(context);

        pgd.show();
        getSucursalsArrayListFromServer(context);
        getCommentsArrayListFromServer(context);
        getMenusArrayListFromServer(context);

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


    //Funcion para consumo de API REST del servidor para la lista de restaurantes
    public void getSucursalsArrayListFromServer(final Context context){
        RequestQueue requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
        dataRequests++;
        JsonArrayRequest req = new JsonArrayRequest(Sucursal.LIST_URL,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pgd.dismiss();
                dataRequests=0;
                sucursalsArray = Sucursal.getParseSucursalesJson(response);
                dataReadyListener.OnSucursalesDataReady(sucursalsArray);
                for(Sucursal sucursal : sucursalsArray)
                    operationsCommentRestaurants.insert(sucursal);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pgd.dismiss();
                if(dataRequests<3)
                    getSucursalsArrayListFromServer(context);
                else {
                  // Toast.makeText(context,"Error al cargar los datos del servidor...",
                            //Toast.LENGTH_LONG).show();
                    dataRequests=0;
                    //cargar lo q se tenga en SQLITE
                    sucursalsArray = operationsCommentRestaurants.getSucursalesCache();
                    dataReadyListener.OnSucursalesDataReady(sucursalsArray);
                    pgd.dismiss();
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
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pgd.dismiss();
                   // getCommentsArrayListFromServer(context);
                commentArrayList = operationsCommentRestaurants.getComentariosCache();
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
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //pgd.dismiss();
               // getMenusArrayListFromServer(context);
                menuComboses = operationsCommentRestaurants.getCombosCache();
            }
        });
        requestQueue.add(req);
    }


    //##########################################################################################
    public void setDataReadyListener(DataReadyListener dataReadyListener) {
        this.dataReadyListener = dataReadyListener;
    }

    //Interface para notificar cuando los datos esten cargados
    public interface DataReadyListener{
        public void OnSucursalesDataReady( ArrayList<Sucursal> sucursals);

    }
}

