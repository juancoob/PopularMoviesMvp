package com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl;

import android.support.annotation.Nullable;

import com.juancoob.nanodegree.and.popularmoviesmvp.BuildConfig;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.Executor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.AbstractUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.FetchingMoviesUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.MoviesRepository;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 23/03/18.
 */

public class FetchingMoviesUseCaseImpl extends AbstractUseCase implements FetchingMoviesUseCase {

    private FetchingMoviesUseCase.Callback mCallback;
    private MoviesRepository mMoviesRepository;
    private String mChosenOption;

    public FetchingMoviesUseCaseImpl(Executor executor,
                                     MainThread mainThread,
                                     Callback callback,
                                     MoviesRepository moviesRepository,
                                     String chosenOption) {
        super(executor, mainThread);
        mCallback = callback;
        mMoviesRepository = moviesRepository;
        mChosenOption = chosenOption;
    }

    @Override
    public void run() {

        // If the project has no API key, stop the search process
        if (BuildConfig.MOVIE_DB_API_KEY.isEmpty()) {
            noApiKey();
            return;
        }

        // Fetch movies
        mMoviesRepository.fetchMovies(mChosenOption, this);
    }

    public void noApiKey() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.noApiKey();
            }
        });
    }

    public void showMovies(@Nullable final ArrayList<Movie> movieList) {

        if (movieList == null || movieList.size() == 0) {
            noInternetConnection();
            return;
        }

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMoviesRetrieved(movieList);
            }
        });
    }

    private void noInternetConnection() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.noInternetConnection();
            }
        });
    }

}
