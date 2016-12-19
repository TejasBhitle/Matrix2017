package spit.matrix2017.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import spit.matrix2017.R;

/**
 * Created by Tejas on 09/12/2016.
 */

public class AboutAppFragment extends Fragment {

    LinearLayout rateThisApp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aboutapp,container,false);

        rateThisApp = (LinearLayout)view.findViewById(R.id.fork_button);
        rateThisApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(getResources().getString(R.string.github_link));
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });

        return view;
    }
}
