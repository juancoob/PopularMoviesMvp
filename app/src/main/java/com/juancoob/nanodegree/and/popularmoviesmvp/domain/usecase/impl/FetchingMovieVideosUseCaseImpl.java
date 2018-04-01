package com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.Executor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Video;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.FetchingMovieVideosUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.AbstractUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.MoviesRepository;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 31/03/18.
 */
public class FetchingMovieVideosUseCaseImpl extends AbstractUseCase implements FetchingMovieVideosUseCase {

    private FetchingMovieVideosUseCase.Callback mCallback;
    private MoviesRepository mMoviesRepository;
    private int mMovieId;

    public FetchingMovieVideosUseCaseImpl(Executor executor,
                                          MainThread mainThread,
                                          Callback callback,
                                          MoviesRepository moviesRepository,
                                          int movieId) {
        super(executor, mainThread);
        mCallback = callback;
        mMoviesRepository = moviesRepository;
        mMovieId = movieId;
    }

    @Override
    public void run() {
        mMoviesRepository.fetchMovieVideos(mMovieId, this);
    }

    public void showVideos(final ArrayList<Video> videoList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onVideosRetrieved(videoList);
            }
        });
    }

    public void noInternetConnection() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.noInternetConnection();
            }
        });
    }
}
