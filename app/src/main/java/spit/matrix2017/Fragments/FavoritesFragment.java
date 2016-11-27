package spit.matrix2017.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spit.matrix2017.Activities.EventDetails;
import spit.matrix2017.Activities.MainActivity;
import spit.matrix2017.HelperClasses.Event;
import spit.matrix2017.HelperClasses.EventListAdapter;
import spit.matrix2017.HelperClasses.MatrixContentProvider;
import spit.matrix2017.HelperClasses.RecyclerItemClickListener;
import spit.matrix2017.R;

/**
 * Created by DELL on 12/11/2016.
 */

public class FavoritesFragment extends Fragment {

    RecyclerView mRecyclerView;
    MatrixContentProvider matrixContentProvider;


    public FavoritesFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout,container,false);
        final MatrixContentProvider.MatrixDBConnectionHelper dbConnectionHelper;
        matrixContentProvider=new MatrixContentProvider();
        dbConnectionHelper = new MatrixContentProvider().new MatrixDBConnectionHelper(getContext());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(new EventListAdapter(getContext(),dbConnectionHelper.getData(String.valueOf(1),11)));
        //11 is the index of favourites in the column array of DB. If value is 1, it has been set as a favourite event
        mRecyclerView.scrollToPosition(0);
        AppBarLayout appBarLayout = ((MainActivity)getActivity()).getAppBarLayout();
        appBarLayout.setExpanded(false);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Event event=(dbConnectionHelper.getData(String.valueOf(1),11)).get(position);
                        Intent i = new Intent(getContext(), EventDetails.class);

                        i.putExtra("image",event.getImage());
                        i.putExtra("name", event.getName());
                        i.putExtra("description", event.getDescription());
                        i.putExtra("venue", event.getVenue());
                        i.putExtra("time", event.getTime());
                        i.putExtra("contact1name", event.getContact1_name());
                        i.putExtra("contact1no", event.getContact1_no());
                        i.putExtra("contact2name", event.getContact2_name());
                        i.putExtra("contact2no", event.getContact2_no());
                        i.putExtra("favorite",event.getFavourite());
                        i.putExtra("reminder", event.getReminder());

                        getContext().startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return view;
    }
}
