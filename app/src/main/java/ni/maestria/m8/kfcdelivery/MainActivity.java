package ni.maestria.m8.kfcdelivery;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ni.maestria.m8.kfcdelivery.fragments.FragmenComments;
import ni.maestria.m8.kfcdelivery.fragments.FragmentList;
import ni.maestria.m8.kfcdelivery.fragments.FragmentMap;
import ni.maestria.m8.kfcdelivery.listeners.TabListener;


public class MainActivity extends ActionBarActivity {
    FragmentList fragmentList = new FragmentList();
    TabListener tabListenerList = new TabListener(fragmentList);
    public static int RESULT_EXIT = 1;
    public static int RESULT_EXIT_SESSION = 2;
    public static int RESULT_REVOKE_ACCESS = 3;
    private AlertDialog alert = null;
    private LocationManager locManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //Si el GPS no está habilitado
        locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this,"El GPS no se encuentra activado",Toast.LENGTH_LONG).show();
            AlertNoGps();
        }
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
        Intent returnIntent = new Intent();
        //noinspection SimplifiableIfStatement
        switch (id){
            case  R.id.action_settings:
                Toast.makeText(this,"Conf",Toast.LENGTH_LONG).show();
                Intent newOrder = new Intent(this,MakeOrder.class);
                startActivity(newOrder);
                break;
            case R.id.action_add_comment:
                Intent sendComment = new Intent(this,NewComentActivity.class);
                startActivity(sendComment);
                break;
            case R.id.action_exit:
                returnIntent = new Intent();
                returnIntent.putExtra("result",MainActivity.RESULT_EXIT);
                setResult(RESULT_OK,returnIntent);
                finish();
                break;
            case R.id.action_exit_session:
                returnIntent = new Intent();
                returnIntent.putExtra("result",MainActivity.RESULT_EXIT_SESSION);
                setResult(RESULT_OK,returnIntent);
                finish();
                break;
            case R.id.action_revoke_acces:
                returnIntent = new Intent();
                returnIntent.putExtra("result",MainActivity.RESULT_REVOKE_ACCESS);
                setResult(RESULT_OK,returnIntent);
                finish();
                break;
        }
        //if (id == R.id.action_settings) {
          //  return true;
        //}

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

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

}
