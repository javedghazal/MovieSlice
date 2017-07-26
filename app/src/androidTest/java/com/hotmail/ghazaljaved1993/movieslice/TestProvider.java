package com.hotmail.ghazaljaved1993.movieslice;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;
import android.util.Log;

import  com.hotmail.ghazaljaved1993.movieslice.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

/**
 * Created by Ghazal on 7/21/2016.
 */
public class TestProvider extends AndroidTestCase {

//    public void testBasicLocationQueries() {
//        // insert our test records into the database
//        MoviesDBHelper dbHelper = new MoviesDBHelper(mContext);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // Create a new map of values, where column names are the keys
//        ContentValues testValues = new ContentValues();
//        testValues.put(MoviesContract.movies.COLUMN_MOVIE_ID, 1);
//
//        long locationRowId;
//        locationRowId = db.insert(MoviesContract.movies.TABLE_NAME, null, testValues);
//
//        //assertEquals(12, locationRowId);
//        //assertTrue("Error: Failure to insert North Pole Location Values", locationRowId != -1);
//
//        // Test the basic content provider query
////        Cursor locationCursor = dbHelper.getReadableDatabase().rawQuery(
////                "SELECT * FROM movies", null);
//
//        Cursor locationCursor = mContext.getContentResolver().query(
//                MoviesContract.movies.CONTENT_URI,
//                null,
//                null,
//                null,
//                null
//        );
//        assertTrue("Error ",locationCursor.moveToFirst());
//        // Make sure we get the correct cursor out of the database
//        validateCursor("testBasicLocationQueries, location query", locationCursor, testValues);
//
//    }

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }
    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static ContentValues createNorthPoleLocationValues() {
        // Create a new map of values, where column names are the keys
        ContentValues testValues = new ContentValues();
        testValues.put(MoviesContract.movies.COLUMN_MOVIE_ID, 1);
        return testValues;
    }

    /*
        Students: You can uncomment this function once you have finished creating the
        LocationEntry part of the WeatherContract as well as the WeatherDbHelper.
     */
    static long insertNorthPoleLocationValues(Context context) {
        // insert our test records into the database
        MoviesDBHelper dbHelper = new MoviesDBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = createNorthPoleLocationValues();

        long locationRowId;
        locationRowId = db.insert(MoviesContract.movies.TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert North Pole Location Values", locationRowId != -1);

        return locationRowId;
    }


    public void testInsertReadProvider() {
        ContentValues testValues = createNorthPoleLocationValues();

        // Register a content observer for our insert.  This time, directly with the content resolver
        TestContentObserver tco = getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MoviesContract.movies.CONTENT_URI, true, tco);
        Uri locationUri = mContext.getContentResolver().insert(MoviesContract.movies.CONTENT_URI, testValues);

        // Did our content observer get called?  Students:  If this fails, your insert location
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);

        // Data's inserted.  IN THEORY.  Now pull some out to stare at it and verify it made
        // the round trip.

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.movies.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order
        );

        validateCursor("testInsertReadProvider. Error validating LocationEntry.",
                cursor, testValues);
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    public void testDeleteRecords() {
        testInsertReadProvider();

        // Register a content observer for our location delete.
        TestContentObserver locationObserver = getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MoviesContract.movies.CONTENT_URI, true, locationObserver);
        deleteAllRecordsFromProvider();

        // Students: If either of these fail, you most-likely are not calling the
        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider
        // delete.  (only if the insertReadProvider is succeeding)
        locationObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(locationObserver);

    }

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                MoviesContract.movies.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.movies.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Weather table during delete", 0, cursor.getCount());
        cursor.close();

    }

    public void testUpdateLocation() {
        // Create a new map of values, where column names are the keys
        ContentValues values = createNorthPoleLocationValues();

        Uri locationUri = mContext.getContentResolver().
                insert(MoviesContract.movies.CONTENT_URI, values);
        long locationRowId = ContentUris.parseId(locationUri);

        // Verify we got a row back.
        assertTrue(locationRowId != -1);
        Log.d("TEST PROVIDER", "New row id: " + locationRowId);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(MoviesContract.movies.COLUMN_MOVIE_ID, 2);

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor locationCursor = mContext.getContentResolver().query(MoviesContract.movies.CONTENT_URI, null, null, null, null);

        TestContentObserver tco = getTestContentObserver();
        locationCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(
                MoviesContract.movies.CONTENT_URI, updatedValues, MoviesContract.movies._ID + "= ?",
                new String[] { Long.toString(locationRowId)});
        assertEquals(count, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        //
        // Students: If your code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();

        locationCursor.unregisterContentObserver(tco);
        locationCursor.close();

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                MoviesContract.movies.CONTENT_URI,
                null,   // projection
                MoviesContract.movies._ID + " = " + locationRowId,
                null,   // Values for the "where" clause
                null    // sort order
        );

        validateCursor("testUpdateLocation.  Error validating location entry update.",
                cursor, updatedValues);

        cursor.close();
    }


}
