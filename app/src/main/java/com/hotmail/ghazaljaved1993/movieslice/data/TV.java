package com.hotmail.ghazaljaved1993.movieslice.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ghazal on 6/6/2017.
 */

public class TV {
    @SerializedName("name")
    private String tvName;
    @SerializedName("id")
    private int id;
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


    public TV(String mName, int mId, int mEpisodes, int mSeasons, String mFirstAirDate, float mPopularity, String mPoster,
              float mVoteAverage, int mVoteCount, String mOverview)
    {
        tvName = mName;
        id = mId;
        number_of_episodes = mEpisodes;
        number_of_seasons = mSeasons;
        first_air_date = mFirstAirDate;
        popularity = mPopularity;
        poster_path = mPoster;
        voteAverage = mVoteAverage;
        voteCount = mVoteCount;
        overview = mOverview;
    }

    public int getId() {
        return id;
    }

    public String getOverview() {
        return overview;
    }

    public String getTvName() {
        return tvName;
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

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

}
