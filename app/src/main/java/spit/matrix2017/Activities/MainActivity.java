package spit.matrix2017.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import spit.matrix2017.CustomFragmentPagerAdapter;
import spit.matrix2017.EventListFragment;
import spit.matrix2017.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomFragmentPagerAdapter customFragmentPagerAdapter;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //instantiation
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        viewPager = (ViewPager) findViewById(R.id.viewPager_tabLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_main);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar_main);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);


        /*Code to make title visible only in collapsed state*/
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {

                        /*Add Your Intents here*/
                        switch (item.getItemId()) {
                            case R.id.sponsors_menuItem:

                                break;
                            case R.id.commitee_menuItem:

                                break;
                            case R.id.aboutus_menuItem:

                                break;
                            case R.id.developers_menuItem:

                                break;
                        }
                        //item.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );

        addEventFragmentsToViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void addEventFragmentsToViewPager(ViewPager viewPager) {
        customFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());

        EventListFragment megaEventPage = new EventListFragment();
        megaEventPage.setCategory(getResources().getString(R.string.mega));

        EventListFragment techEventPage = new EventListFragment();
        techEventPage.setCategory(getResources().getString(R.string.tech));

        EventListFragment funEventPage = new EventListFragment();
        funEventPage.setCategory(getResources().getString(R.string.fun));

        customFragmentPagerAdapter.AddFragment(megaEventPage, getResources().getString(R.string.mega));
        customFragmentPagerAdapter.AddFragment(techEventPage, getResources().getString(R.string.tech));
        customFragmentPagerAdapter.AddFragment(funEventPage, getResources().getString(R.string.fun));
        viewPager.setAdapter(customFragmentPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.share_menuItem:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.playstore_link));
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_message)));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
