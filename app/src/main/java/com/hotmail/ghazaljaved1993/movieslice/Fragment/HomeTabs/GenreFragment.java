package com.hotmail.ghazaljaved1993.movieslice.Fragment.HomeTabs;

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
import com.hotmail.ghazaljaved1993.movieslice.data.Genre;
import com.hotmail.ghazaljaved1993.movieslice.Adapters.GenreAdapter;
import com.hotmail.ghazaljaved1993.movieslice.data.GenreResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreFragment extends Fragment {

    RecyclerView recycler_genre;
    private GridLayoutManager gridLayoutManager;
    GenreAdapter genreAdapter;
    List<Genre> results = new ArrayList<>();
    public GenreFragment() {
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
        return inflater.inflate(R.layout.fragment_genre, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_genre = (RecyclerView) getView().findViewById(R.id.recycler_Genre);
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recycler_genre.setLayoutManager(gridLayoutManager);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<GenreResponse> call = apiService.getGenre(getResources().getString(R.string.api_key));
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                results = response.body().genres;
                Log.d("GENRE", response.code()+"");
                Log.d("GENRE", results.get(0).getName()+"");
                genreAdapter = new GenreAdapter(getActivity(), getActivity(), getResources(), results);
                recycler_genre.setAdapter(genreAdapter);
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
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
