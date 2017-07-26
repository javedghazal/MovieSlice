package com.hotmail.ghazaljaved1993.movieslice.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
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
import com.hotmail.ghazaljaved1993.movieslice.data.TV;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ghazal on 6/6/2017.
 */

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    Context c;
    List<TV> tvs = new ArrayList<>();
    Resources resources;
    Activity activity;

    public TvAdapter(Context mContext, Activity mActivity, Resources mResources, List<TV> tvList)
    {
        c = mContext;
        tvs = tvList;
        activity = mActivity;
        resources = mResources;
    }

    @Override
    public TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(c);
        View view = inflater.inflate(R.layout.tv_item_layout, parent, false);
        TvAdapter.TvViewHolder vh = new TvAdapter.TvViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final TvViewHolder holder, final int position) {

        holder.txtTvTitle.setText(tvs.get(position).getTvName());
        holder.txtTvFirstAir.setText(c.getString(R.string.release_date, tvs.get(position).getFirst_air_date()));
        if(tvs.get(position).getVoteAverage() == 0.0f)
        {
            holder.txtTvVoteAverage.setText("Rating Not Available");

        }
        else
        {
            holder.txtTvVoteAverage.setText(tvs.get(position).getVoteAverage()+" ");

        }
        if (tvs.get(position).getOverview().equals(""))
        {
            holder.txtTvOverview.setText("Overview Not Available");
            holder.txtTvOverview.setTextSize(14.0f);
            holder.txtTvOverview.setTypeface(Typeface.SANS_SERIF);
        }
        else
        {
            holder.txtTvOverview.setText(tvs.get(position).getOverview());

        }
        Picasso.with(c).load("http://image.tmdb.org/t/p/w500"+tvs.get(position).getPoster_path()).into(holder.imageViewTv);

        Log.d("TV ADAPTER", tvs.get(position).getId()+" ");
        holder.cardViewTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c, DetailActivity.class);
                i.putExtra("image", tvs.get(position).getPoster_path());
                i.putExtra("id1", tvs.get(position).getId());
                i.putExtra("land", false);
                i.putExtra("title",tvs.get(position).getTvName());
                i.putExtra("tvDetail", true);
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                        holder.imageViewTv, "transitionPoster");
                c.startActivity(i, activityOptionsCompat.toBundle());
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("TV SIZE", tvs.size()+"");
        return tvs.size();
    }

    class TvViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.txtTvTitle) TextView txtTvTitle;
        @BindView(R.id.txtTvVoteAverage) TextView txtTvVoteAverage;
        @BindView(R.id.txtTvFirstAir) TextView txtTvFirstAir;
        @BindView(R.id.txtTvOverView) TextView txtTvOverview;
        @BindView(R.id.imgTvPoster) ImageView imageViewTv;
        @BindView(R.id.cardViewTv) CardView cardViewTv;
//        TextView txtTvTitle;
//        TextView txtTvVoteAverage;
//        TextView txtTvFirstAir;
//        TextView txtTvOverview;
//        ImageView imageViewTv;
//        CardView cardViewTv;

        public TvViewHolder(View itemView) {
            super(itemView);
//            txtTvTitle = (TextView) itemView.findViewById(R.id.txtTvTitle);
//            txtTvOverview = (TextView) itemView.findViewById(R.id.txtTvOverView);
//            txtTvVoteAverage = (TextView) itemView.findViewById(R.id.txtTvVoteAverage);
//            txtTvFirstAir = (TextView) itemView.findViewById(R.id.txtTvFirstAir);
//            imageViewTv = (ImageView) itemView.findViewById(R.id.imgTvPoster);
//            cardViewTv = (CardView) itemView.findViewById(R.id.cardViewTv);
            ButterKnife.bind(this, itemView);
        }
    }
}
