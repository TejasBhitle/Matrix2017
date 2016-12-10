package spit.matrix2017.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import spit.matrix2017.Fragments.Committees.AdminFragment;
import spit.matrix2017.Fragments.Committees.DecorFragment;
import spit.matrix2017.Fragments.Committees.EventsFragment;
import spit.matrix2017.Fragments.Committees.ExecFragment;
import spit.matrix2017.Fragments.Committees.MarketingFragment;
import spit.matrix2017.Fragments.Committees.PRFragment;
import spit.matrix2017.Fragments.Committees.SecurityFragment;
import spit.matrix2017.Fragments.Committees.TechFragment;
import spit.matrix2017.Fragments.Committees.LeadersFragment;
import spit.matrix2017.R;


public class CommitteeFragment
        extends Fragment{
    private ViewPager viewPager;
    private TabLayout tabLayout;

    public CommitteeFragment(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_committee,container,false);
        viewPager = (ViewPager) view.findViewById(R.id.committee_viewPager_tabLayout);
        tabLayout = (TabLayout) view.findViewById(R.id.committee_fragment_tabLayout);

        viewPager.setAdapter(new CommitteeAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        return view;
    }

    class CommitteeAdapter
        extends FragmentPagerAdapter{

        CommitteeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            LeadersFragment leadersFragment = new LeadersFragment();
            ExecFragment execFragment = new ExecFragment();
            TechFragment techFragment = new TechFragment();
            PRFragment prFragment = new PRFragment();
            MarketingFragment marketingFragment = new MarketingFragment();
            EventsFragment eventsFragment = new EventsFragment();
            DecorFragment decorFragment = new DecorFragment();
            AdminFragment adminFragment = new AdminFragment();
            SecurityFragment securityFragment = new SecurityFragment();

            switch(position)
            {
                case 0:
                    return leadersFragment;
                case 1:
                    return execFragment;
                case 2:
                    return techFragment;
                case 3:
                    return prFragment;
                case 4:
                    return marketingFragment;
                case 5:
                    return eventsFragment;
                case 6:
                    return decorFragment;
                case 7:
                    return adminFragment;
                case 8:
                    return securityFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position)
            {
                case 0:
                    return "Leaders";
                case 1:
                    return "Exec";
                case 2:
                    return "Tech";
                case 3:
                    return "PR";
                case 4:
                    return "Marketing";
                case 5:
                    return "Events";
                case 6:
                    return "Decor";
                case 7:
                    return "Admin";
                case 8:
                    return "Security";
            }
            return null;
        }
    }
}