package com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hotmail.ghazaljaved1993.movieslice.Retrofit.ApiClient;
import com.hotmail.ghazaljaved1993.movieslice.R;
import com.hotmail.ghazaljaved1993.movieslice.Retrofit.ApiInterface;
import com.hotmail.ghazaljaved1993.movieslice.data.Movie;
import com.hotmail.ghazaljaved1993.movieslice.Adapters.MoviesAdapter;
import com.hotmail.ghazaljaved1993.movieslice.data.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpFragment extends Fragment {

    RecyclerView recycler_up;
    private GridLayoutManager gridLayoutManager;
    MoviesAdapter moviesAdapter;

    public UpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_up = (RecyclerView) getView().findViewById(R.id.recycler_Up);
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recycler_up.setLayoutManager(gridLayoutManager);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<MoviesResponse> call = apiService.getTopRatedMovies("upcoming", getResources().getString(R.string.api_key));
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> results = response.body().getResults();
                Log.d("MAIN ACTIVITY", "Number of movies received: " + results.size());
                Log.d("MAIN ACTIVITY", "" + results.get(0).getTitle());
                moviesAdapter = new MoviesAdapter(getContext(), getActivity(), getResources(), results);
                recycler_up.setAdapter(moviesAdapter);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
