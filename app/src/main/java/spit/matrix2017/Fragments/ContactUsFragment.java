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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import spit.matrix2017.R;

public class ContactUsFragment extends Fragment {

    AppCompatImageButton contact_one_Button,contact_two_Button,save_one_Button,save_two_Button;
    TextView emailId_matrix_TextView;
    Button findOnMap;
    Button visitWebsite;

    public ContactUsFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contactus,container,false);

        findOnMap =(Button)view.findViewById(R.id.findOnMap);
        visitWebsite = (Button) view.findViewById(R.id.visitWebsite);
        contact_one_Button =(AppCompatImageButton)view.findViewById(R.id.contact_us_call_one);
        contact_two_Button =(AppCompatImageButton)view.findViewById(R.id.contact_us_call_two);
        save_one_Button =(AppCompatImageButton)view.findViewById(R.id.contact_us_save_one);
        save_two_Button=(AppCompatImageButton)view.findViewById(R.id.contact_us_save_two);
        emailId_matrix_TextView= (TextView)view.findViewById(R.id.emailId_matrix_TextView);


        emailId_matrix_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = "principal@spit.ac.in";
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:"+to));
                intent.putExtra(Intent.EXTRA_EMAIL,to);
                try{
                    startActivity(Intent.createChooser(intent,"Send Email"));
                }
                catch(Exception e){
                    Toast.makeText(getActivity(),e.getStackTrace().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        visitWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.spit.ac.in")));
            }
        });

        findOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://maps.google.com/maps?q="+Uri.encode(getString(R.string.college_name)));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                try
                {
                    startActivity(mapIntent);
                }
                catch(ActivityNotFoundException ex)
                {
                    try
                    {
                        Intent newIntent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(newIntent);
                    }
                    catch(ActivityNotFoundException innerEx)
                    {
                        Toast.makeText(getContext(), "Please install a maps application", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        View.OnClickListener dialerOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                switch (v.getId()){
                    case R.id.contact_us_call_one:
                        intent.setData(Uri.parse("tel:" + "02226707440"));
                        break;
                    case R.id.contact_us_call_two:
                        intent.setData(Uri.parse("tel:" + "02226287250"));
                        break;
                }
                startActivity(intent);
            }
        };
        contact_one_Button.setOnClickListener(dialerOnClickListener);
        contact_two_Button.setOnClickListener(dialerOnClickListener);

        View.OnClickListener saveOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                switch (v.getId()){
                    case R.id.contact_us_save_one:
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, "S.P.I.T.");
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "02226707440");
                        break;
                    case R.id.contact_us_save_two:
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, "S.P.I.T.");
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "02226708520");
                        break;
                }
                startActivity(intent);
            }
        };
        save_one_Button.setOnClickListener(saveOnClickListener);
        save_two_Button.setOnClickListener(saveOnClickListener);

        return view;
    }
}
