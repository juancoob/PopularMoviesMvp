package com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.IMovieListAdapterContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.MovieContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.MovieDbHelper;

/**
 * Created by Juan Antonio Cobos Obrero on 4/04/18.
 */
public class MovieDb {
    private static final MovieDb movieDb = new MovieDb();
    private IMovieListAdapterContract mAdapter;

    private SQLiteDatabase mDb;

    public static MovieDb getInstance() {
        return movieDb;
    }

    private MovieDb() {
    }

    public void init(Context context) {
        if(mDb == null) {
            MovieDbHelper dbHelper = new MovieDbHelper(context);
            mDb = dbHelper.getWritableDatabase();
        }
    }

    public Cursor getFavoriteMovies() {
        return mDb.query(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE
        );
    }

    public Cursor getFavoriteMovieIds() {
        String[] column = {MovieContract.MovieEntry.COLUMN_MOVIE_ID};
        return mDb.query(
                MovieContract.MovieEntry.TABLE_NAME,
                column,
                null,
                null,
                null,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE
        );
    }

    public long addFavoriteMovie(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getImagePath());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, movie.getVoteAverage());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT, movie.getVoteCount());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO, movie.isVideo());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY, movie.getPopularity());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE, movie.getOriginalTitle());
        //cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_GENRE_IDS, movie.getGenreIdsList());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH, movie.getBackdropPath());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_ADULT, movie.isAdult());
        cv.put(MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE, movie.isFavorite());
        return mDb.insert(MovieContract.MovieEntry.TABLE_NAME, null, cv);
    }

    public int removeMovie(int movieId) {
        return mDb.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=" + movieId, null);
    }

}
