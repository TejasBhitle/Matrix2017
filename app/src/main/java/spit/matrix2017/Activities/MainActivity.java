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

package spit.matrix2017.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import me.relex.circleindicator.CircleIndicator;
import spit.matrix2017.Fragments.AboutAppFragment;
import spit.matrix2017.Fragments.CommitteeFragment;
import spit.matrix2017.Fragments.ContactUsFragment;
import spit.matrix2017.Fragments.DevelopersFragment;
import spit.matrix2017.Fragments.FavoritesFragment;
import spit.matrix2017.Fragments.MainFragment;
import spit.matrix2017.Fragments.SponsorsFragment;
import spit.matrix2017.HelperClasses.CustomPagerAdapter;
import spit.matrix2017.HelperClasses.CustomViewPager;
import spit.matrix2017.R;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private AppBarLayout appBarLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    CollapsingToolbarLayout collapsingToolbarLayout;

    FragmentManager fm;
    String backStageName;

    CustomPagerAdapter mCustomPagerAdapter;
    CustomViewPager mViewPager;

    private static final long DRAWER_DELAY = 250;
    private static int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 21)
            setContentView(R.layout.activity_main_v21);
        else
            setContentView(R.layout.activity_main);

        int[] images = {
                R.drawable.event_daniel_fernandes,
                R.drawable.event_techshiksha,
                R.drawable.event_ethical_hacking,
                R.drawable.event_startup_showcase,
                R.drawable.event_hackathon,
                R.drawable.event_project_mania,
                R.drawable.event_sky_observation,
                R.drawable.event_vsm,
                R.drawable.event_codatron,
                R.drawable.event_laser_maze,
                R.drawable.event_laser_tag,

                R.drawable.event_tech_charades,
                R.drawable.event_battle_frontier,
                R.drawable.event_escape_plan,
                R.drawable.event_tech_xplosion,
                R.drawable.event_no_escape,
                R.drawable.event_techeshis_castle,
                R.drawable.event_tesseract,

                R.drawable.event_human_foosball,
                R.drawable.event_battle_of_brains,
                R.drawable.event_lan_gaming,
                R.drawable.event_pokemon_showdown,
                R.drawable.event_lan_mafia,
                R.drawable.event_mind_that_word,

        };

        for(int i: images)
            Picasso.with(getApplicationContext()).load(i).resize(400, 400).centerCrop().fetch();

        //ViewPager
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (CustomViewPager) findViewById(R.id.viewpager_main);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        mViewPager.setAdapter(mCustomPagerAdapter);
        indicator.setViewPager(mViewPager);

        final Handler h = new Handler(Looper.getMainLooper());
        final Runnable r = new Runnable() {
            public void run() {
                mViewPager.setCurrentItem((mViewPager.getCurrentItem()+1)%NUM_PAGES, true);
                h.postDelayed(this, 5000);
            }
        };
        h.postDelayed(r, 5000);

        //instantiation
        toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        navigationView =(NavigationView)findViewById(R.id.navigation_view);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        collapsingToolbarLayout= (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar_main);
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar_layout);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        AppBarLayout.Behavior appBarLayoutBehaviour = new AppBarLayout.Behavior();
        appBarLayoutBehaviour.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
            @Override
            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                return false;
            }
        });
        layoutParams.setBehavior(appBarLayoutBehaviour);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        if(savedInstanceState == null){
            fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            MainFragment mainFragment = MainFragment.newInstance();
            transaction.replace(R.id.fragment_container,mainFragment).commit();
        }

        setupDrawerLayout();

        NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
        if (navigationMenuView != null) {
            navigationMenuView.setVerticalScrollBarEnabled(false);
        }
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    public void setupDrawerLayout(){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        drawerLayout.closeDrawers();
                        if(!item.isChecked()) {
                            final FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                            switch (item.getItemId()) {
                                case R.id.homepage_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            boolean isFragmentInStack = fm.popBackStackImmediate(backStageName, 0);
                                            if (!isFragmentInStack) {
                                                MainFragment fragment = MainFragment.newInstance();
                                                fragmentTransaction.replace(R.id.fragment_container, fragment);
                                                backStageName = fragment.getClass().getName();
                                                fragmentTransaction.addToBackStack(backStageName).commit();
                                            }
                                            appBarLayout.setExpanded(true, true);
                                            collapsingToolbarLayout.setTitle("Matrix 17");
                                        }
                                    }, DRAWER_DELAY);
                                    break;
                                case R.id.favorites_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getSupportFragmentManager().popBackStackImmediate();
                                            fragmentTransaction.replace(R.id.fragment_container, new FavoritesFragment());
                                            appBarLayout.setExpanded(false, true);
                                            fragmentTransaction.addToBackStack(null).commit();
                                            collapsingToolbarLayout.setTitle("Favorites");
                                        }
                                    }, DRAWER_DELAY);
                                    break;
                                case R.id.sponsors_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getSupportFragmentManager().popBackStackImmediate();
                                            fragmentTransaction.replace(R.id.fragment_container, new SponsorsFragment());
                                            appBarLayout.setExpanded(false, true);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            collapsingToolbarLayout.setTitle("Sponsors");
                                        }
                                    }, DRAWER_DELAY);
                                    break;

                                case R.id.commitee_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getSupportFragmentManager().popBackStackImmediate();
                                            fragmentTransaction.replace(R.id.fragment_container, new CommitteeFragment());
                                            appBarLayout.setExpanded(false, true);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            collapsingToolbarLayout.setTitle("Committee");
                                        }
                                    }, DRAWER_DELAY);
                                    break;
                                case R.id.developers_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getSupportFragmentManager().popBackStackImmediate();
                                            fragmentTransaction.replace(R.id.fragment_container, new DevelopersFragment());
                                            appBarLayout.setExpanded(false, true);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            collapsingToolbarLayout.setTitle("Developers");
                                        }
                                    }, DRAWER_DELAY);
                                    break;
                                case R.id.contact_us_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getSupportFragmentManager().popBackStackImmediate();
                                            fragmentTransaction.replace(R.id.fragment_container, new ContactUsFragment());
                                            appBarLayout.setExpanded(false, true);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            collapsingToolbarLayout.setTitle("Contact us");
                                        }
                                    }, DRAWER_DELAY);
                                    break;

                                case R.id.share_app_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent();
                                            intent.setAction(Intent.ACTION_SEND);
                                            intent.putExtra(Intent.EXTRA_TEXT, "Check out the official app for Matrix 17!\n\n" + getResources().getString(R.string.playstore_link));
                                            intent.setType("text/plain");
                                            startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_message)));
                                        }
                                    }, DRAWER_DELAY);
                                    return true;
                                case R.id.rate_app_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(getResources().getString(R.string.playstore_link)));
                                            startActivity(intent);
                                        }
                                    }, DRAWER_DELAY);
                                    return true;
                                case R.id.about_menuItem:
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getSupportFragmentManager().popBackStackImmediate();
                                            fragmentTransaction.replace(R.id.fragment_container, new AboutAppFragment());
                                            appBarLayout.setExpanded(false, true);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                            collapsingToolbarLayout.setTitle(getResources().getString(R.string.aboutapp));
                                        }
                                    }, DRAWER_DELAY);
                            }
                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Uri uri=null;
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.follow_us:
                return true;
            case R.id.menu_visit_website:
                uri = Uri.parse(getResources().getString(R.string.matrix_website));
                break;
            case R.id.menu_follow_facebook:
                uri = Uri.parse(getResources().getString(R.string.matrix_fb_link));
                break;
            case R.id.menu_follow_twitter:
                uri = Uri.parse(getResources().getString(R.string.matrix_twit_link));
                break;
            case R.id.menu_follow_instagram:
                uri = Uri.parse(getResources().getString(R.string.matrix_insta_link));
                break;
            case R.id.menu_follow_snapchat:
                uri = Uri.parse(getResources().getString(R.string.matrix_snap_link));
                break;
        }

        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else {
            navigationView.getMenu().getItem(0).setChecked(true);
            collapsingToolbarLayout.setTitle("Matrix 17");
            appBarLayout.setExpanded(true, true);

            super.onBackPressed();
        }
    }
}