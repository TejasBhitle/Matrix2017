package spit.matrix2017;


import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import spit.matrix2017.HelperClasses.EventListAdapter;

public class EventListFragment extends Fragment{

    private RecyclerView recyclerView;
    private String category ="";
    private ArrayList<String> arrayList;
    private ContentResolver contentResolver;
    private Uri uri;

    public static EventListFragment newInstance(String category){
        EventListFragment fragment = new EventListFragment();
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

        contentResolver = getActivity().getContentResolver();
        uri = Uri.parse("content://spit.matrix2017.provider");

        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        /*cursor will contain all the rows of the event table. You can use it to set the views manually.

        Further, if you want to use a SimpleCursorAdapter(makes it easier to inflate views directly), use:

        int layoutResId = <Make a separate layout which you want to inflate repeatedly in a listview and give its ID here, in the form R.id.<listitem>>;
        String[] cursorColumns = {"names", "of", "the", "columns", "from", "cursor", "that", "you", "want"};
        int[] resId = {ID's of the corresponding views e.g TextViews of the list item whose ID is given in R.id.<listitem> (order should be corresponding to the cursorColumns array)};

        SimpleCursorAdapter cursorAdapter = new
        SimpleCursorAdapter(<Activity_name>.this, la,,youtResId, cursor, cursorColumns, resId, 0);*/


        /*String Category contains the options of sorting into mega,fun,tech*/
        arrayList = new ArrayList<>();
        for(int i =0;i< 50;i++){
            arrayList.add(category+" "+(i+1));
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentlayout,container,false);

        recyclerView =(RecyclerView)view.findViewById(R.id.fragmentRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));





        recyclerView.setAdapter(new EventListAdapter(arrayList,getActivity()));
        recyclerView.scrollToPosition(0);
        return view;
    }

}
