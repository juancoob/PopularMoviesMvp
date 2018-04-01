package com.juancoob.nanodegree.and.popularmoviesmvp.repository;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMovieReviewsUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMovieVideosUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMoviesUseCaseImpl;

/**
 * Created by Juan Antonio Cobos Obrero on 22/03/18.
 */

public interface Repository {
    void fetchMovies(String chosenOption, final FetchingMoviesUseCaseImpl fetchingMoviesUseCaseImpl);
    void fetchMovieReviews(int movieId, FetchingMovieReviewsUseCaseImpl fetchingMovieReviewsUseCase);
    void fetchMovieVideos(int movieId, FetchingMovieVideosUseCaseImpl fetchingMovieVideosUseCase);
}
