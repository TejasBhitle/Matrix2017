package spit.matrix2017;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spit.matrix2017.Activities.EventDetails;
import spit.matrix2017.HelperClasses.Event;
import spit.matrix2017.HelperClasses.EventListAdapter;
import spit.matrix2017.HelperClasses.MatrixContentProvider;
import spit.matrix2017.HelperClasses.RecyclerItemClickListener;


public class EventListFragment extends Fragment{
    RecyclerView mRecyclerView;
    MatrixContentProvider matrixContentProvider;
    private String category ="";

    //    private RecyclerView recyclerView;
//    private ArrayList<String> arrayList;
//    private ContentResolver contentResolver;
//    private Uri uri;

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

//        contentResolver = getActivity().getContentResolver();
//        uri = Uri.parse("content://spit.matrix2017.provider");
//
//        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        /*cursor will contain all the rows of the event table. You can use it to set the views manually.

        Further, if you want to use a SimpleCursorAdapter(makes it easier to inflate views directly), use:

        int layoutResId = <Make a separate layout which you want to inflate repeatedly in a listview and give its ID here, in the form R.id.<listitem>>;
        String[] cursorColumns = {"names", "of", "the", "columns", "from", "cursor", "that", "you", "want"};
        int[] resId = {ID's of the corresponding views e.g TextViews of the list item whose ID is given in R.id.<listitem> (order should be corresponding to the cursorColumns array)};

        SimpleCursorAdapter cursorAdapter = new
        SimpleCursorAdapter(<Activity_name>.this, la,,youtResId, cursor, cursorColumns, resId, 0);*/


        /*String Category contains the options of sorting into mega,fun,tech*/


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
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(new EventListAdapter(getContext(),dbConnectionHelper.getAllCards()));

        mRecyclerView.setAdapter(new EventListAdapter(getContext(),dbConnectionHelper.getData("Fun",4,-1)));//Fun to be replaced with the fragment name
        mRecyclerView.scrollToPosition(0);


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        Event event=(dbConnectionHelper.getData("Fun",4,position)).get(position);
                        Intent i = new Intent(getContext(), EventDetails.class);

                        //TODO: Replace with data fetched from database of the selected event [Done]
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


//        arrayList = new ArrayList<>();
//        for(int i =0;i< 50;i++){
//            arrayList.add(category+" "+(i+1));
//        }

//        recyclerView =(RecyclerView)view.findViewById(R.id.fragmentRecyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
//        recyclerView.setAdapter(new EventListAdapter(arrayList,getActivity()));
//        recyclerView.scrollToPosition(0);
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
