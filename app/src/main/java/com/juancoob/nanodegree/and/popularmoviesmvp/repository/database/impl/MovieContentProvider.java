package com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.impl;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.MovieContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.MovieDbHelper;

/**
 * Created by Juan Antonio Cobos Obrero on 4/04/18.
 */
public class MovieContentProvider extends ContentProvider {
    public static final int FAVORITES = 100;
    public static final int FAVORITE_ID = 101;
    private static final UriMatcher sUriMatcher = builUriMatcher();
    private MovieDbHelper mMovieDbHelper;

    private static UriMatcher builUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITE_MOVIES, FAVORITES);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_FAVORITE_MOVIES + "/#", FAVORITE_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mMovieDbHelper = new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnedCursor;

        switch (match) {
            case FAVORITES:
                returnedCursor = db.query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(String.format(getContext().getString(R.string.unknown_uri), uri.toString()));
        }

        returnedCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnedCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException(getContext().getString(R.string.no_implemented));
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVORITES:
                long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException(String.format(getContext().getString(R.string.failed_insert_row), uri.toString()));
                }
                break;
            default:
                throw new UnsupportedOperationException(String.format(getContext().getString(R.string.unknown_uri), uri.toString()));
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int favoriteMoviesDeleted;

        switch (match) {
            case FAVORITE_ID:
                String movieId = uri.getLastPathSegment();
                favoriteMoviesDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?", new String[]{movieId});
                break;
            default:
                throw new UnsupportedOperationException(String.format(getContext().getString(R.string.unknown_uri), uri.toString()));
        }

        if (favoriteMoviesDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return favoriteMoviesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException(getContext().getString(R.string.no_implemented));
    }
}
