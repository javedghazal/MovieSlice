package com.hotmail.ghazaljaved1993.movieslice.Fragment.HomeTabs;

import android.content.Context;
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

import com.hotmail.ghazaljaved1993.movieslice.Fragment.TvTabs.TvAirFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.TvTabs.TvPopFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.TvTabs.TvTopFragment;
import com.hotmail.ghazaljaved1993.movieslice.R;

import java.util.ArrayList;
import java.util.List;


public class TVFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public TVFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) getView().findViewById(R.id.viewpager3);
        setViewPager(mViewPager);

        mTabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        TVFragment.ViewAdapter adapter = new TVFragment.ViewAdapter(getChildFragmentManager());
        adapter.addFragment(new TvPopFragment(), "Popular");
        adapter.addFragment(new TvTopFragment(), "Top Rated");
        adapter.addFragment(new TvAirFragment(), "Airing Today");
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(3);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    class ViewAdapter extends FragmentPagerAdapter {

        private final List<String> mTitles = new ArrayList<>();
        private final List<Fragment> mFragments = new ArrayList<>();

        public ViewAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        /*This method add Fragments to the fragmentList.*/
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }

}
