package spit.matrix2017.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import spit.matrix2017.R;

/**
 * Created by Tejas on 09/11/2016.
 */

public class Committee extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee);

        toolbar = (Toolbar)findViewById(R.id.toolbar_committee);
        setSupportActionBar(toolbar);
        setTitle("Committee");

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);


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
