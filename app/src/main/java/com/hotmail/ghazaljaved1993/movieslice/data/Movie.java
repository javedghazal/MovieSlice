package com.hotmail.ghazaljaved1993.movieslice.data;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ghazal on 1/11/2017.
 */

public class Movie {
    @SerializedName("title")
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("id")
    private int id;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("poster_path")
    private String imagePoster;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("homepage")
    private String homepage;

    public Movie(String mTitle, String mOverView, int mId,int mVoteAverage, String mReleaseDate, int mVoteCount, String image )
    {
        this.title = mTitle;
        this.overview = mOverView;
        this.id = mId;
        this.voteAverage = mVoteAverage;
        this.releaseDate = mReleaseDate;
        this.voteCount = mVoteCount;
        this.imagePoster = image;
    }

    public String getImage() {
        return imagePoster;
    }
    public void setImage(String image) {
        this.imagePoster = image;
    }

    public int getVoteCount() {
        return voteCount;
    }
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getRuntime() {
        return runtime;
    }
    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getreleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }
    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getHomepage() {
        return homepage;
    }
    public void setHomepage(String mHomepage) {
        this.homepage = mHomepage;
    }
}
