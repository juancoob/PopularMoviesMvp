package com.juancoob.nanodegree.and.popularmoviesmvp.repository.database;

import android.provider.BaseColumns;

/**
 * Created by Juan Antonio Cobos Obrero on 3/04/18.
 */
public class MovieContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoriteMovies";
        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_MOVIE_POSTER_PATH = "moviePosterPath";
        public static final String COLUMN_MOVIE_OVERVIEW = "movieOverview";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movieVoteAverage";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movieReleaseDate";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "movieVoteCount";
        public static final String COLUMN_MOVIE_VIDEO = "movieVideo";
        public static final String COLUMN_MOVIE_POPULARITY = "moviePopularity";
        public static final String COLUMN_MOVIE_ORIGINAL_LANGUAGE = "movieOriginalLanguage";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "movieOriginalTitle";
        //public static final String COLUMN_MOVIE_GENRE_IDS = "movieGenreIds";
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "movieBackdropPath";
        public static final String COLUMN_MOVIE_ADULT = "movieAdult";
        public static final String COLUMN_MOVIE_FAVORITE = "favoriteMovie";
    }

}