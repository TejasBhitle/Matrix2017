package spit.matrix2017.Fragments;

import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

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


        View.OnClickListener linkListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri;
                switch (v.getId()){

                    /*Google+ links*/
                    case R.id.google_tejas:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_tejas));
                        break;
                    case R.id.google_shubham:
                        uri=Uri.parse(getResources().getString(R.string.googleplus_shubham));
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

                    /*LInkedin Links*/
                    case R.id.linkedin_tejas:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_tejas));
                        break;
                    case R.id.linkedin_shubham:
                        uri=Uri.parse(getResources().getString(R.string.linkedin_shubham));
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
                case R.id.emailId_shubham:
                    to = getResources().getString(R.string.email_shubham);
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
