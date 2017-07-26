package com.hotmail.ghazaljaved1993.movieslice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ghazal on 6/7/2017.
 */

public class Genre {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;


    public Genre(int mId, String mName)
    {
        id = mId;
        name = mName;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
