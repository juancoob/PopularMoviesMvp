package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.FetchingMovieReviewsUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMovieReviewsUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.presenters.AbstractPresenter;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.MoviesRepository;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 20/02/18.
 */

public class MovieDetailPresenter extends AbstractPresenter implements IMovieDetailContract.Presenter,
        FetchingMovieReviewsUseCase.Callback {

    private MovieDetailFragment mMovieDetailFragment;
    private MoviesRepository mMoviesRepository;
    private int mMovieId;

    public MovieDetailPresenter(MovieDetailFragment movieDetailFragment,
                                ThreadExecutor executor,
                                MainThread mainThread,
                                MoviesRepository moviesRepository,
                                int movieId) {
        super(executor, mainThread);
        mMovieDetailFragment = movieDetailFragment;
        mMoviesRepository = moviesRepository;
        mMovieId = movieId;
    }

    @Override
    public void resume() {

        FetchingMovieReviewsUseCase useCase = new FetchingMovieReviewsUseCaseImpl(
                mExecutor,
                mMainThread,
                this,
                mMoviesRepository,
                mMovieId);

        useCase.execute();

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onReviewsRetrieved(ArrayList<Review> movieReviewList) {
        mMovieDetailFragment.showMovieReviews(movieReviewList);
    }

    @Override
    public void noInternetConnection() {
        mMovieDetailFragment.noInternetConnection();
    }
}
