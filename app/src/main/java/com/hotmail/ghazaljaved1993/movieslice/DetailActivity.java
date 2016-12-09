package com.hotmail.ghazaljaved1993.movieslice;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            DetailActivityFragment f = new DetailActivityFragment();
            f = (DetailActivityFragment) getSupportFragmentManager().getFragment(savedInstanceState, "idFromMainActivity");

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        DetailActivityFragment f = new DetailActivityFragment();
        getSupportFragmentManager().putFragment(outState, "idFromMainActivity", f);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
