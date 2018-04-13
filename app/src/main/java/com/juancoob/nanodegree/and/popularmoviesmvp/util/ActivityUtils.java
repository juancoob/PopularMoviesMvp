package com.juancoob.nanodegree.and.popularmoviesmvp.util;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.MovieContract;

/**
 * Created by Juan Antonio Cobos Obrero on 19/02/18.
 */

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment, int frameId) {
        if (fragmentManager != null && fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
        }
    }

    public static Uri getMovieImageUri(String imagePath) {
        return Uri.parse(Constants.MOVIE_DB_BASE_IMAGE_URL).buildUpon()
                .appendEncodedPath(Constants.MOVIE_DB_DEFAULT_IMAGE_SIZE)
                .appendEncodedPath(imagePath)
                .build();
    }

    public static Uri getYoutubeVideoImageUri(String key) {
        return Uri.parse(Constants.YOUTUBE_VIDEO_BASE_IMAGE_URL).buildUpon()
                .appendEncodedPath(key)
                .appendEncodedPath(Constants.YOUTUBE_VIDEO_DEFAULT_IMAGE)
                .build();
    }

    public static Uri getYoutubeVideoUri(String key) {
        return Uri.parse(Constants.YOUTUBE_VIDEO_BASE_URL).buildUpon()
                .appendQueryParameter(Constants.YOUTUBE_VIDEO_V, key)
                .build();
    }

    public static Uri insertMovie(Movie movie, ContentResolver contentResolver) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getImagePath());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT, movie.getVoteCount());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO, movie.isVideo());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY, movie.getPopularity());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH, movie.getBackdropPath());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ADULT, movie.isAdult());
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE, movie.isFavorite());
        return contentResolver.insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
    }

    public static int deleteMovie(int id, ContentResolver contentResolver) {
        return contentResolver.delete(MovieContract.MovieEntry.buildNewUriWithId(String.valueOf(id)), null, null);
    }

}
