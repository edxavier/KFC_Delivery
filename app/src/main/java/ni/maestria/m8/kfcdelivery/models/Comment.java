package ni.maestria.m8.kfcdelivery.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Eder Xavier Rojas on 18/12/2014.
 */
public class Comment {
    String comment;
    String client;
    public static String API_URL= "http://192.168.137.20:8000/api/comentarios/?format=json";

    public Comment() {
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

    public static ArrayList<Comment> getParsedJson(JSONArray response)
    {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        for(int i = 0; i<response.length();i++){
            Comment comment = new Comment();
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);
                JSONObject subJsonObject = jsonObject.getJSONObject("cliente");
                comment.setClient(subJsonObject.getString("nombre"));
                comment.setComment(jsonObject.getString("comentario"));
                comments.add(comment);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Commetns","Error "+e.getMessage());
            }
        }
        return comments;
    }
}
