package com.hotmail.ghazaljaved1993.movieslice.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ghazal on 1/12/2017.
 */

public class MovieTrailer {
    @SerializedName("key")
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String mKey) {
        this.key = mKey;
    }
}
