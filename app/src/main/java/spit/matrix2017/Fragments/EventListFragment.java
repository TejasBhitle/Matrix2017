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

import android.content.Context;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import spit.matrix2017.Activities.EventDetails;
import spit.matrix2017.HelperClasses.Event;
import spit.matrix2017.HelperClasses.EventListAdapter;
import spit.matrix2017.HelperClasses.MatrixContentProvider;
import spit.matrix2017.HelperClasses.RecyclerItemClickListener;
import spit.matrix2017.R;

public class EventListFragment extends Fragment{
    RecyclerView mRecyclerView;
    MatrixContentProvider matrixContentProvider;
    private String category ="";


    public static EventListFragment newInstance(String category){
        EventListFragment fragment = new EventListFragment();
        Log.i("ViewPagerFragment ",category);
        Bundle bundle = new Bundle();
        bundle.putString("data",category);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null)
            category = bundle.getString("data");

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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(new EventListAdapter(getContext(),dbConnectionHelper.getData(category, 3)));
        mRecyclerView.scrollToPosition(0);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Event event=(dbConnectionHelper.getData(category,3)).get(position);
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
                        i.putExtra("color",event.getColor());
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


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Fragment attached",category);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment detached",category);
    }
}
