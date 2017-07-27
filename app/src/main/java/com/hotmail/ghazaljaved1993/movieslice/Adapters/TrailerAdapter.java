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
