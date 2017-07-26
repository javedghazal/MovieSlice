package com.hotmail.ghazaljaved1993.movieslice.Fragment.TvTabs;

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
import com.hotmail.ghazaljaved1993.movieslice.data.TV;
import com.hotmail.ghazaljaved1993.movieslice.data.TVResponse;
import com.hotmail.ghazaljaved1993.movieslice.Adapters.TvAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TvAirFragment extends Fragment {

    RecyclerView recycler_tvLat;
    private GridLayoutManager gridLayoutManager;
    TvAdapter tvAdapter;
    List<TV> results = new ArrayList<>();

    public TvAirFragment() {
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
        return inflater.inflate(R.layout.fragment_tv_lat, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_tvLat = (RecyclerView) getView().findViewById(R.id.recycler_TvLat);
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recycler_tvLat.setLayoutManager(gridLayoutManager);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<TVResponse> call = apiService.getTopRatedTV("airing_today", getResources().getString(R.string.api_key));
        call.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                results = response.body().getResults();

                Log.d("Main Activity", response.body().toString());
                if(!results.isEmpty())
                {
                    Log.d("MAIN ACTIVITY", "Number of tv received: " + results.size());
                    Log.d("MAIN ACTIVITY", "" + results.get(0).getTvName());
                    tvAdapter = new TvAdapter(getActivity(), getActivity(), getResources(), results);
                    recycler_tvLat.setAdapter(tvAdapter);
                }
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
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
