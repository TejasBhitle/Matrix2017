package spit.matrix2017.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spit.matrix2017.Activities.MainActivity;
import spit.matrix2017.EventListFragment;
import spit.matrix2017.R;

/**
 * Created by Tejas on 12/11/2016.
 */

public class MainFragment extends Fragment {


    TabLayout tabLayout;
    ViewPager viewPager;

    public MainFragment(){}

    public static MainFragment newInstance(){
        MainFragment mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        viewPager = (ViewPager)view.findViewById(R.id.fragment_viewPager_tabLayout);
        tabLayout =(TabLayout)view.findViewById(R.id.main_fragment_tabLayout);


        AppBarLayout main_appBarLayout = ((MainActivity)getActivity()).getAppBarLayout();
        main_appBarLayout.setExpanded(true);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return view;
    }


    class MyAdapter extends FragmentPagerAdapter{

        MyAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            EventListFragment megaEventPage = EventListFragment.newInstance(getResources().getString(R.string.mega));
            EventListFragment techEventPage =  EventListFragment.newInstance(getResources().getString(R.string.tech));
            EventListFragment funEventPage =  EventListFragment.newInstance(getResources().getString(R.string.fun));
            switch (position){
                case 0:return megaEventPage;
                case 1 :return techEventPage;
                case 2: return funEventPage;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return getResources().getString(R.string.mega);
                case 1:return getResources().getString(R.string.tech);
                case 2:return getResources().getString(R.string.fun);
            }
            return null;
        }
    }

}
