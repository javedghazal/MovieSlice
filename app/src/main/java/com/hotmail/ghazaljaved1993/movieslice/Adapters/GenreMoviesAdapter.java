package com.hotmail.ghazaljaved1993.movieslice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotmail.ghazaljaved1993.movieslice.Activity.DetailActivity;
import com.hotmail.ghazaljaved1993.movieslice.R;
import com.hotmail.ghazaljaved1993.movieslice.data.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ghazal on 6/14/2017.
 */

public class GenreMoviesAdapter extends RecyclerView.Adapter<GenreMoviesAdapter.GenreMViewHolder> {

    Context context;
    List<Results> results = new ArrayList<>();

    public GenreMoviesAdapter(Context mContext, List<Results> mResults)
    {
        context = mContext;
        results = mResults;
    }

    @Override
    public GenreMViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        GenreMoviesAdapter.GenreMViewHolder vh = new GenreMoviesAdapter.GenreMViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final GenreMViewHolder holder, final int position) {

        holder.txtTitle.setText(results.get(position).getTitle());
        holder.txtVoteAverage.setText(context.getString(R.string.rating, results.get(position).getVoteAverage()+" "));
        holder.txtReleaseDate.setText(context.getString(R.string.release_date, results.get(position).getReleaseDate()));
        holder.txtOverView.setText(results.get(position).getOverView());
        Picasso.with(context).load("http://image.tmdb.org/t/p/w500"+results.get(position).getPosterPath()).into(holder.imageView);
        Log.d("Title", results.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("image", results.get(position).getPosterPath());
                i.putExtra("id1",  results.get(position).getId());
                i.putExtra("land", false);
                i.putExtra("title",results.get(position).getTitle());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class GenreMViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.txtVoteAverage) TextView txtVoteAverage;
        @BindView(R.id.txtReleaseDate) TextView txtReleaseDate;
        @BindView(R.id.txtOverView) TextView txtOverView;
        @BindView(R.id.imgPoster) ImageView imageView;
        @BindView(R.id.cardView) CardView cardView;

//        TextView txtVoteAverage;
//        TextView txtReleaseDate;
//        TextView txtOverView;
//        ImageView imageView;
//        CardView cardView;

        public GenreMViewHolder(View itemView) {
            super(itemView);
//            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
//            txtVoteAverage = (TextView) itemView.findViewById(R.id.txtVoteAverage);
//            txtReleaseDate = (TextView) itemView.findViewById(R.id.txtReleaseDate);
//            txtOverView = (TextView) itemView.findViewById(R.id.txtOverView);
//            imageView = (ImageView) itemView.findViewById(R.id.imgPoster);
//            cardView = (CardView) itemView.findViewById(R.id.cardView);
            ButterKnife.bind(this, itemView);
        }
    }
}
