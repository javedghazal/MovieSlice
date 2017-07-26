package com.hotmail.ghazaljaved1993.movieslice.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.hotmail.ghazaljaved1993.movieslice.Retrofit.ApiClient;
import com.hotmail.ghazaljaved1993.movieslice.R;
import com.hotmail.ghazaljaved1993.movieslice.Retrofit.ApiInterface;
import com.hotmail.ghazaljaved1993.movieslice.data.Movie;
import com.hotmail.ghazaljaved1993.movieslice.data.MoviesResponse;
import com.hotmail.ghazaljaved1993.movieslice.Adapters.MoviesAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    RecyclerView recycler_search;
    private GridLayoutManager gridLayoutManager;
    private MoviesAdapter moviesAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Log.d("Inside: ", "SearchResultsActivity");
        toolbar = (Toolbar) findViewById(R.id.toolbar_searchResults);
        setSupportActionBar(toolbar);
        recycler_search = (RecyclerView) findViewById(R.id.recycler_search);
        gridLayoutManager = new GridLayoutManager(this, 1);
        recycler_search.setLayoutManager(gridLayoutManager);
        Intent i = getIntent();
        if(i!=null)
        {
            Log.d("Inside: ", "I IS NOT NULL");

            final String query = i.getStringExtra("query");
            setTitle("Search Results for "+query);
            String api_key = "aa30ea4bd93242bad0effff696d4e82a";
            ApiInterface apiService =
                    ApiClient.getClient().create(ApiInterface.class);
            Call<MoviesResponse> call = apiService.getSearchMovie(query, api_key);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> results = response.body().getResults();
//                    Log.d("SEARCH ACTIVITY", "Number of search received: " + results.size());
//                    Log.d("SEARCH ACTIVITY", "" + results.get(0).getTitle());
                    if(!results.isEmpty())
                    {
                        moviesAdapter = new MoviesAdapter(SearchResultsActivity.this, SearchResultsActivity.this, getResources(), results);
                        recycler_search.setAdapter(moviesAdapter);
                    }
                    else
                    {
                        setTitle("No Results found for "+query);
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
