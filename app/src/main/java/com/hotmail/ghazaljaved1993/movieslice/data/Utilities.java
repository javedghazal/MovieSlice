package com.hotmail.ghazaljaved1993.movieslice.data;

import android.content.ContentValues;

/**
 * Created by Ghazal on 7/21/2016.
 */
public class Utilities {
   public static ContentValues createNorthPoleLocationValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.movies.COLUMN_MOVIE_ID, 1);

        return testValues;
    }
}
