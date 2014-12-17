package ni.maestria.m8.kfcdelivery.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ni.maestria.m8.kfcdelivery.R;

/**
 * Created by cura on 11/12/2014.
 */
public class FragmenComments extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }
}
