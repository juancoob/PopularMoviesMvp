package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieList;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.presenters.BasePresenter;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.ui.BaseView;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 18/02/18.
 */

public interface IMovieListContract {

    void onClickListener(Movie movie);
    void showNoFavoriteMovies();
    String getChosenOption();

    interface View extends BaseView {
        void showMovieList(ArrayList<Movie> mMovieList);
        void showApiKeyError();
        void hideErrorTextAndButton();
        void showNoFavoriteMovies();
    }

    interface Presenter extends BasePresenter {
    }

}
