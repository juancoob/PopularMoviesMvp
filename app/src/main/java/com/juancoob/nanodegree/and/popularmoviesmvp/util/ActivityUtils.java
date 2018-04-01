package com.juancoob.nanodegree.and.popularmoviesmvp.util;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

}
