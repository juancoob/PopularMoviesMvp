package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieList;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.IMovieListAdapterContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl.MovieListAdapter;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Juan Antonio Cobos Obrero on 18/02/18.
 */

public class MovieListFragment extends Fragment implements IMovieListContract.View {

    @BindView(R.id.rv_movie_list)
    public RecyclerView movieListRecyclerView;

    @BindView(R.id.pb_movie)
    public ProgressBar movieProgressBar;

    @BindView(R.id.tv_warning)
    public TextView warningTextView;

    @BindView(R.id.btn_retry)
    public Button retryButton;

    private IMovieListAdapterContract mAdapter;
    private IMovieListContract.Presenter mMovieListPresenter;
    private IMovieListContract mIMovieListContract;
    private GridLayoutManager mGridLayoutManager;
    private Parcelable mCurrentRecyclerViewState;
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private ArrayList<Integer> mMovieIdList = new ArrayList<>();

    public MovieListFragment() {
    }

    public static MovieListFragment getInstance() {
        return new MovieListFragment();
    }

    public void setPresenter(IMovieListContract.Presenter movieListPresenter) {
        mMovieListPresenter = movieListPresenter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.fragment_movie_list, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            mCurrentRecyclerViewState = savedInstanceState.getParcelable(Constants.CURRENT_GRID_POSITION);
            mMovieList = savedInstanceState.getParcelableArrayList(Constants.MOVIE_LIST);
            mMovieIdList = savedInstanceState.getIntegerArrayList(Constants.MOVIE_ID_LIST);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.CURRENT_GRID_POSITION, mGridLayoutManager.onSaveInstanceState());
        outState.putParcelableArrayList(Constants.MOVIE_LIST, mMovieList);
        outState.putIntegerArrayList(Constants.MOVIE_ID_LIST, mAdapter.getMovieIdList());
        mMovieIdList = mAdapter.getMovieIdList();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getActivity() != null) {
            getActivity().setTitle(getResources().getString(R.string.app_name));
        }

        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMovieListPresenter.resume();
    }

    private void initRecyclerView() {
        mGridLayoutManager = new GridLayoutManager(getContext(), getNumberOfColumns());
        movieListRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new MovieListAdapter(getContext(), mIMovieListContract);
        movieListRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
    }

    private int getNumberOfColumns() {
        if (getActivity() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            int columns = width / getResources().getInteger(R.integer.width_divider);
            if (columns >= 2) return columns;
        }
        return 2;
    }

    @Override
    public void showMovieList(ArrayList<Movie> movieList) {
        mMovieList = movieList;
        mAdapter.updateMovieList(movieList);
        if(mCurrentRecyclerViewState != null) {
            mGridLayoutManager.onRestoreInstanceState(mCurrentRecyclerViewState);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IMovieListContract) {
            mIMovieListContract = (IMovieListContract) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIMovieListContract = null;
    }

    @Override
    public void showProgress() {
        movieProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        movieProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void noInternetConnection() {
        // Empty the list and show the message
        mAdapter.updateMovieList(new ArrayList<Movie>());
        warningTextView.setVisibility(View.VISIBLE);
        warningTextView.setText(getString(R.string.no_internet));
        retryButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_retry)
    public void onRetryButtonClick() {
        mMovieListPresenter.resume();
        hideErrorTextAndButton();
    }

    @Override
    public void showApiKeyError() {
        warningTextView.setVisibility(View.VISIBLE);
        warningTextView.setText(getString(R.string.no_api_key));
    }

    @Override
    public void hideErrorTextAndButton() {
        warningTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    @Override
    public void showNoFavoriteMovies() {
        mAdapter.updateMovieList(new ArrayList<Movie>());
        warningTextView.setVisibility(View.VISIBLE);
        warningTextView.setText(getString(R.string.favorite_movies_empty));
    }

    @Override
    public void retrieveFavoriteMovieIds(ArrayList<Integer> favoriteMovieIdsList) {
        mMovieIdList = favoriteMovieIdsList;
        mAdapter.retrieveFavoriteMovieIdList(favoriteMovieIdsList);
    }

    @Override
    public ArrayList<Movie> getMovieList() {
        return mMovieList;
    }

    @Override
    public ArrayList<Integer> getMovieIdList() {
        return mMovieIdList;
    }

}
