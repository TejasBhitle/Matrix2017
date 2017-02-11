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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import spit.matrix2017.HelperClasses.SponsorRecyclerAdapter;
import spit.matrix2017.R;

public class SponsorsFragment extends Fragment {

    RecyclerView recyclerView;

    public SponsorsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sponsors,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclersponsor);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.hasFixedSize();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Knowledge Partner - TCS");
        arrayList.add("Digital Partner  - Campus Commune");
        arrayList.add("Fn B - Mad Brew Cafe");
        arrayList.add("Media Partner - Maharashtra Times");
        arrayList.add("Engineering Partner - Brainheaters");
        arrayList.add("Online Media Partner - Careers360");
        arrayList.add("Styling Partner -  Scotlane");
        arrayList.add("Internship Partner - Internshala");
        arrayList.add("More - Nactus");
        arrayList.add("More - RentSetGo");

        Integer[] drawableArray = {R.drawable.tcs,R.drawable.campuscommune,R.drawable.madbrewcafe,R.drawable.maharastratimes,R.drawable.brainheater,R.drawable.careers360,R.drawable.scotlane,R.drawable.internshala,R.drawable.nactus,R.drawable.rentsetgo};
        recyclerView.setAdapter(new SponsorRecyclerAdapter(arrayList,drawableArray));

        return view;
    }
}
