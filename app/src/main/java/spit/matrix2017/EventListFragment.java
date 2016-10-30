package spit.matrix2017;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import spit.matrix2017.HelperClasses.EventListAdapter;

public class EventListFragment extends Fragment{

    private RecyclerView recyclerView;
    private String category ="";

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlayout,container,false);

        recyclerView =(RecyclerView)view.findViewById(R.id.fragmentRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));


        /*Get Arraylist From Content Provider and sort according to category(String)*/
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i =0;i< 50;i++){
            arrayList.add(category+" "+(i+1));
        }

        recyclerView.setAdapter(new EventListAdapter(arrayList,getActivity()));
        recyclerView.scrollToPosition(0);
        return view;
    }


}
