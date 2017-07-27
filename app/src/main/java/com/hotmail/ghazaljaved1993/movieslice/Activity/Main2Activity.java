package com.hotmail.ghazaljaved1993.movieslice.Activity;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hotmail.ghazaljaved1993.movieslice.Fragment.HomeTabs.GenreFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.HomeTabs.Main2ActivityFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.HomeTabs.TVFragment;
import com.hotmail.ghazaljaved1993.movieslice.R;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        setViewPager(mViewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        Log.d("Inside :", "Main2Activity");
    }
    private void setViewPager(ViewPager viewPager) {
        Main2Activity.ViewAdapter adapter = new Main2Activity.ViewAdapter(getSupportFragmentManager());
        adapter.addFragment(new Main2ActivityFragment(), "Movies");
        adapter.addFragment(new TVFragment(), "TV Shows");
        adapter.addFragment(new GenreFragment(), "Genre");
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
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
         searchView = (SearchView) myActionMenuItem.getActionView();

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(Main2Activity.this, SearchResultsActivity.class)));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(String.valueOf(query), false);
            Intent i = new Intent(Main2Activity.this, SearchResultsActivity.class);
            i.putExtra("query", query);
            Log.d("Starting:", "search activity");
            startActivity(i);
        }
    }
}
