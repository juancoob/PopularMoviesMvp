package com.juancoob.nanodegree.and.popularmoviesmvp.adapter;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Video;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 31/03/18.
 */

public interface IMovieVideoAdapterContract {
    void updateMovieVideos(ArrayList<Video> videoList);
}
