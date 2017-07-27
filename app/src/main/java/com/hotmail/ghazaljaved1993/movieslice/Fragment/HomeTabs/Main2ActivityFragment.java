package com.hotmail.ghazaljaved1993.movieslice.Fragment.HomeTabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.NowFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.PopularFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.TopFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.UpFragment;
import com.hotmail.ghazaljaved1993.movieslice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class Main2ActivityFragment extends Fragment {

    boolean tab = false;
    String image = "";
    String id = "";
    boolean dataFetched = false;
    private ArrayAdapter<String> mForecastAdapter;
    int densityDpi = 0;
    private ListView lv;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Main2ActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        densityDpi = dm.densityDpi;
        if(!isNetworkAvailable())
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setMessage("No Internet Connection. Go to Settings and Turn on Internet?");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which){
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }

            });
            alertDialog.create().show();
        }


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) getView().findViewById(R.id.viewpager2);
        setViewPager(mViewPager);

        mTabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        Main2ActivityFragment.ViewAdapter adapter = new Main2ActivityFragment.ViewAdapter(getChildFragmentManager());
        adapter.addFragment(new PopularFragment(), "Popular");
        adapter.addFragment(new TopFragment(), "Top Rated");
        adapter.addFragment(new UpFragment(), "Upcoming");
        adapter.addFragment(new NowFragment(), "Now Playing");
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(3);
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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView;
        rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mForecastAdapter != null){
            Log.d("^^^^^^saveInstance", "onSaveInstance");
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main2_fragment, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
    }

}
