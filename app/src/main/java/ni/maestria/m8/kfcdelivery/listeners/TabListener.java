package ni.maestria.m8.kfcdelivery.listeners;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import ni.maestria.m8.kfcdelivery.R;

/**
 * Created by cura on 09/12/2014.
 */
public class TabListener implements ActionBar.TabListener {

    private Fragment fragment;
    public TabListener(Fragment fg) {
        this.fragment = fg;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(R.id.container,this.fragment);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        //fragmentTransaction.remove(this.fragment);
        //fragmentTransaction.hide(this.fragment);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}
