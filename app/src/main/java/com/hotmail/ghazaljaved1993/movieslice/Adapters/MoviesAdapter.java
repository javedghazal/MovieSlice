package com.hotmail.ghazaljaved1993.movieslice.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.ActivityOptionsCompat;
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
import com.hotmail.ghazaljaved1993.movieslice.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ghazal on 1/11/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.PopViewHolder> {
    Context c;
    List<Movie> movies = new ArrayList<>();
    LayoutInflater inflater;
    Resources resources;
    Activity activity;

    public MoviesAdapter(final Context context, Activity mActivity, final Resources res, List<Movie> moviesList)
    {
        c = context;
        movies = moviesList;
        activity = mActivity;
        resources = res;
        for(int i = 0; i<movies.size(); i++)
        {
            Log.d("MAIN ACTIVITY", "" + movies.get(i).getTitle());
        }
    }


    @Override
    public PopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        MoviesAdapter.PopViewHolder vh = new MoviesAdapter.PopViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PopViewHolder holder, final int position) {
        holder.txtTitle.setText(movies.get(position).getTitle());
        holder.txtVoteAverage.setText(resources.getString(R.string.rating, movies.get(position).getVoteAverage()+" "));
        holder.txtOverView.setText(movies.get(position).getOverview());
        holder.txtReleaseDate.setText(resources.getString(R.string.release_date, movies.get(position).getreleaseDate()));
        Picasso.with(c).load("http://image.tmdb.org/t/p/w500"+movies.get(position).getImage()).into(holder.imageView);
        Log.d("Title", movies.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, DetailActivity.class);
                i.putExtra("image", movies.get(position).getImage());
                i.putExtra("id1",  movies.get(position).getId());
                i.putExtra("land", false);
                i.putExtra("title",movies.get(position).getTitle());
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                        holder.imageView, "transitionPoster");
                c.startActivity(i, activityOptionsCompat.toBundle());
//                c.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.d("MOVIES SIZE", movies.size()+" ");
        return movies.size();
    }

    class PopViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.txtTitle) TextView txtTitle;
        @BindView(R.id.txtVoteAverage) TextView txtVoteAverage;
        @BindView(R.id.txtReleaseDate) TextView txtReleaseDate;
        @BindView(R.id.txtOverView) TextView txtOverView;
        @BindView(R.id.imgPoster) ImageView imageView;
        @BindView(R.id.cardView) CardView cardView;
////        TextView txtTitle;
//        TextView txtVoteAverage;
//        TextView txtReleaseDate;
//        TextView txtOverView;
//        ImageView imageView;
//        CardView cardView;

        public PopViewHolder(View itemView) {
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
