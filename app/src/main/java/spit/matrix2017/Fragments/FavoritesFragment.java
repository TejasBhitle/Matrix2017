package spit.matrix2017.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spit.matrix2017.Activities.MainActivity;
import spit.matrix2017.R;

/**
 * Created by DELL on 12/11/2016.
 */

public class FavoritesFragment extends Fragment {

    public FavoritesFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites,container,false);

        AppBarLayout appBarLayout = ((MainActivity)getActivity()).getAppBarLayout();
        appBarLayout.setExpanded(false);
        return view;
    }
}
