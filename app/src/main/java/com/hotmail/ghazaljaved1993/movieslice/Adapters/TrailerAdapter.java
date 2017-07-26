package com.hotmail.ghazaljaved1993.movieslice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotmail.ghazaljaved1993.movieslice.R;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.List;

/**
 * Created by Ghazal on 6/5/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    Context context;
    List<String> result;
    public TrailerAdapter(Context mContext, List<String> mResults)
    {
        context = mContext;
        result = mResults;
        final String[] responseJsonStr = {null};
        final HttpURLConnection[] urlConnection = {null};
        final BufferedReader[] reader = {null};
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    Uri builtUri = Uri.parse("http://api.themoviedb.org/3/movie/"+mId+"/videos?api_key=aa30ea4bd93242bad0effff696d4e82a");
//
//                    URL url = new URL(builtUri.toString());
//                    Log.v("^^^^^", "Built TRAILER " + builtUri.toString());
//
//                    urlConnection[0] = (HttpURLConnection) url.openConnection();
//                    urlConnection[0].setRequestMethod("GET");
//                    urlConnection[0].connect();
//                    Log.d("@@@@@@", "Outside if");
//
//                    InputStream inputStream = urlConnection[0].getInputStream();
//                    StringBuffer buffer = new StringBuffer();
//
//
//                    reader[0] = new BufferedReader(new InputStreamReader(inputStream));
//                    String line;
//                    while ((line = reader[0].readLine()) != null) {
//
//                        buffer.append(line + "\n");
//                        responseJsonStr[0] = buffer.toString();
//
//                    }
//                    Log.d("%%%%%%", responseJsonStr[0]);
////                String result[] = new String[10];
//                    result = new ArrayList<>();
//                    if(buffer.length() != 0) {
//                        try {
//
//                            String key = "key";
//                            String value = null;
//
//                            JSONObject trailerJson = new JSONObject(responseJsonStr[0]);
//                            JSONArray resultArray = trailerJson.getJSONArray("results");
//
//                            for(int i = 0; i < resultArray.length(); i++)
//                            {
//                                JSONObject arrayItem = resultArray.getJSONObject(i);
//                                value = arrayItem.getString(key);
//                                result.add(value);
//                                Log.d("^^^^^^value", value);
//                            }
////                    return result;
//
//                        } catch (JSONException e) {
//                            Log.d("json exception", "" + e);
//                        }
//                    }
//                }
//                catch (IOException e) {
//                    Log.e("^^^^^^", "Error ", e);
//                }
//            }
//        });
//        t.start();

    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movieslistitem, parent, false);
        TrailerAdapter.TrailerViewHolder vh = new TrailerAdapter.TrailerViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {
        holder.movies_list_item_textview.setText("Trailer "+(position+1));
        holder.cardView_Movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("^^^^trailer_id", "" + position);
                int id = position;
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + result.get(id))));

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("results size", result.size()+" ");
        return result.size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder
    {

        TextView movies_list_item_textview;
        CardView cardView_Movie;
        public TrailerViewHolder(View itemView) {
            super(itemView);
            movies_list_item_textview = (TextView) itemView.findViewById(R.id.movies_list_item_textview);
            cardView_Movie = (CardView) itemView.findViewById(R.id.cardview_Movie);
        }
    }
}
