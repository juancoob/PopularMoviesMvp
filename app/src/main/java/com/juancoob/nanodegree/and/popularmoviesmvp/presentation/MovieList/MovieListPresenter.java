package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieList;


import android.content.ContentResolver;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.MainThread;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.FetchingMoviesUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMoviesUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.presenters.AbstractPresenter;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.MoviesRepository;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 18/02/18.
 */

public class MovieListPresenter extends AbstractPresenter implements IMovieListContract.Presenter,
        FetchingMoviesUseCase.Callback {

    private final IMovieListContract.View mMovieListFragment;
    private final MoviesRepository mMoviesRepository;
    private String mChosenOption;
    private final ContentResolver mContentResolver;

    public MovieListPresenter(IMovieListContract.View movieListFragment,
                              ThreadExecutor executor,
                              MainThread mainThread,
                              MoviesRepository moviesRepository,
                              String chosenOption,
                              ContentResolver contentResolver) {
        super(executor, mainThread);
        mMovieListFragment = movieListFragment;
        mMoviesRepository = moviesRepository;
        mChosenOption = chosenOption;
        mContentResolver = contentResolver;
    }

    @Override
    public void resume() {
        mMovieListFragment.showProgress();

        // Initialize the use case
        FetchingMoviesUseCase useCase = new FetchingMoviesUseCaseImpl(
                mExecutor,
                mMainThread,
                this,
                mMoviesRepository,
                mChosenOption,
                mContentResolver);

        // Run the use case
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
    public void onMoviesRetrieved(ArrayList<Movie> movieList) {
        mMovieListFragment.hideProgress();
        mMovieListFragment.showMovieList(movieList);
    }

    @Override
    public void onFavoriteMovieIdsRetrieved(ArrayList<Integer> favoriteMovieIdsList) {
        mMovieListFragment.getFavoriteMovieIds(favoriteMovieIdsList);
    }

    @Override
    public void noInternetConnection() {
        mMovieListFragment.hideProgress();
        mMovieListFragment.noInternetConnection();
    }

    @Override
    public void noApiKey() {
        mMovieListFragment.hideProgress();
        mMovieListFragment.showApiKeyError();
    }

    @Override
    public void noFavoriteMovies() {
        mMovieListFragment.hideProgress();
        mMovieListFragment.showNoFavoriteMovies();
    }

    public String getChosenOption() {
        return mChosenOption;
    }

    public void setChosenOption(String chosenOption) {
        mChosenOption = chosenOption;
    }
}
