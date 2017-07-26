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
import com.hotmail.ghazaljaved1993.movieslice.data.GenreMovies;
import com.hotmail.ghazaljaved1993.movieslice.Adapters.GenreMoviesAdapter;
import com.hotmail.ghazaljaved1993.movieslice.data.Results;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreDetailActivity extends AppCompatActivity {


    RecyclerView recycler_genreMovies;
    private GridLayoutManager gridLayoutManager;
    GenreMoviesAdapter genreMoviesAdapter;
    Toolbar toolbar;
    List<Results> results = new ArrayList<>();
    int genreId = 0;
    String genreName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_detail);
        Intent i = getIntent();
        if(i!=null)
        {
            genreId = i.getIntExtra("genreId", 0);
            genreName = i.getStringExtra("genreName");
//            Toast.makeText(this, i.getIntExtra("genreId", 0)+" ", Toast.LENGTH_SHORT).show();
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar_Genre);
        toolbar.setTitle(genreName);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_navigate_before_white_24dp);
        }

        recycler_genreMovies = (RecyclerView) findViewById(R.id.recycler_GenreMovies);
        gridLayoutManager = new GridLayoutManager(this, 1);
        recycler_genreMovies.setLayoutManager(gridLayoutManager);
        String api_key = "aa30ea4bd93242bad0effff696d4e82a";
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<GenreMovies> call = apiService.getGenreMovies(genreId, api_key);
        call.enqueue(new Callback<GenreMovies>() {
            @Override
            public void onResponse(Call<GenreMovies> call, Response<GenreMovies> response) {
                results = response.body().getResults();
                Log.d("GENRE", response.code()+"");
                Log.d("GENRE", results.get(0).getTitle());
                genreMoviesAdapter = new GenreMoviesAdapter(getApplicationContext(), results);
                recycler_genreMovies.setAdapter(genreMoviesAdapter);
            }

            @Override
            public void onFailure(Call<GenreMovies> call, Throwable t) {
                Toast.makeText(getApplication(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
