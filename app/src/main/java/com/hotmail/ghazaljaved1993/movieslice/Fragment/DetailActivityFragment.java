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
        //        if(_getScreenOrientation()!=2) {
//
//            Intent intent = getActivity().getIntent();
//            idFromMainActivity = intent.getStringExtra("id1");
//            image = intent.getStringExtra("image");
//            title = intent.getStringExtra("title");
//            //Toast.makeText(getContext(), image, Toast.LENGTH_SHORT).show();
//            ((CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_layout)).setTitle(title);
//
//
//            //Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500"+image).into(image2);
//            Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500"+image).into(image_background);
//
//        }
//        else
//        {
//            idFromMainActivity = getArguments().getString("id1");
//            image = getArguments().getString("image");
//            land = false;
//
//        }


//        if(isNetworkAvailable())
//        {
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


            //Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500"+image).into(image2);
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
//                String overView = "iii";
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
//                tv9.setMovementMethod(LinkMovementMethod.getInstance());
//                            if(homepageLink.equals(""))
//                                detailHome.setText("No link to homepage available.");
//                            else {
//                                String homePage = getResources().getString(R.string.homepage, response.body().getHomepage());
//                                detailHome.setText((Html.fromHtml("<a href=" + homepageLink + ">" + homePage)));
//                            }
                            recycler_trailer = (RecyclerView) getView().findViewById(R.id.recycler_Trailer);
                            gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            recycler_trailer.setLayoutManager(gridLayoutManager);


//                if (_getScreenOrientation() != 2)
//                {
//                    tv6.setTextSize(20);
//                }
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
//                String overView = "iii";
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
//                tv9.setMovementMethod(LinkMovementMethod.getInstance());
                            if(homepageLink.equals(""))
                                detailHome.setText("No link to homepage available.");
                            else {
                                String homePage = getResources().getString(R.string.homepage, response.body().getHomepage());
                                detailHome.setText((Html.fromHtml("<a href=" + homepageLink + ">" + homePage)));
                            }

                            recycler_trailer = (RecyclerView) getView().findViewById(R.id.recycler_Trailer);
                            gridLayoutManager = new GridLayoutManager(getContext(), 1);
                            recycler_trailer.setLayoutManager(gridLayoutManager);


//                if (_getScreenOrientation() != 2)
//                {
//                    tv6.setTextSize(20);
//                }
                            fetchTrailersFromId fetch = new fetchTrailersFromId();
                            fetch.execute(idFromMainActivity);
                        }

                        //fetchTrailers(idFromMainActivity);
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
//        if(savedInstanceState != null)
//        {
//            idFromMainActivity = savedInstanceState.getString("idFromMainActivity");
//            image = savedInstanceState.getString("image");
//        }
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
//    public void startTask() {
//
////        fetchdatafromid fetch = new fetchdatafromid();
////        fetch.execute(idFromMainActivity);
////        fetchTrailersFromId fetchTrailer = new fetchTrailersFromId();
////        fetchTrailer.execute(idFromMainActivity);
//
//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//        Call<MoviesResponse> call = apiService.getMovieDetails(idFromMainActivity, getResources().getString(R.string.api_key));
//        call.enqueue(new Callback<MoviesResponse>() {
//            @Override
//            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                TextView tv6 = (TextView) getActivity().findViewById(R.id.detail_overview);
//                TextView tv5 = (TextView) getActivity().findViewById(R.id.detail_vote_average);
//                TextView tv3 = (TextView) getActivity().findViewById(R.id.detail_release_date);
//                TextView tv7 = (TextView) getActivity().findViewById(R.id.detail_vote_count);
//                TextView tv8 = (TextView) getActivity().findViewById(R.id.detail_runtime);
//                TextView tv9 = (TextView) getActivity().findViewById(R.id.detail_homepage);
//                String overView = response.body().getOverview();
//                float voteAverage = response.body().getVoteAverage();
//                String releaseDate = response.body().getReleaseDate();
//                int voteCount = response.body().getVoteCount();
//                int runtime = response.body().getRuntime();
//                String homepageLink = response.body().getHomepage();
//                tv6.setText(getResources().getString((R.string.overview),overView));
//                tv5.setText(getResources().getString((R.string.rating), String.valueOf(voteAverage)));
//                tv3.setText(getResources().getString((R.string.release_date),releaseDate));
//                tv7.setText(getActivity().getString(R.string.vote_count, String.valueOf(voteCount)));
//                tv8.setText(getResources().getString(R.string.runtime, String.valueOf(runtime)));
//                tv9.setMovementMethod(LinkMovementMethod.getInstance());
//                if(homepageLink.equals(""))
//                    tv9.setText("No link to homepage available.");
//                else {
//                    String homePage = getResources().getString(R.string.homepage, response.body().getHomepage());
//                    tv9.setText((Html.fromHtml("<a href=" + homepageLink + ">" + homePage)));
//                }
//                TrailerAdapter trailerAdapter = new TrailerAdapter(getActivity(), idFromMainActivity);
//
//
////                if (_getScreenOrientation() != 2)
////                {
////                    tv6.setTextSize(20);
////                }
//
////                fetchTrailersFromId fetch = new fetchTrailersFromId();
////                fetch.execute(idFromMainActivity);
//                //fetchTrailers(idFromMainActivity);
//            }
//
//            @Override
//            public void onFailure(Call<MoviesResponse> call, Throwable t) {
//
//            }
//        });
//
//    }

//    public class fetchdatafromid extends AsyncTask<String, Void, String[]> {
//        //TextView tv2 = (TextView) getActivity().findViewById(R.id.detail_title);
//        TextView tv6 = (TextView) getActivity().findViewById(R.id.detail_overview);
//        TextView tv5 = (TextView) getActivity().findViewById(R.id.detail_vote_average);
//        TextView tv3 = (TextView) getActivity().findViewById(R.id.detail_release_date);
//        TextView tv7 = (TextView) getActivity().findViewById(R.id.detail_vote_count);

//        private final String LOG_TAG = fetchdatafromid.class.getSimpleName();
//        String responseJsonStr = null;
//
//        @Override
//        protected String[] doInBackground(String... params) {
//
//            String[] data = new String[5];
//
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            try {
//
//                Uri builtUri = Uri.parse("http://api.themoviedb.org/3/movie/"+params[0]+"?api_key=aa30ea4bd93242bad0effff696d4e82a");
//
//                URL url = new URL(builtUri.toString());
//                Log.v(LOG_TAG, "Built URI " + builtUri.toString());
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//                Log.d("@@@@@@", "Outside if");
//
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//
//
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//                String line;
//                while ((line = reader.readLine()) != null) {
//
//                    buffer.append(line + "\n");
//                    responseJsonStr = buffer.toString();
//
//                }
//
//                Log.d(LOG_TAG+"%%%%%%",responseJsonStr);
//
//                if(buffer.length() != 0) {
//
//
//                    try {
//                        String original_title = "original_title";
//                        String title = null;
//                        String overview = "overview";
//                        String plot = null;
//                        String vote_average = "vote_average";
//                        String rating = null;
//                        String release_date = "release_date";
//                        String release = null;
//                        String vote_count = "vote_count";
//                        String votes;
//                        JSONObject response = new JSONObject(responseJsonStr);
//
//                        title = response.getString(original_title);
//                        plot = response.getString(overview);
//                        rating = response.getString(vote_average);
//                        release = response.getString(release_date);
//                        votes = response.getString(vote_count);
//
//                        data[0] = title;
//                        data[1] = plot;
//                        data[2] = rating;
//                        data[3] = release;
//                        data[4] = votes;
//
//                        return data;
//
//                    } catch (JSONException e) {
//                        Log.d("json exception", "" + e);
//                    }
//                }
//            }
//            catch (IOException e) {
//                Log.e(LOG_TAG, "Error ", e);
//
//            }
//
//
//                return data;
//        }
//
//        @Override
//        protected void onPostExecute(String[] result) {
//            Log.d("onPostExecute of detail", result[0]);
//            Log.d("onPostExecute of detail", result[1]);
//            Log.d("onPostExecute of detail", result[2]);
//            Log.d("onPostExecute of detail", result[3]);
//            Log.d("onPostExecute of detail", result[4]);
//
//            //tv2.setText(result[0]);
//            tv6.setText(result[1]);
//            tv5.setText(getResources().getString((R.string.rating), result[2]));
//            tv3.setText(getResources().getString((R.string.release_date),result[3]));
//            String formattedStr = "By "+result[4]+" votes";
//            tv7.setText(getActivity().getString(R.string.vote_count, result[4]));
//            if (_getScreenOrientation() != 2)
//            {
//                tv6.setTextSize(20);
//            }
//
//        }
//
//    }

    //public void fetchTrailers(String idFromMainActivity)
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
//                String result[] = new String[10];
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

//                String temp[];
//                temp = new String[]{"Trailer 1", "Trailer 2", "Trailer 3","Trailer 4", "Trailer 5"};
//
//                Log.d("^^^^Temp array", "" + temp.length);
//                ListView trailerListView = (ListView) getActivity().findViewById(R.id.trailer_list_view);
//                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.movieslistitem, temp);
//                trailerListView.setAdapter(adapter);
//                if (_getScreenOrientation() != 2)
//                {
//                    ViewGroup.LayoutParams params = trailerListView.getLayoutParams();
//                    params.height = 1050;
//                    trailerListView.setLayoutParams(params);
//                    trailerListView.requestLayout();
//                }
//                trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(AdapterView adapterView, View view, int position, long l) {
//                        long trailer_id = adapterView.getItemIdAtPosition(position);
//                        Log.d("^^^^trailer_id", "" + trailer_id);
//                        int id = ((int) trailer_id);
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + s.get(id))));
//                    }
//                });
            if (s.size()>0)
            {
                TrailerAdapter trailerAdapter = new TrailerAdapter(getActivity(), s);
                recycler_trailer.setAdapter(trailerAdapter);
            }
        }
    }
}
