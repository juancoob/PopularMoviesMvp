package com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.UseCase;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 22/03/18.
 */

public interface FetchingMoviesUseCase extends UseCase {

    interface Callback {
        void onMoviesRetrieved(ArrayList<Movie> movieList);
        void onFavoriteMovieIdsRetrieved(ArrayList<Integer> favoriteMovieIdsList);
        void noInternetConnection();
        void noApiKey();
        void noFavoriteMovies();
    }

}
