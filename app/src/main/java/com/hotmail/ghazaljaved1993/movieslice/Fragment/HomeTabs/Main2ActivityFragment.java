package com.hotmail.ghazaljaved1993.movieslice.Fragment.HomeTabs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.NowFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.PopularFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.TopFragment;
import com.hotmail.ghazaljaved1993.movieslice.Fragment.MovieTabs.UpFragment;
import com.hotmail.ghazaljaved1993.movieslice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class Main2ActivityFragment extends Fragment {

    boolean tab = false;
    String image = "";
    String id = "";
    boolean dataFetched = false;
    private ArrayAdapter<String> mForecastAdapter;
    int densityDpi = 0;
    private ListView lv;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public Main2ActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        densityDpi = dm.densityDpi;
        if(!isNetworkAvailable())
        {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) getView().findViewById(R.id.viewpager2);
        setViewPager(mViewPager);

        mTabLayout = (TabLayout) getView().findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        Main2ActivityFragment.ViewAdapter adapter = new Main2ActivityFragment.ViewAdapter(getChildFragmentManager());
        adapter.addFragment(new PopularFragment(), "Popular");
        adapter.addFragment(new TopFragment(), "Top Rated");
        adapter.addFragment(new UpFragment(), "Upcoming");
        adapter.addFragment(new NowFragment(), "Now Playing");
        viewPager.setAdapter(adapter);
//        viewPager.setOffscreenPageLimit(3);
    }

    class ViewAdapter extends FragmentPagerAdapter {

        private final List<String> mTitles = new ArrayList<>();
        private final List<Fragment> mFragments = new ArrayList<>();

        public ViewAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        /*This method add Fragments to the fragmentList.*/
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!= null) {

//            Log.d("^^^^^state is saved", "inside if");
//            Bitmap b1 = savedInstanceState.getParcelable("Key1");
//            Bitmap b2 = savedInstanceState.getParcelable("Key2");
//            Bitmap b3 = savedInstanceState.getParcelable("Key3");
//            Bitmap b4 = savedInstanceState.getParcelable("Key4");
//            Bitmap b5 = savedInstanceState.getParcelable("Key5");
//            Bitmap b6 = savedInstanceState.getParcelable("Key6");
//            Bitmap b7 = savedInstanceState.getParcelable("Key7");
//            Bitmap b8 = savedInstanceState.getParcelable("Key8");
//            Bitmap b9 = savedInstanceState.getParcelable("Key9");
//
//            ImageView img1 = (ImageView) getActivity().findViewById(R.id.imageView0);
//            ImageView img2 = (ImageView) getActivity().findViewById(R.id.imageView1);
//            ImageView img3 = (ImageView) getActivity().findViewById(R.id.imageView2);
//            ImageView img4 = (ImageView) getActivity().findViewById(R.id.imageView3);
//            ImageView img5 = (ImageView) getActivity().findViewById(R.id.imageView4);
//            ImageView img6 = (ImageView) getActivity().findViewById(R.id.imageView5);
//            ImageView img7 = (ImageView) getActivity().findViewById(R.id.imageView6);
//            ImageView img8 = (ImageView) getActivity().findViewById(R.id.imageView7);
//            ImageView img9 = (ImageView) getActivity().findViewById(R.id.imageView8);
//
//            img1.setImageBitmap(b1);
//            img2.setImageBitmap(b2);
//            img3.setImageBitmap(b3);
//            img4.setImageBitmap(b4);
//            img5.setImageBitmap(b5);
//            img6.setImageBitmap(b6);
//            img7.setImageBitmap(b7);
//            img8.setImageBitmap(b8);
//            img9.setImageBitmap(b9);
//
//            Log.d("^^^^on Activity created", "images restored");
        }

    }
    public boolean isTablet(Context ctx){
        return (ctx.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public int _getScreenOrientation(){
        return getResources().getConfiguration().orientation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
//        mForecastAdapter =
//                new ArrayAdapter<String>(
//                        getActivity(), // The current context (this activity)
//                        R.layout.list_item_holder, // The name of the layout ID.
//                        R.id.imageView0, // The ID of the textview to populate.
//                        new ArrayList<String>());
//
//
        View rootView;
//        if(!isTablet(getContext()))
//        {
            rootView = inflater.inflate(R.layout.fragment_main2, container, false);
//            lv = (ListView) rootView.findViewById(R.id.list_view);
//            loadDataOnActivityVisible();
//            final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
//            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
////                    if(isNetworkAvailable())
////                    {
////                        startTask();
////                        swipeRefreshLayout.setRefreshing(false);
////                    }
////                    else{
////                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
////                        alertDialog.setMessage("No Internet Connection. Go to Settings and Turn on Internet?");
////                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
////                        {
////                            public void onClick(DialogInterface dialog, int which){
////                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
////                            }
////
////                        });
////                        alertDialog.create().show();
////                    }
//                    loadDataOnActivityVisible();
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            });
//
//        }
//        else
//        {
//            rootView = inflater.inflate(R.layout.tablayout, container, false);
//            tab = true;
//        }
        return rootView;
    }

//    public void loadDataOnActivityVisible()
//    {
//        if(isNetworkAvailable())
//        {
//            startTask();
//        }
//        else{
//            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//            alertDialog.setMessage("No Internet Connection. Go to Settings and Turn on Internet?");
//            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
//            {
//                public void onClick(DialogInterface dialog, int which){
//                    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
//                }
//
//            });
//            alertDialog.create().show();
//        }
//    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mForecastAdapter != null){
            Log.d("^^^^^^saveInstance", "onSaveInstance");
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main2_fragment, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onStart() {
        super.onStart();
//        loadDataOnActivityVisible();
    }

//    public void startTask() {
//
//        if(dataFetched == false)
//        {
////            fetchdata fetch = new fetchdata();
////
////            fetch.execute(category);
////            Log.d("^^^^^^task executed", "data ");
////
////            HorizontalScrollView sv = (HorizontalScrollView) getActivity().findViewById(R.id.horizontl_scrollView);
////            sv.setOnTouchListener(new View.OnTouchListener() {
////                @Override
////                public boolean onTouch(View v, MotionEvent event) {
////                    return true;
////                }
////            });
//
////            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
////            String category = prefs.getString(getString(R.string.category_list), getString(R.string.category_default));
//            //String uri = "http://api.themoviedb.org/3/movie/"+category+"?api_key=aa30ea4bd93242bad0effff696d4e82a";
//            String api_key = "aa30ea4bd93242bad0effff696d4e82a";
//            ApiInterface apiService =
//                    ApiClient.getClient().create(ApiInterface.class);
//            Call<MoviesResponse> call = apiService.getTopRatedMovies("popular",api_key);
//            call.enqueue(new Callback<MoviesResponse>() {
//                @Override
//                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
//                    List<Movie> movies = response.body().getResults();
//                    Log.d("MAIN ACTIVITY", "Number of movies received: " + movies.size());
////                    MoviesAdapter myAdapter = new MoviesAdapter(movies, getContext(), getResources());
////                    lv.setAdapter(myAdapter);
//
//                }
//
//                @Override
//                public void onFailure(Call<MoviesResponse> call, Throwable t) {
//                    Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }


//    public class fetchdata extends AsyncTask<String, Void, String[]> {
//
//        private ArrayAdapter<String> mForecastAdapter;
//        private Context mContext = getContext();
//
////        public fetchdata(Context context, ArrayAdapter<String> forecastAdapter) {
////            mContext = context;
////            mForecastAdapter = forecastAdapter;
////        }
//
//
//        private final String LOG_TAG = fetchdata.class.getSimpleName();
////
////        ImageView img1 = (ImageView) getActivity().findViewById(R.id.imageView0);
////        ImageView img2 = (ImageView) getActivity().findViewById(R.id.imageView1);
////        ImageView img3 = (ImageView) getActivity().findViewById(R.id.imageView2);
////        ImageView img4 = (ImageView) getActivity().findViewById(R.id.imageView3);
////        ImageView img5 = (ImageView) getActivity().findViewById(R.id.imageView4);
////        ImageView img6 = (ImageView) getActivity().findViewById(R.id.imageView5);
////        ImageView img7 = (ImageView) getActivity().findViewById(R.id.imageView6);
////        ImageView img8 = (ImageView) getActivity().findViewById(R.id.imageView7);
////        ImageView img9 = (ImageView) getActivity().findViewById(R.id.imageView8);
////        ImageView img10 = (ImageView) getActivity().findViewById(R.id.imageView9);
////        ImageView img11 = (ImageView) getActivity().findViewById(R.id.imageView10);
////        ImageView img12 = (ImageView) getActivity().findViewById(R.id.imageView11);
////        ImageView img13 = (ImageView) getActivity().findViewById(R.id.imageView12);
////        ImageView img14 = (ImageView) getActivity().findViewById(R.id.imageView13);
////        ImageView img15 = (ImageView) getActivity().findViewById(R.id.imageView14);
////        ImageView img16 = (ImageView) getActivity().findViewById(R.id.imageView15);
////        ImageView img17 = (ImageView) getActivity().findViewById(R.id.imageView16);
////        ImageView img18 = (ImageView) getActivity().findViewById(R.id.imageView17);
////        ImageView img19 = (ImageView) getActivity().findViewById(R.id.imageView18);
////        ImageView img20 = (ImageView) getActivity().findViewById(R.id.imageView19);
//
//        Bitmap bitmap;
//
//        @Override
//        protected String[] doInBackground(String... params) {
//
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            String[] myArray = new String[40];
//
//            String forecastJsonStr = null;
//
//            try {
//
//                Uri builtUri = Uri.parse("http://api.themoviedb.org/3/movie/"+params[0]+"?api_key=aa30ea4bd93242bad0effff696d4e82a");
//
//                URL url = new URL(builtUri.toString());
//                Log.v(LOG_TAG, "Built URI " + builtUri.toString());
//
//
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//
//                    buffer.append(line + "\n");
//
//                    forecastJsonStr = buffer.toString();
//
//                }
//
//                if (buffer.length() != 0) {
//                    int j = 0;
//                    int count = 1;
//
//                    try{
//                        final String id2 = "id";
//
//                        final String result = "results";
//                        final String poster_path = "poster_path";
//
//
//                        JSONObject forecastJson = new JSONObject(forecastJsonStr);
//                        JSONArray resultArray = forecastJson.getJSONArray(result);
//                        int numberOfResponse = resultArray.length();
//
//
//                        if(resultArray != null) {
//
//                            for (int i = 0; i < numberOfResponse; i = i + 2) {
//
//
//                                JSONObject imagePath = resultArray.getJSONObject(i);
//                                image = imagePath.getString(poster_path);
//
//                                id = imagePath.getString(id2);
//                                Log.d("%%%%%%%image is ", image + i);
//                                Log.d("%%%%%%%id is ", id + i);
//                                myArray[i] = image;
//                                Log.d("myarray[i]", myArray[i]);
//                                myArray[i + 1] = id;
//                                Log.d("myarray[i+1]", myArray[i + 1]);
//
//                            }
//                            dataFetched = true;
//                        }
//                    }
//                    catch (JSONException e) { Log.d("json exception",""+ e); }
//                }
//
//
//            } catch (IOException e) {
//                Log.e("ForecastFragment", "Error ", e);
//
//            }
//
//
//            return myArray;
//        }
//
//
//        @Override
//        protected void onPostExecute(final String[] result)
//        {
//
//            img1.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    fillView(result[0], result[1]);
//
//                }
//            });
//            img2.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    fillView(result[2], result[3]);
//                }
//            });
//            img3.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[4], result[5]);
//
//                }
//            });
//            img4.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[6], result[7]);
//                }
//            });
//            img5.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[8], result[9]);
//                }
//            });
//            img6.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[10], result[11]);
//                }
//            });
//            img7.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[12], result[13]);
//                }
//            });
//            img8.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[14], result[15]);
//                }
//            });
//            img9.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[16], result[17]);
//                }
//            });
//
//            img10.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    fillView(result[18], result[19]);
//                }
//            });
//
//
//            if (result != null && mForecastAdapter != null) {
//                mForecastAdapter.clear();
//
//                for(int i = 0; i<result.length/2; i++) {
//                    mForecastAdapter.add(result[i + 1]);
//                    //Log.d("&&&&&&&&on post execute", "" + result[i].toString());
//                    Log.d("&&&&& mForecastAdapter", "" + mForecastAdapter.getItem(i));
//                }
//
//                if(!tab && densityDpi > 480)
//                {
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[0]).into(img1);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[2]).into(img2);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[4]).into(img3);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[6]).into(img4);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[8]).into(img5);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[10]).into(img6);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[12]).into(img7);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[14]).into(img8);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[16]).into(img9);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w780" + result[18]).into(img10);
//
//                    long tempInserted = insertInDatabase(result);
//                    Log.d("^^^^^^^TEMP", "" + tempInserted);
//                }
//                else if (densityDpi == 480)
//                {
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[0]).into(img1);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[2]).into(img2);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[4]).into(img3);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[6]).into(img4);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[8]).into(img5);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[10]).into(img6);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[12]).into(img7);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[14]).into(img8);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[16]).into(img9);
//                    Picasso.with(getActivity().getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+result[18]).into(img10);
//                    long tempInserted = insertInDatabase(result);
//                    Log.d("^^^^^^^TEMP", "" + tempInserted);
//                }
//
//
//            }
//
//        }
//
//        public void fillView(String image, String id){
//            Log.d("&&&&&&inside fillview", image+" "+id);
//            int x = _getScreenOrientation();
//            Log.d("$$$$$$$$","$$$$$$"+x);
//
//            if(x!=2) {
//                Intent i = new Intent(getActivity(), DetailActivity.class);
//
//                i.putExtra("image", image);
//                i.putExtra("id1", id);
//                i.putExtra("land", false);
//                startActivity(i);
//            }
//            else {
//
//                DetailActivityFragment df = new DetailActivityFragment();
//                Bundle args = new Bundle();
//                args.putString("image", image);
//                args.putString("id1", id);
//                args.putBoolean("land", true);
//                df.setArguments(args);
//
////Inflate the fragment
//                getFragmentManager().beginTransaction().replace(R.id.weather_detail_container, df).commit();
//            }
//        }
//
//        public long insertInDatabase(String[] myArray){
//            long locationId = 0;
//            Cursor locationCursor = mContext.getContentResolver().query(
//                    MoviesContract.movies.CONTENT_URI,
//                    new String[]{MoviesContract.movies._ID},
//                    null,
//                    null,
//                    null);
//
//            if (locationCursor.moveToFirst()) {
//                int IdIndex = locationCursor.getColumnIndex(MoviesContract.movies._ID);
//                locationId = locationCursor.getLong(IdIndex);
//            } else {
//                ContentValues locationValues = new ContentValues();
//                String[] imageArray = new String[9];
//                Log.d("~~~~~~~inside insert", "image inserted");
//                for (int i=1; i<myArray.length/2; i=i+2) {
//                    locationValues.put(MoviesContract.movies.COLUMN_MOVIE_ID, myArray[i]);
//
//                    // Finally, insert location data into the database.
//                    Uri insertedUri = mContext.getContentResolver().insert(
//                            MoviesContract.movies.CONTENT_URI,
//                            locationValues
//                    );
//                    locationId = ContentUris.parseId(insertedUri);
//                }
//
//            }
//
//            locationCursor.close();
//
//            return locationId;
//        }
//
//
//    }


}
