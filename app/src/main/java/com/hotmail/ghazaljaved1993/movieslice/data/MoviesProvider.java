package com.hotmail.ghazaljaved1993.movieslice.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by Ghazal on 7/21/2016.
 */
public class MoviesProvider extends ContentProvider {

    static final int ONLY_ADDING_ID = 100;
    static final int ONLY_RETRIEVING_ID = 101;

    private static final SQLiteQueryBuilder sWeatherByLocationSettingQueryBuilder = null;
    private MoviesDBHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

//    private void getId(Uri uri, String[] projection, String sortOrder) {
//
//
//    }

    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // 2) Use the addURI function to match each of the types.  Use the constants from
        // WeatherContract to help define the types to the UriMatcher.
        //sURIMatcher.addURI("com.example.ghazal.popularmoviesproject", "movies", ONLY_ADDING_ID);
        sURIMatcher.addURI("com.example.ghazal.popularmoviesproject", "movies", ONLY_RETRIEVING_ID);

        // 3) Return the new matcher!
        return sURIMatcher;
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            // "weather/*/*"
            case ONLY_RETRIEVING_ID:
            {
                mOpenHelper = new MoviesDBHelper(getContext());
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MoviesContract.movies.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
//            // "weather"
//            case ONLY_ADDING_ID: {
//                retCursor = mOpenHelper.getReadableDatabase().query(
//                        MoviesContract.movies.TABLE_NAME,
//                        projection,
//                        selection,
//                        selectionArgs,
//                        null,
//                        null,
//                        sortOrder);
//                break;
//            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case ONLY_RETRIEVING_ID:
                return MoviesContract.movies.CONTENT_TYPE;
            case ONLY_ADDING_ID:
                return MoviesContract.movies.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        mOpenHelper = new MoviesDBHelper(getContext());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case ONLY_RETRIEVING_ID: {

                long _id = db.insert(MoviesContract.movies.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.movies.buildUriToInsertId(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
//            case LOCATION: {
//                long _id = db.insert(WeatherContract.LocationEntry.TABLE_NAME, null, values);
//                if ( _id > 0 )
//                    returnUri = WeatherContract.LocationEntry.buildLocationUri(_id);
//                else
//                    throw new android.database.SQLException("Failed to insert row into " + uri);
//                break;
//
//            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        mOpenHelper = new MoviesDBHelper(getContext());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int retRows = 0;
        // Student: Use the uriMatcher to match the WEATHER and LOCATION URI's we are going to
        // handle.  If it doesn't match these, throw an UnsupportedOperationException.
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        if(selection == null) selection="1";

        switch(match) {
            case ONLY_RETRIEVING_ID: {
                retRows = db.delete(MoviesContract.movies.TABLE_NAME, selection, selectionArgs );
                break;
            }
//            case LOCATION: {
//                retRows = db.delete(WeatherContract.LocationEntry.TABLE_NAME, selection, selectionArgs );
//                break;
//            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Student: A null value deletes all rows.  In my implementation of this, I only notified
        // the uri listeners (using the content resolver) if the rowsDeleted != 0 or the selection
        // is null.

        // Oh, and you should notify the listeners here.
        if(retRows!= 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Student: return the actual rows deleted

        return retRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        mOpenHelper = new MoviesDBHelper(getContext());
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int retRows = 0;

        final int match = sUriMatcher.match(uri);

        switch(match) {
            case ONLY_RETRIEVING_ID: {
                retRows = db.update(MoviesContract.movies.TABLE_NAME, values, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(retRows!= 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return retRows;
    }
}
