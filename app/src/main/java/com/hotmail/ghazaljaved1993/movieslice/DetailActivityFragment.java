package com.hotmail.ghazaljaved1993.movieslice;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    String idFromMainActivity = "";
    String image = "";
    boolean land = true;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        if(_getScreenOrientation()!=2) {
            Intent intent = getActivity().getIntent();
            idFromMainActivity = intent.getStringExtra("id1");
            image = intent.getStringExtra("image");
        }
        else
        {
            idFromMainActivity = getArguments().getString("id1");
            image = getArguments().getString("image");
            land = false;

        }


        ImageView image2 = (ImageView) rootView.findViewById(R.id.movie_poster);
        Picasso.with(getActivity().getApplication()).load("http://image.tmdb.org/t/p/w780"+image).into(image2);

        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("idFromMainActivity", idFromMainActivity);
        outState.putString("image", image);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null)
        {
            idFromMainActivity = savedInstanceState.getString("idFromMainActivity");
            image = savedInstanceState.getString("image");
        }
    }

    public int _getScreenOrientation(){
        return getResources().getConfiguration().orientation;
    }

    @Override
    public void onStart() {
        super.onStart();
        startTask();

    }
    public void startTask() {

        fetchdatafromid fetch = new fetchdatafromid();
        fetch.execute(idFromMainActivity);
        fetchTrailersFromId fetchTrailer = new fetchTrailersFromId();
        fetchTrailer.execute(idFromMainActivity);

    }
    public class fetchdatafromid extends AsyncTask<String, Void, String[]> {
        TextView tv2 = (TextView) getActivity().findViewById(R.id.detail_title);
        TextView tv6 = (TextView) getActivity().findViewById(R.id.detail_overview);
        TextView tv5 = (TextView) getActivity().findViewById(R.id.detail_vote_average);
        TextView tv3 = (TextView) getActivity().findViewById(R.id.detail_release_date);
        TextView tv7 = (TextView) getActivity().findViewById(R.id.detail_vote_count);

        private final String LOG_TAG = fetchdatafromid.class.getSimpleName();
        String responseJsonStr = null;

        @Override
        protected String[] doInBackground(String... params) {

            String[] data = new String[5];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {

                Uri builtUri = Uri.parse("http://api.themoviedb.org/3/movie/"+params[0]+"?api_key=aa30ea4bd93242bad0effff696d4e82a");

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

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

                Log.d(LOG_TAG+"%%%%%%",responseJsonStr);

                if(buffer.length() != 0) {


                    try {
                        String original_title = "original_title";
                        String title = null;
                        String overview = "overview";
                        String plot = null;
                        String vote_average = "vote_average";
                        String rating = null;
                        String release_date = "release_date";
                        String release = null;
                        String vote_count = "vote_count";
                        String votes;
                        JSONObject response = new JSONObject(responseJsonStr);

                        title = response.getString(original_title);
                        plot = response.getString(overview);
                        rating = response.getString(vote_average);
                        release = response.getString(release_date);
                        votes = response.getString(vote_count);

                        data[0] = title;
                        data[1] = plot;
                        data[2] = rating;
                        data[3] = release;
                        data[4] = votes;

                        return data;

                    } catch (JSONException e) {
                        Log.d("json exception", "" + e);
                    }
                }
            }
            catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);

            }


                return data;
        }

        @Override
        protected void onPostExecute(String[] result) {
            Log.d("onPostExecute of detail", result[0]);
            Log.d("onPostExecute of detail", result[1]);
            Log.d("onPostExecute of detail", result[2]);
            Log.d("onPostExecute of detail", result[3]);
            Log.d("onPostExecute of detail", result[4]);

            tv2.setText(result[0]);
            tv6.setText(result[1]);
            tv5.setText(result[2]);
            tv3.setText(result[3]);
            String formattedStr = "By "+result[4]+" votes";
            tv7.setText(getActivity().getString(R.string.vote_count, result[4]));
            if (_getScreenOrientation() != 2)
            {
                tv6.setTextSize(20);
            }

        }

    }

    public class fetchTrailersFromId extends AsyncTask<String, Void, String[]>
    {

        @Override
        protected String[] doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String responseJsonStr = null;
            try {

                Uri builtUri = Uri.parse("http://api.themoviedb.org/3/movie/"+params[0]+"/videos?api_key=aa30ea4bd93242bad0effff696d4e82a");

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
                String result[] = new String[10];
                if(buffer.length() != 0) {


                    try {

                        String key = "key";
                        String value = null;

                        JSONObject trailerJson = new JSONObject(responseJsonStr);
                        JSONArray resultArray = trailerJson.getJSONArray("results");

                        for(int i = 0; i < resultArray.length(); i++)
                        {
                            JSONObject arrayItem = resultArray.getJSONObject(i);
                            value = arrayItem.getString(key);
                            result[i] = value;
                        }

                        Log.d("^^^^^^value", value);
                        return result;

                    } catch (JSONException e) {
                        Log.d("json exception", "" + e);
                    }
                }
            }
            catch (IOException e) {
                Log.e("^^^^^^", "Error ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String[] s) {

                String temp[];
                temp = new String[]{"Trailer 1", "Trailer 2", "Trailer 3", "Trailer 4", "Trailer 5", "Trailer 6"};

                Log.d("^^^^Temp array", "" + temp.length);
                ListView trailerListView = (ListView) getActivity().findViewById(R.id.trailer_list_view);
                ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.movieslistitem, temp);
                trailerListView.setAdapter(adapter);
                if (_getScreenOrientation() != 2)
                {
                    ViewGroup.LayoutParams params = trailerListView.getLayoutParams();
                    params.height = 720;
                    trailerListView.setLayoutParams(params);
                    trailerListView.requestLayout();
                }
                trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                        long trailer_id = adapterView.getItemIdAtPosition(position);
                        Log.d("^^^^trailer_id", "" + trailer_id);
                        int id = ((int) trailer_id);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + s[id])));
                    }
                });

        }


    }
}
