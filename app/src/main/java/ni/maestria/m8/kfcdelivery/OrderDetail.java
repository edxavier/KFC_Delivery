package ni.maestria.m8.kfcdelivery;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ni.maestria.m8.kfcdelivery.adapters.AdapterDetallePedido;
import ni.maestria.m8.kfcdelivery.db.OperationsTempDetalle;
import ni.maestria.m8.kfcdelivery.models.DetallePedido;


public class OrderDetail extends ActionBarActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    OperationsTempDetalle dbOperations = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        if(dbOperations==null)
            dbOperations = new OperationsTempDetalle(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_order_detail);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        TextView txtTotal = (TextView) findViewById(R.id.txt_total_detalle);


       AdapterDetallePedido adapter = new AdapterDetallePedido(R.layout.row_order_detail,
                dbOperations.getTempDetallePedido());
        mRecyclerView.setAdapter(adapter);

        float total = 0;
        for(DetallePedido detallePedido : dbOperations.getTempDetallePedido())
            total += detallePedido.getSubTotal();
        txtTotal.setText("Total: "+Float.toString(total));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_detail, menu);
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
}
