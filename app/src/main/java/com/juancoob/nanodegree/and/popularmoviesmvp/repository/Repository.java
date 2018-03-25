package com.juancoob.nanodegree.and.popularmoviesmvp.repository;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.FetchingMoviesUseCase;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMoviesUseCaseImpl;

import java.util.List;

/**
 * Created by Juan Antonio Cobos Obrero on 22/03/18.
 */

public interface Repository {
    void fetchMovies(String chosenOption, final FetchingMoviesUseCaseImpl fetchingMoviesUseCaseImpl);
}
