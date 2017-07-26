package com.hotmail.ghazaljaved1993.movieslice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ghazal on 1/11/2017.
 */

public class MoviesResponse {
    @SerializedName("results")
    private List<Movie> results;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("overview")
    private String overView;
    @SerializedName("runtime")
    private Integer runTime;
    @SerializedName("homepage")
    private String homepage;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public int getRuntime() {
        return runTime;
    }

    public void setRuntime(int mmRuntime) {
        this.runTime = mmRuntime;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float mVoteAverage) {
        this.voteAverage = mVoteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int mVoteCount) {
        this.voteCount = mVoteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setVoteCount(String mReleaseDate) {
        this.releaseDate = mReleaseDate;
    }

    public String getOverview() {
        return overView;
    }

    public void setOverView(String mOverview) {
        this.overView = mOverview;
    }

    public String getHomepage() {
        return homepage;
    }
    public void setHomepage(String mHomepage) {
        this.homepage = mHomepage;
    }
}
