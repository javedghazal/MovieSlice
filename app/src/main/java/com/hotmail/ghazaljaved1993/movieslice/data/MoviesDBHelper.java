package com.hotmail.ghazaljaved1993.movieslice.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ghazal on 7/21/2016.
 */
public class MoviesDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4 ;

    static final String DATABASE_NAME = "movies.db";

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_IDS_TABLE = "CREATE TABLE " + MoviesContract.movies.TABLE_NAME + " (" +
                // Why AutoIncrement here, and not above?
                // Unique keys will be auto-generated in either case.  But for weather
                // forecasting, it's reasonable to assume the user will want information
                // for a certain date and all dates *following*, so the forecast data
                // should be sorted accordingly.
                MoviesContract.movies._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                MoviesContract.movies.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                MoviesContract.movies.COLUMN_MOVIE_IMAGE_NAME + " TEXT, "+
                MoviesContract.movies.COLUMN_MOVIE_IMAGE_DATA + " BLOB);";

        db.execSQL(SQL_CREATE_IDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.movies.TABLE_NAME);
        onCreate(db);
    }
}
