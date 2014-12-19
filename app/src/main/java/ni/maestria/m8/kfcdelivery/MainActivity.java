package ni.maestria.m8.kfcdelivery;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import ni.maestria.m8.kfcdelivery.fragments.FragmenComments;
import ni.maestria.m8.kfcdelivery.fragments.FragmentList;
import ni.maestria.m8.kfcdelivery.fragments.FragmentMap;
import ni.maestria.m8.kfcdelivery.listeners.TabListener;


public class MainActivity extends ActionBarActivity {
    FragmentList fragmentList = new FragmentList();
    TabListener tabListenerList = new TabListener(fragmentList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void setActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab1 = actionBar.newTab().setText("Lista");
        ActionBar.Tab tab2 = actionBar.newTab().setText("Mapa");
        ActionBar.Tab tab3 = actionBar.newTab().setText("Comentarios");

        tab1.setTabListener(tabListenerList);
        tab2.setTabListener(new TabListener(new FragmentMap()));
        tab3.setTabListener(new TabListener(new FragmenComments()));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);

    }

}
