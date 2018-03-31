package com.juancoob.nanodegree.and.popularmoviesmvp.repository;

import android.support.annotation.NonNull;

import com.juancoob.nanodegree.and.popularmoviesmvp.BuildConfig;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.MovieResponse;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.ReviewResponse;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMovieReviewsUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMoviesUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.REST.IMovieAPIService;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan Antonio Cobos Obrero on 22/03/18.
 */

public class MoviesRepository implements Repository {

    private IMovieAPIService retrofitRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.MOVIE_BD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         return retrofit.create(IMovieAPIService.class);
    }

    @Override
    public void fetchMovies(String chosenOption, final FetchingMoviesUseCaseImpl fetchingMoviesUseCaseImpl) {

        // Create the request depending on the option selected
        IMovieAPIService iMovieAPIService = retrofitRequest();

        Call<MovieResponse> responseCall = null;
        switch (chosenOption) {
            case Constants.POPULAR:
                responseCall = iMovieAPIService.getPopularMovies(BuildConfig.MOVIE_DB_API_KEY);
                break;
            case Constants.TOP:
                responseCall = iMovieAPIService.getTopRatedMovies(BuildConfig.MOVIE_DB_API_KEY);
                break;
        }

        if(responseCall != null) {
            responseCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    if(response.body() != null) {
                        fetchingMoviesUseCaseImpl.showMovies(response.body().getMovieList());
                    } else {
                        fetchingMoviesUseCaseImpl.noApiKey();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    // no internet
                    fetchingMoviesUseCaseImpl.showMovies(null);
                }
            });
        }
    }

    @Override
    public void fetchMovieReviews(int movieId, final FetchingMovieReviewsUseCaseImpl fetchingMovieReviewsUseCaseImpl) {
        IMovieAPIService iMovieAPIService = retrofitRequest();
        Call<ReviewResponse> responseCall = iMovieAPIService.getReviewsByMovieId(movieId, BuildConfig.MOVIE_DB_API_KEY);
        if(responseCall != null) {
            responseCall.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                    if(response.isSuccessful()) {
                        fetchingMovieReviewsUseCaseImpl.showReviews(response.body().getReviewList());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                    // no internet
                    fetchingMovieReviewsUseCaseImpl.noInternetConnection();
                }
            });
        }
    }

    @Override
    public void fetchMovieVideos(int movieId) {

    }


}
