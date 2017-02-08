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
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import spit.matrix2017.R;

public class DevelopersFragment extends Fragment {

    TextView email1, email2, email3, email4, email5, email6;
    Button g1, g2, g3, g4, g5, g6, l1, l2, l3, l4, l5, l6;
    ImageView image1, image2, image3, image4, image5, image6;

    public DevelopersFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developers,container,false);

        email1 =(TextView)view.findViewById(R.id.emailId_tejas);
        email2 =(TextView)view.findViewById(R.id.emailId_adnan);
        email3 =(TextView)view.findViewById(R.id.emailId_rohit);
        email4 =(TextView)view.findViewById(R.id.emailId_mithil);
        email5 =(TextView)view.findViewById(R.id.emailId_akshay);
        email6 =(TextView)view.findViewById(R.id.emailId_shubham);

        g1 =(Button)view.findViewById(R.id.google_tejas);
        g2 =(Button)view.findViewById(R.id.google_adnan);
        g3 =(Button)view.findViewById(R.id.google_rohit);
        g4 =(Button)view.findViewById(R.id.google_mithil);
        g5 =(Button)view.findViewById(R.id.google_akshay);
        g6 =(Button)view.findViewById(R.id.google_shubham);

        l1=(Button) view.findViewById(R.id.linkedin_tejas);
        l2=(Button) view.findViewById(R.id.linkedin_adnan);
        l3=(Button) view.findViewById(R.id.linkedin_rohit);
        l4=(Button) view.findViewById(R.id.linkedin_mithil);
        l5=(Button) view.findViewById(R.id.linkedin_akshay);
        l6=(Button) view.findViewById(R.id.linkedin_shubham);

        image1 = (ImageView)view.findViewById(R.id.pic_tejas);
        image2 =(ImageView)view.findViewById(R.id.pic_adnan);
        image3 =(ImageView)view.findViewById(R.id.pic_rohit);
        image4 =(ImageView)view.findViewById(R.id.pic_mithil);
        image5 =(ImageView)view.findViewById(R.id.pic_akshay);
        image6 =(ImageView)view.findViewById(R.id.pic_shubham);


        /*Add Your Pics Here And Not In Xml*/
        Picasso.with(getActivity()).load(R.drawable.dev_tejas_bhitle).into(image1);
        Picasso.with(getActivity()).load(R.drawable.dev_adnan_ansari).into(image2);
        Picasso.with(getActivity()).load(R.drawable.dev_rohit_nahata).into(image3);
        Picasso.with(getActivity()).load(R.drawable.dev_mithil_gotarne).into(image4);
        Picasso.with(getActivity()).load(R.drawable.dev_akshay_shah).into(image5);
        Picasso.with(getActivity()).load(R.drawable.dev_shubham_mahajan).into(image6);


        View.OnClickListener linkListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
                switch (v.getId()){

                    /*Google+ links*/
                    case R.id.google_tejas:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_tejas));
                        break;
                    case R.id.google_adnan:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_adnan));
                        break;
                    case R.id.google_rohit:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_rohit));
                        break;
                    case R.id.google_mithil:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_mithil));
                        break;
                    case R.id.google_akshay:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_akshay));
                        break;
                    case R.id.google_shubham:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_shubham));
                        break;

                    /*LInkedin Links*/
                    case R.id.linkedin_tejas:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_tejas));
                        break;
                    case R.id.linkedin_adnan:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_adnan));
                        break;
                    case R.id.linkedin_rohit:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_rohit));
                        break;
                    case R.id.linkedin_mithil:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_mithil));
                        break;
                    case R.id.linkedin_akshay:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_akshay));
                        break;
                    case R.id.linkedin_shubham:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_shubham));
                        break;

                    default:uri =Uri.parse(getResources().getString(R.string.linkedin_tejas));
                }
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                try{
                    startActivity(i);
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"Error Loading Link",Toast.LENGTH_SHORT).show();
                }

            }
        };

    View.OnClickListener emailListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String to="";
            switch (v.getId()){
                /*Email Ids*/
                case R.id.emailId_tejas:
                    to = getResources().getString(R.string.email_tejas);
                    break;
                case R.id.emailId_adnan:
                    to = getResources().getString(R.string.email_adnan);
                    break;
                case R.id.emailId_rohit:
                    to = getResources().getString(R.string.email_rohit);
                    break;
                case R.id.emailId_mithil:
                    to = getResources().getString(R.string.email_mithil);
                    break;
                case R.id.emailId_akshay:
                    to = getResources().getString(R.string.email_akshay);
                    break;
                case R.id.emailId_shubham:
                    to = getResources().getString(R.string.email_shubham);
                    break;
            }
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:"+to));
            intent.putExtra(Intent.EXTRA_EMAIL,to);
            try{
                startActivity(Intent.createChooser(intent,"Send Email"));
            }
            catch(Exception e){
                Toast.makeText(getActivity(),e.getStackTrace().toString(), Toast.LENGTH_SHORT).show();
            }
            }
        };
        email1.setOnClickListener(emailListener);
        email2.setOnClickListener(emailListener);
        email3.setOnClickListener(emailListener);
        email4.setOnClickListener(emailListener);
        email5.setOnClickListener(emailListener);
        email6.setOnClickListener(emailListener);
        g1.setOnClickListener(linkListener);
        g2.setOnClickListener(linkListener);
        g3.setOnClickListener(linkListener);
        g4.setOnClickListener(linkListener);
        g5.setOnClickListener(linkListener);
        g6.setOnClickListener(linkListener);
        l1.setOnClickListener(linkListener);
        l2.setOnClickListener(linkListener);
        l3.setOnClickListener(linkListener);
        l4.setOnClickListener(linkListener);
        l5.setOnClickListener(linkListener);
        l6.setOnClickListener(linkListener);

        return view;
    }
}
