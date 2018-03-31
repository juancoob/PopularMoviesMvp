package com.juancoob.nanodegree.and.popularmoviesmvp.repository.REST;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.MovieResponse;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.ReviewResponse;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Juan Antonio Cobos Obrero on 22/02/18.
 */

public interface IMovieAPIService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideosByMovieId(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResponse> getReviewsByMovieId(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

}
