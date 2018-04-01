package com.juancoob.nanodegree.and.popularmoviesmvp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 25/03/18.
 */

public class VideoResponse {

    @SerializedName("id")
    private int mMovieId;

    @SerializedName("results")
    private ArrayList<Video> mVideoList;

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public ArrayList<Video> getVideoList() {
        return mVideoList;
    }

    public void setVideoList(ArrayList<Video> videoList) {
        mVideoList = videoList;
    }
}
