package com.hotmail.ghazaljaved1993.movieslice.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotmail.ghazaljaved1993.movieslice.Activity.GenreDetailActivity;
import com.hotmail.ghazaljaved1993.movieslice.R;
import com.hotmail.ghazaljaved1993.movieslice.data.Genre;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ghazal on 6/7/2017.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{

    Context context;
    List<Genre> genres = new ArrayList<>();
    Activity activity;

    public GenreAdapter(Context mContext, Activity mActivity, Resources mResources, List<Genre> mGenres)
    {
        context = mContext;
        genres = mGenres;
        activity = mActivity;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.genre_item_layout, parent, false);
        GenreAdapter.GenreViewHolder vh = new GenreAdapter.GenreViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final GenreViewHolder holder, final int position) {

        holder.txtGenreName.setText(genres.get(position).getName());
        holder.cardView_Genre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, GenreDetailActivity.class);
                i.putExtra("genreId", genres.get(position).getId());
                i.putExtra("genreName", genres.get(position).getName());

                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                        holder.txtGenreName, "transitionToolbar");
                context.startActivity(i, activityOptionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    class GenreViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.cardView_Genre) CardView cardView_Genre;
        @BindView(R.id.txtGenreName) TextView txtGenreName;

        public GenreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
