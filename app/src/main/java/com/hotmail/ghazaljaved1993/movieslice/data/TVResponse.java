package com.hotmail.ghazaljaved1993.movieslice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ghazal on 6/6/2017.
 */

public class TVResponse {
    @SerializedName("results")
    private List<TV> results;
    @SerializedName("name")
    private String tvName;
    @SerializedName("number_of_episodes")
    private int number_of_episodes;
    @SerializedName("number_of_seasons")
    private int number_of_seasons;
    @SerializedName("first_air_date")
    private String first_air_date;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String poster_path;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("overview")
    private String overview;

    public String getOverview() {
        return overview;
    }

    public String getTvName() {
        return tvName;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public float getPopularity() {
        return popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public List<TV> getResults() {
        return results;
    }

}
