package com.hotmail.ghazaljaved1993.movieslice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ghazal on 6/13/2017.
 */

public class GenreMovies {
    @SerializedName("id")
    private int id;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<Results> results;

    public GenreMovies(int mId, int mtotalPages, int mTotalResults, int mPage, List<Results> mResults)
    {
        id = mId;
        total_pages = mtotalPages;
        total_results = mTotalResults;
        page = mPage;
        results = mResults;
    }

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public List<Results> getResults() {
        return results;
    }
}
