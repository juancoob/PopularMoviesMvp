package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl.MovieReviewAdapter;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl.MovieVideoAdapter;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Video;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.ActivityUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Juan Antonio Cobos Obrero on 20/02/18.
 */

public class MovieDetailFragment extends Fragment implements IMovieDetailContract.View {

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

    @BindView(R.id.tv_reviews_label)
    public TextView reviewsLabelTextView;

    @BindView(R.id.rv_movie_reviews)
    public RecyclerView movieReviewsRecyclerView;

    @BindView(R.id.tv_video_label)
    public TextView videoLabelTextView;

    @BindView(R.id.pb_movie_videos)
    public ProgressBar movieVideosProgressBar;

    @BindView(R.id.rv_movie_videos)
    public RecyclerView movieVideosRecyclerView;

    @BindView(R.id.pb_movie_reviews)
    public ProgressBar movieReviewsProgressBar;

    @BindView(R.id.tv_no_internet)
    public TextView noInternetTextView;

    @BindView(R.id.btn_retry)
    public Button retryButton;

    private Movie mMovie;
    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieReviewAdapter mMovieReviewAdapter;
    private MovieVideoAdapter mMovieVideoAdapter;
    private IMovieDetailContract mIMovieDetailContract;

    public static MovieDetailFragment getInstance() {
        return new MovieDetailFragment();
    }

    protected void setPresenter(MovieDetailPresenter movieDetailPresenter) {
        mMovieDetailPresenter = movieDetailPresenter;
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
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
        initializeRecyclerViews();
    }

    private void populateUi() {
        if (getActivity() != null) {
            getActivity().setTitle(mMovie.getTitle());
        }
        movieDateTextView.setText(mMovie.getReleaseDate());
        moviePlotTextView.setText(mMovie.getOverview());
        movieAverageTextView.setText(mMovie.getVoteAverage());
        movieAverageRattingBar.setRating(Float.parseFloat(mMovie.getVoteAverage()));

        Picasso.with(getContext()).load(ActivityUtils
                .getMovieImageUri(mMovie.getImagePath()))
                .into(moviePosterImageView);
    }

    private void initializeRecyclerViews() {
        LinearLayoutManager reviewsManager = new LinearLayoutManager(getContext());
        movieReviewsRecyclerView.setLayoutManager(reviewsManager);
        mMovieReviewAdapter = new MovieReviewAdapter(getContext());
        movieReviewsRecyclerView.setAdapter(mMovieReviewAdapter);

        LinearLayoutManager videoManager = new LinearLayoutManager(getContext());
        movieVideosRecyclerView.setLayoutManager(videoManager);
        mMovieVideoAdapter = new MovieVideoAdapter(getContext(), mIMovieDetailContract);
        movieVideosRecyclerView.setAdapter(mMovieVideoAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMovieDetailPresenter.resume();
    }

    @Override
    public void showProgress() {
        movieReviewsProgressBar.setVisibility(View.VISIBLE);
        movieVideosProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        movieReviewsProgressBar.setVisibility(View.GONE);
        movieVideosProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMovieReviews(ArrayList<Review> reviews) {
        mMovieReviewAdapter.updateMovieReviews(reviews);
    }

    @Override
    public void showMovieVideos(ArrayList<Video> videos) {
        mMovieVideoAdapter.updateMovieVideos(videos);
    }

    @Override
    public void noInternetConnection() {
        hideRecyclersAndLabels();
        noInternetTextView.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_retry)
    public void onClickBtnRetry() {
        mMovieDetailPresenter.resume();
        showRecyclersAndLabels();
        noInternetTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    public void hideRecyclersAndLabels() {
        reviewsLabelTextView.setVisibility(View.GONE);
        movieReviewsRecyclerView.setVisibility(View.GONE);
        videoLabelTextView.setVisibility(View.GONE);
        movieVideosRecyclerView.setVisibility(View.GONE);
    }

    public void showRecyclersAndLabels() {
        reviewsLabelTextView.setVisibility(View.VISIBLE);
        movieReviewsRecyclerView.setVisibility(View.VISIBLE);
        videoLabelTextView.setVisibility(View.VISIBLE);
        movieVideosRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IMovieDetailContract) {
            mIMovieDetailContract = (IMovieDetailContract) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIMovieDetailContract = null;
    }
}
