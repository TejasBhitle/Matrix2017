package spit.matrix2017.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import spit.matrix2017.Fragments.AboutAppFragment;
import spit.matrix2017.Fragments.CommitteeFragment;
import spit.matrix2017.Fragments.ContactUsFragment;
import spit.matrix2017.Fragments.DevelopersFragment;
import spit.matrix2017.Fragments.FavoritesFragment;
import spit.matrix2017.Fragments.MainFragment;
import spit.matrix2017.Fragments.SponsorsFragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_main);

        int[] images = {R.drawable.codatron,
        R.drawable.laser_maze,
        R.drawable.laser_tag,
        R.drawable.virtual_stock_market,
        R.drawable.battle_frontier,
        R.drawable.escape_plan,
        R.drawable.tech_charades,
        R.drawable.tech_xplosion,
        R.drawable.no_escape,
        R.drawable.techeshis_castle,
        R.drawable.technovanza,
        R.drawable.tesseract,
        R.drawable.battle_of_brains,
        R.drawable.blind_car_racing,
        R.drawable.human_foosball,
        R.drawable.lan_gaming,
        R.drawable.lan_mafia,
        R.drawable.mind_that_word,
        R.drawable.pokemon_showdown};

        for(int i: images)
            Picasso.with(getApplicationContext()).load(i).resize(400, 400).centerCrop().fetch();
        
        //instantiation
        toolbar = (Toolbar)findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        navigationView =(NavigationView)findViewById(R.id.navigation_view);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        collapsingToolbarLayout= (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar_main);
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
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

        navigationView.getMenu().getItem(0).setChecked(true);
    }


    public AppBarLayout getAppBarLayout(){
        return appBarLayout;
    }


    public void setupDrawerLayout(){
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        drawerLayout.closeDrawers();
                        if(!item.isChecked()) {
                            FragmentTransaction fragmentTransaction = fm.beginTransaction();
                            boolean isFragmentInStack;
                            switch (item.getItemId()) {
                                case R.id.homepage_menuItem:
                                    isFragmentInStack = fm.popBackStackImmediate(backStageName, 0);
                                    if (!isFragmentInStack) {
                                        MainFragment fragment = MainFragment.newInstance();
                                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                                        backStageName = fragment.getClass().getName();
                                        fragmentTransaction.addToBackStack(backStageName).commit();
                                    }
                                    collapsingToolbarLayout.setTitle("Matrix 17");
                                    break;
                                case R.id.favorites_menuItem:
                                    getSupportFragmentManager().popBackStackImmediate();
                                    fragmentTransaction.replace(R.id.fragment_container, new FavoritesFragment());
                                    fragmentTransaction.addToBackStack(null).commit();
                                    collapsingToolbarLayout.setTitle("Favorites");
                                    break;
                                case R.id.contact_us_menuItem:
                                    getSupportFragmentManager().popBackStackImmediate();
                                    fragmentTransaction.replace(R.id.fragment_container, new ContactUsFragment());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    collapsingToolbarLayout.setTitle("Contact us");
                                    break;
                                case R.id.sponsors_menuItem:
                                    getSupportFragmentManager().popBackStackImmediate();
                                    fragmentTransaction.replace(R.id.fragment_container, new SponsorsFragment());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    collapsingToolbarLayout.setTitle("Sponsors");
                                    break;
                                case R.id.share_app_menuItem:
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND);
                                    intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.playstore_link));
                                    intent.setType("text/plain");
                                    startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_message)));
                                    return true;
                                case R.id.commitee_menuItem:
                                    getSupportFragmentManager().popBackStackImmediate();
                                    fragmentTransaction.replace(R.id.fragment_container, new CommitteeFragment());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    collapsingToolbarLayout.setTitle("Committee");
                                    break;
                                case R.id.developers_menuItem:
                                    getSupportFragmentManager().popBackStackImmediate();
                                    fragmentTransaction.replace(R.id.fragment_container, new DevelopersFragment());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    collapsingToolbarLayout.setTitle("Developers");
                                    break;
                                case R.id.about_menuItem:
                                    getSupportFragmentManager().popBackStackImmediate();
                                    fragmentTransaction.replace(R.id.fragment_container, new AboutAppFragment());
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                    collapsingToolbarLayout.setTitle(getResources().getString(R.string.aboutapp));
                            }
                            for (int i = 0; i < navigationView.getMenu().size(); i++) {
                                navigationView.getMenu().getItem(i).setChecked(false);
                            }
                            item.setChecked(true);
                        }
                        return true;
                    }
                }
        );
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else {
            for (int i = 0; i < navigationView.getMenu().size(); i++) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
            navigationView.getMenu().getItem(0).setChecked(true);
            collapsingToolbarLayout.setTitle("Matrix 17");

            super.onBackPressed();
        }
    }
}