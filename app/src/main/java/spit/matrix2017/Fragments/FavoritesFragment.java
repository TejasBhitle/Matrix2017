/*
 * *
 *  * This file is part of Matrix2017
 *  * Created for the annual technical festival of Sardar Patel Institute of Technology
 *  *
 *  * The original contributors of the software include:
 *  * - Adnan Ansari (psyclone20)
 *  * - Tejas Bhitle (TejasBhitle)
 *  * - Mithil Gotarne (mithilgotarne)
 *  * - Rohit Nahata (rohitnahata)
 *  * - Akshay Shah (akshah1997)
 *  *
 *  * Matrix2017 is free software: you can redistribute it and/or modify
 *  * it under the terms of the MIT License as published by the Massachusetts Institute of Technology
*/

package spit.matrix2017.Fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import spit.matrix2017.Activities.EventDetails;
import spit.matrix2017.HelperClasses.Event;
import spit.matrix2017.HelperClasses.EventListAdapter;
import spit.matrix2017.HelperClasses.MatrixContentProvider;
import spit.matrix2017.HelperClasses.RecyclerItemClickListener;
import spit.matrix2017.R;

public class FavoritesFragment extends Fragment {

    RecyclerView mRecyclerView;
    MatrixContentProvider matrixContentProvider;
    MatrixContentProvider.MatrixDBConnectionHelper dbConnectionHelper;
    EventListAdapter eventListAdapter;
    TextView blankTextview;

    public FavoritesFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerview_layout,container,false);

        matrixContentProvider=new MatrixContentProvider();
        dbConnectionHelper = new MatrixContentProvider().new MatrixDBConnectionHelper(getContext());
        blankTextview =(TextView)view.findViewById(R.id.blank_textview);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragmentRecyclerView);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setNestedScrollingEnabled(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        eventListAdapter = new EventListAdapter(getContext(),dbConnectionHelper.getData(String.valueOf(1),12));
        mRecyclerView.swapAdapter(eventListAdapter, false);
        //12 is the index of favourites in the column array of DB. If value is 1, it has been set as a favourite event
        mRecyclerView.scrollToPosition(0);

        if(eventListAdapter.getItemCount() ==0){
            blankTextview.setVisibility(View.VISIBLE);
        }
        else{
            blankTextview.setVisibility(View.GONE);
        }

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Event event = (dbConnectionHelper.getData(String.valueOf(1),12)).get(position);

                        Intent i = new Intent(getContext(), EventDetails.class);
                        i.putExtra("image",event.getImage());
                        i.putExtra("name", event.getName());
                        i.putExtra("description", event.getDescription());
                        i.putExtra("venue", event.getVenue());
                        i.putExtra("time", event.getTime());
                        i.putExtra("registration", event.getRegistration());
                        i.putExtra("prizes", event.getPrizes());
                        i.putExtra("contact1name", event.getContact1_name());
                        i.putExtra("contact1no", event.getContact1_no());
                        i.putExtra("contact2name", event.getContact2_name());
                        i.putExtra("contact2no", event.getContact2_no());
                        i.putExtra("favorite",event.getFavourite());
                        i.putExtra("reminder", event.getReminder());

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            ImageView poster = (ImageView)view.findViewById(R.id.thumbnail);
                            poster.setTransitionName("poster");
                            Pair pair = new Pair<>(poster, ViewCompat.getTransitionName(poster));

                            ActivityOptionsCompat optionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),pair);
                            ActivityCompat.startActivity(getActivity(),i,optionsCompat.toBundle());
                        }
                        else
                            getContext().startActivity(i);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
}