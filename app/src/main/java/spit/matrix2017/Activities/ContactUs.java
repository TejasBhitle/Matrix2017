package spit.matrix2017.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import spit.matrix2017.R;

/**
 * Created by Tejas on 10/11/2016.
 */

public class ContactUs extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    AppCompatImageButton contact_one_Button,contact_two_Button,save_one_Button,save_two_Button;
    TextView emailId_matrix_TextView;
    Button findOnMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        toolbar = (Toolbar)findViewById(R.id.toolbar_committee);
        setSupportActionBar(toolbar);
        setTitle(R.string.contact_us);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        findOnMap =(Button)findViewById(R.id.findOnMap);
        contact_one_Button =(AppCompatImageButton)findViewById(R.id.contact_us_call_one);
        contact_two_Button =(AppCompatImageButton)findViewById(R.id.contact_us_call_two);
        save_one_Button =(AppCompatImageButton)findViewById(R.id.contact_us_save_one);
        save_two_Button=(AppCompatImageButton)findViewById(R.id.contact_us_save_two);
        emailId_matrix_TextView= (TextView)findViewById(R.id.emailId_matrix_TextView);

        emailId_matrix_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to = getResources().getString(R.string.email_matrix);
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
        });

        findOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:0,0?q="+ Uri.encode(getString(R.string.college_name)));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,uri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        View.OnClickListener dialerOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (v.getId()){
                    case R.id.contact_us_call_one:
                        intent.setData(Uri.parse("tel:" + getResources().getString(R.string.contact_us_contact1_number)));
                        break;
                    case R.id.contact_us_call_two:
                        intent.setData(Uri.parse("tel:" + getResources().getString(R.string.contact_us_contact2_number)));
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
                        intent.putExtra(ContactsContract.Intents.Insert.NAME,
                                getResources().getString(R.string.contact_us_contact1_name));
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE,
                                Long.valueOf(getResources().getString(R.string.contact_us_contact1_number)));
                        break;
                    case R.id.contact_us_save_two:
                        intent.putExtra(ContactsContract.Intents.Insert.NAME,
                                getResources().getString(R.string.contact_us_contact2_name));
                        intent.putExtra(ContactsContract.Intents.Insert.PHONE,
                                Long.valueOf(getResources().getString(R.string.contact_us_contact2_number)));
                        break;
                }
                startActivity(intent);
            }
        };
        save_one_Button.setOnClickListener(saveOnClickListener);
        save_two_Button.setOnClickListener(saveOnClickListener);


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
                            case R.id.contact_us_menuItem:
                                //do Nothing
                                break;
                            case R.id.sponsors_menuItem:
                                intent = new Intent(getApplicationContext(),Sponsors.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.commitee_menuItem:
                                // Do Nothing
                                break;
                            case R.id.developers_menuItem:
                                intent = new Intent(getApplicationContext(),Developers.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                        //item.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );


    }
}
