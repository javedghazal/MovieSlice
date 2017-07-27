package com.hotmail.ghazaljaved1993.movieslice.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotmail.ghazaljaved1993.movieslice.Retrofit.ApiClient;
import com.hotmail.ghazaljaved1993.movieslice.R;
import com.hotmail.ghazaljaved1993.movieslice.Retrofit.ApiInterface;
import com.hotmail.ghazaljaved1993.movieslice.data.MoviesResponse;
import com.hotmail.ghazaljaved1993.movieslice.data.TVResponse;
import com.hotmail.ghazaljaved1993.movieslice.Adapters.TrailerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    RecyclerView recycler_trailer;
    private GridLayoutManager gridLayoutManager;
    int idFromMainActivity = 0;
    String image = "";
    String title = "";
    boolean land = true;
    TextView detailOverView;
    TextView detailVote;
    TextView detailRelease;
    TextView detailCount;
    TextView detailRun;
    TextView detailHome;
    boolean tvDetail;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       
        Intent intent = getActivity().getIntent();
        View rootView;
        if(intent!=null)
        {
            idFromMainActivity = intent.getIntExtra("id1", 0);
//            Toast.makeText(getContext(), idFromMainActivity+" ", Toast.LENGTH_SHORT).show();
            image = intent.getStringExtra("image");
            title = intent.getStringExtra("title");
            tvDetail = intent.getBooleanExtra("tvDetail", false);
            if (tvDetail)
            {
                rootView = inflater.inflate(R.layout.fragment_detail_tv, container, false);
            }
            else
                rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            //ImageView image2 = (ImageView) rootView.findViewById(R.id.movie_poster);
            ImageView image_background = (ImageView) rootView.findViewById(R.id.background_image);

            //Toast.makeText(getContext(), image, Toast.LENGTH_SHORT).show();
            ((CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_layout)).setTitle(title);
            Toolbar movieTitle = (Toolbar) rootView.findViewById(R.id.app_bar);
            movieTitle.setTitle(title);

            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500"+image).into(image_background);
            Log.d("RESPONSE ", idFromMainActivity+" ");
            return rootView;
        }
//        }
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(isNetworkAvailable())
        {
            detailOverView = (TextView) getView().findViewById(R.id.detail_overview);
            detailVote = (TextView) getView().findViewById(R.id.detail_vote_average);
            detailRelease = (TextView) getView().findViewById(R.id.detail_release_date);
            detailCount = (TextView) getView().findViewById(R.id.detail_vote_count);
            detailRun = (TextView) getView().findViewById(R.id.detail_runtime);
            detailHome = (TextView) getView().findViewById(R.id.detail_homepage);
            if(tvDetail)
            {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<TVResponse> call = apiService.getTvDetails(idFromMainActivity, getResources().getString(R.string.api_key));
                call.enqueue(new Callback<TVResponse>() {
                    @Override
                    public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
//                        Toast.makeText(getContext(), "OnResponse", Toast.LENGTH_SHORT).show();
                        if(response.isSuccessful())
                        {
                            String overView = response.body().getOverview();
//                            Toast.makeText(getContext(), overView, Toast.LENGTH_SHORT).show();
                            float voteAverage = response.body().getVoteAverage();
                            String first_air_date = response.body().getFirst_air_date();
                            int voteCount = response.body().getVoteCount();
                            int episodes = response.body().getNumber_of_episodes();
                            int seasons = response.body().getNumber_of_seasons();
                            detailOverView.setText(getResources().getString((R.string.overview),overView));
                            detailVote.setText(getResources().getString((R.string.rating), String.valueOf(voteAverage)));
                            detailRelease.setText(getResources().getString((R.string.release_date),first_air_date));
                            detailCount.setText(getActivity().getString(R.string.vote_count, String.valueOf(voteCount)));
                            detailRun.setText(getResources().getString(R.string.episodes, String.valueOf(episodes)));
                            recycler_trailer = (RecyclerView) getView().findViewById(R.id.recycler_Trailer);
                            gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            recycler_trailer.setLayoutManager(gridLayoutManager);
                            fetchTrailersFromId fetch = new fetchTrailersFromId();
                            fetch.execute(idFromMainActivity);
                        }

                        //fetchTrailers(idFromMainActivity);
                    }

                    @Override
                    public void onFailure(Call<TVResponse> call, Throwable t) {

                    }
                });
            }
            else
            {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiService.getMovieDetails(idFromMainActivity, getResources().getString(R.string.api_key));
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                        if(response.isSuccessful())
                        {
                            String overView = response.body().getOverview();
                            float voteAverage = response.body().getVoteAverage();
                            String releaseDate = response.body().getReleaseDate();
                            int voteCount = response.body().getVoteCount();
                            int runtime = response.body().getRuntime();
                            String homepageLink = response.body().getHomepage();
                            detailOverView.setText(getResources().getString((R.string.overview),overView));
                            detailVote.setText(getResources().getString((R.string.rating), String.valueOf(voteAverage)));
                            detailRelease.setText(getResources().getString((R.string.release_date),releaseDate));
                            detailCount.setText(getActivity().getString(R.string.vote_count, String.valueOf(voteCount)));
                            detailRun.setText(getResources().getString(R.string.runtime, String.valueOf(runtime)));
                            if(homepageLink.equals(""))
                                detailHome.setText("No link to homepage available.");
                            else {
                                String homePage = getResources().getString(R.string.homepage, response.body().getHomepage());
                                detailHome.setText((Html.fromHtml("<a href=" + homepageLink + ">" + homePage)));
                            }

                            recycler_trailer = (RecyclerView) getView().findViewById(R.id.recycler_Trailer);
                            gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            recycler_trailer.setLayoutManager(gridLayoutManager);


                            fetchTrailersFromId fetch = new fetchTrailersFromId();
                            fetch.execute(idFromMainActivity);
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
            }

        }
        else{
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("idFromMainActivity", idFromMainActivity+" ");
        outState.putString("image", image);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public int _getScreenOrientation(){
        return getResources().getConfiguration().orientation;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
public class fetchTrailersFromId extends AsyncTask<Integer, Void, List<String>>
    {

        List<String> result;
        @Override
        protected List<String> doInBackground(Integer... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String responseJsonStr = null;
            Uri builtUri;
            try {

                if(tvDetail)
                {
                    builtUri = Uri.parse("http://api.themoviedb.org/3/tv/"+params[0]+"/videos?api_key=aa30ea4bd93242bad0effff696d4e82a");
                }
                else
                {
                    builtUri = Uri.parse("http://api.themoviedb.org/3/movie/"+params[0]+"/videos?api_key=aa30ea4bd93242bad0effff696d4e82a");
                }

                URL url = new URL(builtUri.toString());
                Log.v("^^^^^", "Built TRAILER " + builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.d("@@@@@@", "Outside if");

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();


                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                    responseJsonStr = buffer.toString();

                }
                Log.d("%%%%%%",responseJsonStr);
                result = new ArrayList<>();
                if(buffer.length() != 0) {
                    try {

                        String key = "key";
                        String value = null;

                        JSONObject trailerJson = new JSONObject(responseJsonStr);
                        JSONArray resultArray = trailerJson.getJSONArray("results");

                        if (resultArray.length()>0)
                        {
                            for(int i = 0; i < resultArray.length(); i++)
                            {
                                JSONObject arrayItem = resultArray.getJSONObject(i);
                                value = arrayItem.getString(key);
                                result.add(value);
                            }

                            Log.d("^^^^^^value", value);
                            return result;
                        }


                    } catch (JSONException e) {
                        Log.d("json exception", "" + e);
                    }
                }
            }
            catch (IOException e) {
                Log.e("^^^^^^", "Error ", e);
            }
            return result;
        }

        @Override
        protected void onPostExecute(final List<String> s) {
            if (s.size()>0)
            {
                TrailerAdapter trailerAdapter = new TrailerAdapter(getActivity(), s);
                recycler_trailer.setAdapter(trailerAdapter);
            }
        }
    }
}
