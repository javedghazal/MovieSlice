package com.hotmail.ghazaljaved1993.movieslice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ghazal on 6/7/2017.
 */

public class GenreResponse {
    public List<Genre> genres;

    public GenreResponse(List<Genre> mResult)
    {
        genres = mResult;
    }

    public List<Genre> getResult() {
        return genres;
    }
}
