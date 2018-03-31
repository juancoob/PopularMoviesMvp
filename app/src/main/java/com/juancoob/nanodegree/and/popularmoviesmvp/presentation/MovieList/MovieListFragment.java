package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieList;


import android.content.Context;
import android.os.Bundle;
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

    @BindView(R.id.tv_no_internet_nor_api_key)
    public TextView noInternetNorApiKeyTextView;

    @BindView(R.id.btn_retry)
    public Button retryButton;

    private IMovieListAdapterContract mAdapter;
    private IMovieListContract.Presenter mMovieListPresenter;
    private IMovieListContract mIMovieListContract;

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
    public void onStart() {
        super.onStart();
        if(getActivity() != null) {
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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getNumberOfColumns());
        movieListRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MovieListAdapter(getContext(), mIMovieListContract);
        movieListRecyclerView.setAdapter((RecyclerView.Adapter) mAdapter);
    }

    private int getNumberOfColumns() {
        if(getActivity() != null) {
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
        mAdapter.updateMovieList(movieList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof IMovieListContract) {
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
        noInternetNorApiKeyTextView.setVisibility(View.VISIBLE);
        noInternetNorApiKeyTextView.setText(getString(R.string.no_internet));
        retryButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_retry)
    public void onRetryButtonClick() {
        mMovieListPresenter.resume();
        hideErrorTextAndButton();
    }

    @Override
    public void showApiKeyError() {
        noInternetNorApiKeyTextView.setVisibility(View.VISIBLE);
        noInternetNorApiKeyTextView.setText(getString(R.string.no_api_key));
    }

    @Override
    public void hideErrorTextAndButton() {
        noInternetNorApiKeyTextView.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

}
