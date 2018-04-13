package com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.Executor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.FetchingMovieReviewsUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.AbstractUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.MoviesRepository;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 27/03/18.
 */
public class FetchingMovieReviewsUseCaseImpl extends AbstractUseCase implements FetchingMovieReviewsUseCase {

    private final FetchingMovieReviewsUseCase.Callback mCallback;
    private final MoviesRepository mMoviesRepository;
    private final int mMovieId;

    public FetchingMovieReviewsUseCaseImpl(Executor executor,
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
        mMoviesRepository.fetchMovieReviews(mMovieId, this);
    }

    public void showReviews(final ArrayList<Review> reviewList) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onReviewsRetrieved(reviewList);
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
