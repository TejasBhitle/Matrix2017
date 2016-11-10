package spit.matrix2017.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import spit.matrix2017.R;

/**
 * Created by Tejas on 09/11/2016.
 */

public class Developers extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView email1,email2,email3,email4,email5,email6;
    Button g1,g2,g3,g4,g5,g6,l1,l2,l3,l4,l5,l6;
    ImageView image1,image2,image3,image4,image5,image6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);

        toolbar = (Toolbar)findViewById(R.id.toolbar_developers);
        setSupportActionBar(toolbar);
        setTitle(R.string.developers);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        email1 =(TextView)findViewById(R.id.emailId_tejas);
        email2 =(TextView)findViewById(R.id.emailId_shubham);
        email3 =(TextView)findViewById(R.id.emailId_adnan);
        email4 =(TextView)findViewById(R.id.emailId_rohit);
        email5 =(TextView)findViewById(R.id.emailId_mithil);
        email6 =(TextView)findViewById(R.id.emailId_akshay);

        g1 =(Button)findViewById(R.id.google_tejas);
        g2 =(Button)findViewById(R.id.google_shubham);
        g3 =(Button)findViewById(R.id.google_adnan);
        g4 =(Button)findViewById(R.id.google_rohit);
        g5 =(Button)findViewById(R.id.google_mithil);
        g6 =(Button)findViewById(R.id.google_akshay);

        l1=(Button) findViewById(R.id.linkedin_tejas);
        l2=(Button) findViewById(R.id.linkedin_shubham);
        l3=(Button) findViewById(R.id.linkedin_adnan);
        l4=(Button) findViewById(R.id.linkedin_rohit);
        l5=(Button) findViewById(R.id.linkedin_mithil);
        l6=(Button) findViewById(R.id.linkedin_akshay);

        image1 = (ImageView)findViewById(R.id.pic_tejas);
        image2 =(ImageView)findViewById(R.id.pic_shubham);
        image3 =(ImageView)findViewById(R.id.pic_adnan);
        image4 =(ImageView)findViewById(R.id.pic_rohit);
        image5 =(ImageView)findViewById(R.id.pic_mithil);
        image6 =(ImageView)findViewById(R.id.pic_akshay);


        /*Add Your Pics Here And Not In Xml*/
        Picasso.with(this).load(R.drawable.tejasbhitle).into(image1);
        Picasso.with(this).load(R.drawable.shubhammahajan).into(image2);
        Picasso.with(this).load(R.drawable.shubhammahajan).into(image3);
        Picasso.with(this).load(R.drawable.shubhammahajan).into(image4);
        Picasso.with(this).load(R.drawable.shubhammahajan).into(image5);
        Picasso.with(this).load(R.drawable.shubhammahajan).into(image6);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close);


        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {

                        /*Add Your Intents here*/
                        Intent intent;
                        switch (item.getItemId()){
                            case R.id.homepage_menuItem:
                                finish();
                                break;
                            case R.id.favorites_menuItem:
                                intent = new Intent(getApplicationContext(),Favorites.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.sponsors_menuItem:
                                intent = new Intent(getApplicationContext(),Sponsors.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.contact_us_menuItem:
                                intent = new Intent(getApplicationContext(),ContactUs.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.commitee_menuItem:
                                intent = new Intent(getApplicationContext(),Committee.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.developers_menuItem:
                                // Do Nothing
                                break;
                        }
                        //item.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

        View.OnClickListener linkListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri;
                switch(v.getId()){

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

                    /*LinkedIn Links*/
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
                    default:
                        uri =Uri.parse(getResources().getString(R.string.linkedin_tejas));

                }

                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                try{
                    startActivity(i);
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Error Loading Link",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),e.getStackTrace().toString(),Toast.LENGTH_SHORT).show();
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

    }

}
