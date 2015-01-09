package ni.maestria.m8.kfcdelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ni.maestria.m8.kfcdelivery.db.OperationsTempDetalle;
import ni.maestria.m8.kfcdelivery.db.OperationsUser;
import ni.maestria.m8.kfcdelivery.models.Cliente;
import ni.maestria.m8.kfcdelivery.models.DetallePedido;
import ni.maestria.m8.kfcdelivery.models.Pedido;
import ni.maestria.m8.kfcdelivery.utils.SettingsKFC;
import ni.maestria.m8.kfcdelivery.utils.VolleySingleton;


public class SendActivity extends ActionBarActivity implements View.OnClickListener {
    TextView txtNombre;
    TextView txtTelefono;
    TextView txtDireccion;
    OperationsTempDetalle dbOperations = null;
    OperationsUser operationsUser = null;
    Cliente cliente= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Button btnTerminar = (Button) findViewById(R.id.btn_terminar);
        txtNombre = (TextView) findViewById(R.id.txtNombre_envio);
        txtTelefono = (TextView) findViewById(R.id.txt_telefono);
        txtDireccion = (TextView) findViewById(R.id.txt_direccion_envio);
        btnTerminar.setOnClickListener(this);
        if(dbOperations==null)
            dbOperations = new OperationsTempDetalle(this);
        if(operationsUser==null)
            operationsUser= new OperationsUser(this);
        cliente = operationsUser.getUser();
        txtNombre.setText(cliente.getNombre());

        SettingsKFC settingsKFC = new SettingsKFC(this);

        txtTelefono.setText(settingsKFC.getPhone());
        txtDireccion.setText(settingsKFC.getAddress());
    }

    @Override
    public void onClick(View v) {
        //Guardar datos del pedido, luego obtener de sqlite el detalle seleccionado y guardarlo asociandolo con
        //los datos de pedido que se han guardado actualmente
        final ArrayList<DetallePedido> detallePedidos = dbOperations.getTempDetallePedido();

        if(txtNombre.getText().length()>0 && txtTelefono.getText().length()>0 && txtDireccion.getText().length()>0)
        {
            final ProgressDialog pgd = new ProgressDialog(v.getContext());
            pgd.setMessage("Atendiendo solicitud...");
            pgd.setCancelable(false);
            pgd.setTitle("Espere por favor...");
            pgd.setIcon(R.drawable.ic_action_info_outline);
            pgd.show();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cliente", txtNombre.getText().toString());
                jsonObject.put("telefono", txtTelefono.getText().toString());
                jsonObject.put("direccion", txtDireccion.getText().toString());
                jsonObject.put("estado", "EN ESPERA");
                jsonObject.put("social_id", cliente.getSocialId());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Pedido.API_POST_URL,jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //pgd.dismiss();
                    String pedidoUrl="";
                    try {
                        pedidoUrl = response.getString("url");
                        //Toast.makeText(getApplicationContext(),pedidoUrl,Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for(DetallePedido dp:detallePedidos){
                        if(!pedidoUrl.isEmpty())
                            sendDetalle(dp,pedidoUrl);
                    }
                    pgd.dismiss();
                    dbOperations.delete();
                    Toast.makeText(getApplicationContext(),"¡Su pedio ha sido enviado!",Toast.LENGTH_LONG).show();

                    Intent returnIntent = new Intent();
                    //returnIntent.putExtra("result",MainActivity.RESULT_EXIT_SESSION);
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pgd.dismiss();
                    Toast.makeText(getApplicationContext(),"¡Ha ocurrido un error al enviar tu peticion!",Toast.LENGTH_LONG).show();
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

        }else
            Toast.makeText(this,"Todos los campos son requeridos",Toast.LENGTH_LONG).show();
    }

    public void sendDetalle(DetallePedido detallePedido, String pedidoURL){


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("menu", detallePedido.getMenu());
            jsonObject.put("cantidad", detallePedido.getCantidad());
            jsonObject.put("total", detallePedido.getSubTotal());
            jsonObject.put("pedido", pedidoURL);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, DetallePedido.API_POST_URL,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getApplicationContext(),"¡Listo, pedido guardado! ",Toast.LENGTH_LONG).show();

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"¡Ha ocurrido un error al enviar tu peticion!",Toast.LENGTH_LONG).show();
                return;
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
