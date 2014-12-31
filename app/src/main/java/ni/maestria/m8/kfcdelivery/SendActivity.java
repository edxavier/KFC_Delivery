package ni.maestria.m8.kfcdelivery;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SendActivity extends ActionBarActivity implements View.OnClickListener {
    TextView txtNombre;
    TextView txtTelefono;
    TextView txtDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Button btnTerminar = (Button) findViewById(R.id.btn_terminar);
        txtNombre = (TextView) findViewById(R.id.txtNombre_envio);
        txtTelefono = (TextView) findViewById(R.id.txt_telefono);
        txtDireccion = (TextView) findViewById(R.        btnTerminar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Guardar datos del pedido, luego obtener de sqlite el detalle seleccionado y guardarlo asociandolo con
        //los datos de pedido que se han guardado actualmente
        Toast.makeText(this,"Eviando a... "+ txtDireccion.getText().toString(),Toast.LENGTH_LONG).show();
    }
}
