package ni.maestria.m8.kfcdelivery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ni.maestria.m8.kfcdelivery.models.Comment;
import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;
import ni.maestria.m8.kfcdelivery.utils.VolleySingleton;


public class NewComentActivity extends ActionBarActivity implements View.OnClickListener {

    TextView txtComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_coment);
        Button enviar = (Button) findViewById(R.id.btn_enviar);
        txtComentario = (TextView) findViewById(R.id.txtComment);
        enviar.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_new_coment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(txtComentario.getText().length()<=5)
            Toast.makeText(this, "Por favor agrega un comentario de 5 caracteres minimo!", Toast.LENGTH_LONG).show();
        else
        {
            final ProgressDialog pgd = new ProgressDialog(v.getContext());
            pgd.setMessage("Atendiendo solicitud...");
            pgd.setCancelable(false);
            pgd.setTitle("Espere por favor...");
            pgd.setIcon(R.drawable.ic_action_info_outline);
            pgd.show();
            final String coment = txtComentario.getText().toString();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cliente", "Ed Xavier");
                jsonObject.put("imagen_url", "http://192.168.137.20:8000/api");
                jsonObject.put("comentario", coment);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Comment.API_POST_URL,jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    pgd.dismiss();
                    Toast.makeText(getApplicationContext(),"¡Listo, comentario guardado! "+response.toString(),Toast.LENGTH_LONG).show();
                    DataSourceSingleton.getInstance(getApplicationContext()).getCommentsArrayListFromServer(getApplicationContext());
                    finish();
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pgd.dismiss();
                    Toast.makeText(getApplicationContext(),"¡Ah ocurrido un error al enviar tus peticion!",Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                public Map<String, String> getHeaders(){
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);

        }

    }
}
