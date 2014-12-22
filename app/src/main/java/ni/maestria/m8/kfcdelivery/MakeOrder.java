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

import java.util.ArrayList;

import ni.maestria.m8.kfcdelivery.adapters.AdapterMenuCombos;
import ni.maestria.m8.kfcdelivery.models.MenuCombos;


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
    }


    void loadCombos(){
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view_menu);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        ArrayList<MenuCombos> menuComboses = new ArrayList<>();
        MenuCombos menuCombos = new MenuCombos();
        menuCombos.setDescripcion("dddddddd");
        menuCombos.setNombre("eeeeeeeeeeeee");
        menuCombos.setPrecio("1223");
        menuComboses.add(menuCombos);
        adapterMenuCombos = new AdapterMenuCombos(R.layout.row_combo,menuComboses);
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
