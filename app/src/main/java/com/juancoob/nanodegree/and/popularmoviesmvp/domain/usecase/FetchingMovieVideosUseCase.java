package com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Video;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.UseCase;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 31/03/18.
 */
public interface FetchingMovieVideosUseCase extends UseCase {

    interface Callback {
        void onVideosRetrieved(final ArrayList<Video> videoList);
        void noInternetConnection();
    }

}
