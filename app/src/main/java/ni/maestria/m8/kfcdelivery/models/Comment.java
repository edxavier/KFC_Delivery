package ni.maestria.m8.kfcdelivery.models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;

/**
 * Created by Eder Xavier Rojas on 18/12/2014.
 */
public class Comment {
    String comment;
    String client;
    String imgUrl;
    public static String API_GET_URL = "http://192.168.137.99:8000/api/comentarios/?format=json";
    public static String API_POST_URL = "http://192.168.137.99:8000/api/comentarios/";

    public Comment() {
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }


    public static String GET_API_GET_URL(Context context){
        return  DataSourceSingleton.getServer(context)+"/api/comentarios/?format=json";
    }

    public static String GET_API_POST_URL(Context context){
        return  DataSourceSingleton.getServer(context)+"/api/comentarios/";
    }

    public static ArrayList<Comment> getParsedJson(JSONArray response)
    {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        for(int i = 0; i<response.length();i++){
            Comment comment = new Comment();
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);
                comment.setClient(jsonObject.getString("cliente"));
                comment.setComment(jsonObject.getString("comentario"));
                comment.setImgUrl(jsonObject.getString("imagen_url"));
                comments.add(comment);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return comments;
    }
}
