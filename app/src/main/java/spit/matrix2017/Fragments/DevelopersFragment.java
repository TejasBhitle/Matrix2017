package spit.matrix2017.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import spit.matrix2017.Activities.MainActivity;
import spit.matrix2017.R;

/**
 * Created by DELL on 12/11/2016.
 */

public class DevelopersFragment extends Fragment {

    TextView email1,email2,email3,email4,email5,email6;
    Button g1,g2,g3,g4,g5,g6,l1,l2,l3,l4,l5,l6;
    ImageView image1,image2,image3,image4,image5,image6;

    public DevelopersFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developers,container,false);
        AppBarLayout appBarLayout = ((MainActivity)getActivity()).getAppBarLayout();
        appBarLayout.setExpanded(false);

        email1 =(TextView)view.findViewById(R.id.emailId_tejas);
        email2 =(TextView)view.findViewById(R.id.emailId_shubham);
        email3 =(TextView)view.findViewById(R.id.emailId_adnan);
        email4 =(TextView)view.findViewById(R.id.emailId_rohit);
        email5 =(TextView)view.findViewById(R.id.emailId_mithil);
        email6 =(TextView)view.findViewById(R.id.emailId_akshay);

        g1 =(Button)view.findViewById(R.id.google_tejas);
        g2 =(Button)view.findViewById(R.id.google_shubham);
        g3 =(Button)view.findViewById(R.id.google_adnan);
        g4 =(Button)view.findViewById(R.id.google_rohit);
        g5 =(Button)view.findViewById(R.id.google_mithil);
        g6 =(Button)view.findViewById(R.id.google_akshay);

        l1=(Button) view.findViewById(R.id.linkedin_tejas);
        l2=(Button) view.findViewById(R.id.linkedin_shubham);
        l3=(Button) view.findViewById(R.id.linkedin_adnan);
        l4=(Button) view.findViewById(R.id.linkedin_rohit);
        l5=(Button) view.findViewById(R.id.linkedin_mithil);
        l6=(Button) view.findViewById(R.id.linkedin_akshay);

        image1 = (ImageView)view.findViewById(R.id.pic_tejas);
        image2 =(ImageView)view.findViewById(R.id.pic_shubham);
        image3 =(ImageView)view.findViewById(R.id.pic_adnan);
        image4 =(ImageView)view.findViewById(R.id.pic_rohit);
        image5 =(ImageView)view.findViewById(R.id.pic_mithil);
        image6 =(ImageView)view.findViewById(R.id.pic_akshay);


        /*Add Your Pics Here And Not In Xml*/
        Picasso.with(getActivity()).load(R.drawable.tejasbhitle).into(image1);
        Picasso.with(getActivity()).load(R.drawable.shubhammahajan).into(image2);
        Picasso.with(getActivity()).load(R.drawable.shubhammahajan).into(image3);
        Picasso.with(getActivity()).load(R.drawable.shubhammahajan).into(image4);
        Picasso.with(getActivity()).load(R.drawable.shubhammahajan).into(image5);
        Picasso.with(getActivity()).load(R.drawable.shubhammahajan).into(image6);


        return view;
    }
}
