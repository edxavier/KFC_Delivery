package ni.maestria.m8.kfcdelivery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ni.maestria.m8.kfcdelivery.adapters.AdapterMenuCombos;
import ni.maestria.m8.kfcdelivery.utils.DataSourceSingleton;


public class MakeOrder extends ActionBarActivity {

    TextView tv ;
    Menu theMenu;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapterMenuCombos adapterMenuCombos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        loadCombos();

    }


    void loadCombos(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_menu);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapterMenuCombos = new AdapterMenuCombos(R.layout.row_combo,
                DataSourceSingleton.getInstance(this).getMenuComboses());
        mRecyclerView.setAdapter(adapterMenuCombos);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make_order, menu);
        tv = new TextView(this);
        tv.setText("orden");
        tv.setTextColor(Color.WHITE);
        tv.setPadding(5, 0, 5, 0);
        tv.setTextSize(12);
        menu.add(0, 3, 3, ".matchmacking").setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        theMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_send:
                tv.setText("orden:(1) C$300");
                theMenu.findItem(3).setActionView(tv);
                break;

            case R.id.action_shop_car:
                tv.setText("orden:(2) C$400");
                theMenu.findItem(3).setActionView(tv);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
