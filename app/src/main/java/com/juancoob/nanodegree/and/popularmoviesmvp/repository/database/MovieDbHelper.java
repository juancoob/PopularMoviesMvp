package com.juancoob.nanodegree.and.popularmoviesmvp.repository.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan Antonio Cobos Obrero on 3/04/18.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoriteMovies.db";

    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITE_MOVIES_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO + " INTEGER DEFAULT 0, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                //MovieContract.MovieEntry.COLUMN_MOVIE_GENRE_IDS + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_ADULT + " INTEGER DEFAULT 0, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_FAVORITE + " INTEGER DEFAULT 0 " +
                ");";

        /*The next version will implement the genre ids*/

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
