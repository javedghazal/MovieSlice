package com.hotmail.ghazaljaved1993.movieslice.data;

import android.content.res.Resources;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ghazal on 6/13/2017.
 */

public class Results {

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overView;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    private float voteAverage;

    public Results(String mPosterPath, String mOverView, String mReleaseDate, int mId, String mTitle, int mVoteCount, float mVoteAverage)
    {
        posterPath = mPosterPath;
        overView = mOverView;
        releaseDate = mReleaseDate;
        id = mId;
        title= mTitle;
        voteCount = mVoteCount;
        voteAverage = mVoteAverage;
    }

    public int getId() {
        return id;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOverView() {
        return overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public int getVoteCount() {
        return voteCount;
    }
}
