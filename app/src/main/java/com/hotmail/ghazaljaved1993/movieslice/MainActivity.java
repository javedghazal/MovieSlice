package com.hotmail.ghazaljaved1993.movieslice;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static class fragmentForTask extends Fragment{

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onStart() {
            super.onStart();
            startTask();

        }
        public void startTask() {

            fetchdata fetch = new fetchdata();
            fetch.execute();
        }

        public class fetchdata extends AsyncTask<Void, Void, Void[]> {

            private Context mContext;
            private View rootView;
            private final String LOG_TAG = fetchdata.class.getSimpleName();

            Bitmap bitmap;

            public fetchdata(){

            }
            public fetchdata(Context c, View rootView){

                this.mContext = c;
                this.rootView = rootView;

            }

            @Override
            protected Void[] doInBackground(Void... params) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;


                String forecastJsonStr = null;

                try {

                    Uri builtUri = Uri.parse("http://api.themoviedb.org/3/movie/top_rated?api_key=aa30ea4bd93242bad0effff696d4e82a");

                    URL url = new URL(builtUri.toString());
                    Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    Log.d("##########", "Outside if");

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {

                        buffer.append(line + "\n");
                        Log.d("@@@@@@@@@input is:", line + buffer.toString());
                        forecastJsonStr = buffer.toString();

                    }

                    if (buffer.length() != 0) {

                        try{
                            getImageUrl(forecastJsonStr);

                        }
                        catch (JSONException e) { Log.d("json exception",""+ e); }
                    }

                } catch (IOException e) {
                    Log.e("ForecastFragment", "Error ", e);

                }


                return new Void[0];
            }

            private void getImageUrl(String forecastJsonStr)
                    throws JSONException{
                final String page = "page";
                final String result="results";
                final String poster_path= "poster_path";
                JSONObject forecastJson = new JSONObject(forecastJsonStr);
                JSONArray resultArray = forecastJson.getJSONArray(result);

                JSONObject imagePath = resultArray.getJSONObject(0);
                String image = imagePath.getString(poster_path);

                ImageView img = (ImageView) rootView.findViewById(R.id.imageView2);
                Picasso.with(mContext).load(" http://image.tmdb.org/t/p"+image).into(img);




            }

            protected void onPostExecute(Bitmap image) {

            }
        }
    }


}
