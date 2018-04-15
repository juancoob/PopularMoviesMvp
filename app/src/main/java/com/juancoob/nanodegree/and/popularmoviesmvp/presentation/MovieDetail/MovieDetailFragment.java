package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl.MovieReviewAdapter;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl.MovieVideoAdapter;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Video;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.ActivityUtils;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;
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

    @BindView(R.id.tb_details)
    public Toolbar detailsToolbar;

    private Movie mMovie;
    private ArrayList<Video> mMovieVideoList = new ArrayList<>();
    private ArrayList<Review> mMovieReviewList = new ArrayList<>();
    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieReviewAdapter mMovieReviewAdapter;
    private MovieVideoAdapter mMovieVideoAdapter;
    private IMovieDetailContract mIMovieDetailContract;
    private LinearLayoutManager mReviewsManager;
    private Parcelable mCurrentReviewRecyclerViewState;
    private LinearLayoutManager mVideoManager;
    private Parcelable mCurrentVideoRecyclerViewState;
    private boolean mMovieHasChanged;

    public static MovieDetailFragment getInstance() {
        return new MovieDetailFragment();
    }

    protected void setPresenter(MovieDetailPresenter movieDetailPresenter) {
        mMovieDetailPresenter = movieDetailPresenter;
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.MOVIE, mMovie);
        outState.putParcelableArrayList(Constants.MOVIE_VIDEO_LIST, mMovieVideoList);
        outState.putParcelable(Constants.CURRENT_VIDEO_POSITION, mVideoManager.onSaveInstanceState());
        outState.putParcelableArrayList(Constants.MOVIE_REVIEW_LIST, mMovieReviewList);
        outState.putParcelable(Constants.CURRENT_REVIEW_POSITION, mReviewsManager.onSaveInstanceState());
        outState.putBoolean(Constants.MOVIE_CHANGED, mMovieHasChanged);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable(Constants.MOVIE);
            mMovieVideoList = savedInstanceState.getParcelableArrayList(Constants.MOVIE_VIDEO_LIST);
            mCurrentVideoRecyclerViewState = savedInstanceState.getParcelable(Constants.CURRENT_VIDEO_POSITION);
            mMovieReviewList = savedInstanceState.getParcelableArrayList(Constants.MOVIE_REVIEW_LIST);
            mCurrentReviewRecyclerViewState = savedInstanceState.getParcelable(Constants.CURRENT_REVIEW_POSITION);
            mMovieHasChanged = savedInstanceState.getBoolean(Constants.MOVIE_CHANGED);
            updateMovieList();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        populateUi();
        initializeRecyclerViews();
    }

    private void populateUi() {

        if(getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(detailsToolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        mReviewsManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        movieReviewsRecyclerView.setLayoutManager(mReviewsManager);
        mMovieReviewAdapter = new MovieReviewAdapter(getContext());
        movieReviewsRecyclerView.setAdapter(mMovieReviewAdapter);

        mVideoManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        movieVideosRecyclerView.setLayoutManager(mVideoManager);
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
        mMovieReviewList = reviews;
        mMovieReviewAdapter.updateMovieReviews(reviews);
        if(mCurrentReviewRecyclerViewState != null) {
            mReviewsManager.onRestoreInstanceState(mCurrentReviewRecyclerViewState);
        }
    }

    @Override
    public void showMovieVideos(ArrayList<Video> videos) {
        mMovieVideoList = videos;
        mMovieVideoAdapter.updateMovieVideos(videos);
        if(mCurrentVideoRecyclerViewState != null) {
            mVideoManager.onRestoreInstanceState(mCurrentVideoRecyclerViewState);
        }
    }

    @Override
    public ArrayList<Video> getMovieVideoList() {
        return mMovieVideoList;
    }

    @Override
    public ArrayList<Review> getMovieReviewList() {
        return mMovieReviewList;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movie_details_menu, menu);
        if(mMovie.isFavorite()) {
            menu.getItem(0).setIcon(R.drawable.ic_favorite);
            menu.getItem(0).setTitle(R.string.favorite_movie);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_no_favorite);
            menu.getItem(0).setTitle(R.string.no_favorite_movie);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            if(getActivity() != null) {
                getActivity().onBackPressed();
            }
        } else if(item.getTitle().equals(getString(R.string.no_favorite_movie))) {
            item.setIcon(R.drawable.ic_favorite);
            item.setTitle(R.string.favorite_movie);

            Uri uri = null;
            if(getActivity() != null) {
                mMovie.setFavorite(true);
                uri = ActivityUtils.insertMovie(mMovie, getActivity().getContentResolver());
            }

            if(uri == null) {
                mMovie.setFavorite(false);
                Toast.makeText(getContext(), R.string.not_added_favorite_movies_error, Toast.LENGTH_SHORT).show();
                item.setIcon(R.drawable.ic_no_favorite);
            } else {
                mMovieHasChanged = true;
                updateMovieList();
            }
        } else {
            item.setIcon(R.drawable.ic_no_favorite);
            item.setTitle(R.string.no_favorite_movie);

            int rowsDeleted = 0;
            if(getActivity() != null) {
                mMovie.setFavorite(false);
                rowsDeleted = ActivityUtils.deleteMovie(mMovie.getMovieId(), getActivity().getContentResolver());
            }

            if(rowsDeleted == 0) {
                mMovie.setFavorite(true);
                Toast.makeText(getContext(), R.string.not_removed_favorite_movies_error, Toast.LENGTH_SHORT).show();
                item.setIcon(R.drawable.ic_favorite);
            } else {
                mMovieHasChanged = true;
                updateMovieList();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMovieList() {
        Intent returnIntent = new Intent();
        if(getActivity() != null && mMovieHasChanged) {
            returnIntent.putExtra(Constants.FAVORITE_MOVIE_ID, mMovie.getMovieId());
            getActivity().setResult(Activity.RESULT_OK, returnIntent);
        } else {
            getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
        }
    }
}
