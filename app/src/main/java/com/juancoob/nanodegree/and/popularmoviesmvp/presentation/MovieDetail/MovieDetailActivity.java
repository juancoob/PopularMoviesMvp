package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
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

public class MovieDetailActivity extends AppCompatActivity implements IMovieDetailContract {

    private MovieDetailFragment mMovieDetailFragment;

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
            Movie mMovie = intent.getParcelableExtra(Constants.MOVIE_DETAIL);
            mMovieDetailFragment.setMovie(mMovie);

            MovieDetailPresenter movieDetailPresenter = new MovieDetailPresenter(
                    mMovieDetailFragment,
                    ThreadExecutor.getInstance(),
                    MainThreadImpl.getInstane(),
                    new MoviesRepository(),
                    mMovie.getMovieId());

            mMovieDetailFragment.setPresenter(movieDetailPresenter);
        }
    }

    @Override
    public void onClickVideoListener(Uri videoUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(videoUri)
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    @Override
    public void onLongClickVideoListener(Uri videoUri) {
        String videoMessage = getString(R.string.video_message) + " " + videoUri;
        ShareCompat.IntentBuilder
                .from(this)
                .setChooserTitle(R.string.share_video)
                .setType("text/plain")
                .setText(videoMessage)
                .startChooser();
    }


}

