package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.impl.MainThreadImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.MoviesRepository;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.ActivityUtils;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;

/**
 * Created by Juan Antonio Cobos Obrero on 07/03/2018.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private MovieDetailFragment mMovieDetailFragment;
    private MovieDetailPresenter mMovieDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();

        // Add the up functionality
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mMovieDetailFragment = (MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mMovieDetailFragment == null) {
            // Create the movie detail fragment
            mMovieDetailFragment = MovieDetailFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mMovieDetailFragment, R.id.contentFrame);
        }

        if(intent.hasExtra(Constants.MOVIE_DETAIL)) {
            Movie movie = intent.getParcelableExtra(Constants.MOVIE_DETAIL);
            mMovieDetailFragment.setMovie(movie);

            mMovieDetailPresenter = new MovieDetailPresenter(
                    mMovieDetailFragment,
                    ThreadExecutor.getInstance(),
                    MainThreadImpl.getInstane(),
                    new MoviesRepository(),
                    movie.getMovieId());

            mMovieDetailFragment.setPresenter(mMovieDetailPresenter);
        }

        if (savedInstanceState != null) {
            mMovieDetailFragment.setMovie((Movie) savedInstanceState.getParcelable(Constants.MOVIE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.MOVIE, mMovieDetailFragment.getMovie());
        super.onSaveInstanceState(outState);
    }
}

