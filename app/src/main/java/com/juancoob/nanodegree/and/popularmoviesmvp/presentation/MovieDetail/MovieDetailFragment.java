package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.ActivityUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 20/02/18.
 */

public class MovieDetailFragment extends Fragment {

    @BindView(R.id.tv_movie_date)
    public TextView movieDateTextView;

    @BindView(R.id.iv_movie_poster)
    public ImageView moviePosterImageView;

    @BindView(R.id.tv_movie_average)
    public TextView movieAverageTextView;

    @BindView(R.id.rb_movie_average)
    public RatingBar movieAverageRattingBar;

    @BindView(R.id.tv_movie_plot)
    public TextView moviePlotTextView;

    private Movie mMovie;

    public static MovieDetailFragment getInstance() {
        return new MovieDetailFragment();
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
    }

    public Movie getMovie() {
        return mMovie;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateUi();
    }

    private void populateUi() {
        if(getActivity() != null) {
            getActivity().setTitle(mMovie.getTitle());
        }
        movieDateTextView.setText(mMovie.getReleaseDate());
        moviePlotTextView.setText(mMovie.getOverview());
        movieAverageTextView.setText(mMovie.getVoteAverage());
        movieAverageRattingBar.setRating(Float.parseFloat(mMovie.getVoteAverage()));

        Picasso.with(getContext()).load(ActivityUtils
                .getImageUri(mMovie.getImagePath(), getContext()))
                .into(moviePosterImageView);
    }
}
