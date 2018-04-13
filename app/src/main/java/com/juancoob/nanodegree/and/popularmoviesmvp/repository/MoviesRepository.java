package com.juancoob.nanodegree.and.popularmoviesmvp.repository;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.juancoob.nanodegree.and.popularmoviesmvp.BuildConfig;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.MovieResponse;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.ReviewResponse;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.VideoResponse;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMovieReviewsUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMovieVideosUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.impl.FetchingMoviesUseCaseImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.REST.IMovieAPIService;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.MovieContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;

import java.util.ArrayList;

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
            case Constants.FAVORITES:
                Cursor favoriteMoviescursor = fetchingMoviesUseCaseImpl.getContentResolver()
                        .query(MovieContract.MovieEntry.CONTENT_URI,
                                null,
                                null,
                                null,
                                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE);
                fetchingMoviesUseCaseImpl.showMovies(parseCursorToMovies(favoriteMoviescursor));
                if (favoriteMoviescursor != null) {
                    favoriteMoviescursor.close();
                }
                return;
        }

        if (responseCall != null) {
            getFavoriteMovieIds(fetchingMoviesUseCaseImpl);
            responseCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                    if (response.isSuccessful()) {
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

    private void getFavoriteMovieIds(FetchingMoviesUseCaseImpl fetchingMoviesUseCaseImpl) {
        String[] column = {MovieContract.MovieEntry.COLUMN_MOVIE_ID};
        Cursor favoriteMovieIdsCursor = fetchingMoviesUseCaseImpl.getContentResolver()
                .query(MovieContract.MovieEntry.CONTENT_URI,
                        column,
                        null,
                        null,
                        MovieContract.MovieEntry.COLUMN_MOVIE_TITLE);
        fetchingMoviesUseCaseImpl.getFavoriteMovieIds(parseCursorToMovieIds(favoriteMovieIdsCursor));
        if (favoriteMovieIdsCursor != null) {
            favoriteMovieIdsCursor.close();
        }
    }

    @Override
    public void fetchMovieReviews(int movieId, final FetchingMovieReviewsUseCaseImpl fetchingMovieReviewsUseCaseImpl) {
        IMovieAPIService iMovieAPIService = retrofitRequest();
        Call<ReviewResponse> responseCall = iMovieAPIService.getReviewsByMovieId(movieId, BuildConfig.MOVIE_DB_API_KEY);
        if (responseCall != null) {
            responseCall.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                    if (response.isSuccessful()) {
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
    public void fetchMovieVideos(int movieId, final FetchingMovieVideosUseCaseImpl fetchingMovieVideosUseCaseImpl) {
        IMovieAPIService iMovieAPIService = retrofitRequest();
        Call<VideoResponse> responseCall = iMovieAPIService.getVideosByMovieId(movieId, BuildConfig.MOVIE_DB_API_KEY);
        if (responseCall != null) {
            responseCall.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(@NonNull Call<VideoResponse> call, @NonNull Response<VideoResponse> response) {
                    if (response.isSuccessful()) {
                        fetchingMovieVideosUseCaseImpl.showVideos(response.body().getVideoList());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VideoResponse> call, @NonNull Throwable t) {
                    // no internet
                    fetchingMovieVideosUseCaseImpl.noInternetConnection();
                }
            });
        }
    }

    private ArrayList<Movie> parseCursorToMovies(Cursor cursor) {
        Movie movie;
        ArrayList<Movie> movieList = new ArrayList<>();
        if(cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                movie = new Movie();
                movie.setMovieId(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE)));
                movie.setImagePath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW)));
                movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE)));
                movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT)));
                movie.setVideo(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO)) != 0);
                movie.setPopularity(cursor.getFloat(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY)));
                movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE)));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH)));
                movie.setIsAdult(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ADULT)) != 0);
                movie.setFavorite(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE)) != 0);
                movieList.add(movie);
            }
        }
        return movieList;
    }

    private ArrayList<Integer> parseCursorToMovieIds(Cursor cursor) {
        ArrayList<Integer> movieIds = new ArrayList<>();
        if(cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                movieIds.add(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));
            }
        }
        return movieIds;
    }

}
