package com.juancoob.nanodegree.and.popularmoviesmvp.MovieList;


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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.IMovieListAdapterContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.MovieListAdapter;
import com.juancoob.nanodegree.and.popularmoviesmvp.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 18/02/18.
 */

public class MovieListFragment extends Fragment implements IMovieListContract.View {

    @BindView(R.id.rv_movie_list)
    public RecyclerView movieListRecyclerView;

    @BindView(R.id.pb_movie)
    public ProgressBar movieProgressBar;

    private IMovieListAdapterContract adapter;
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
        ArrayList<Movie> mMovieList = mMovieListPresenter.getMovieList();
        initRecyclerView();
        showProgressBar();
        if (mMovieList.size() == 0) {
            mMovieListPresenter.fetchMovies();
        } else {
            hideProgressBar();
            ((RecyclerView.Adapter)adapter).notifyDataSetChanged();
        }

    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), getNumberOfColumns());
        movieListRecyclerView.setLayoutManager(layoutManager);
        adapter = new MovieListAdapter(getContext(), mMovieListPresenter.getMovieList(), mIMovieListContract);
        movieListRecyclerView.setAdapter((RecyclerView.Adapter) adapter);
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
        hideProgressBar();
        adapter.updateMovieList(movieList);
    }

    @Override
    public void showToast(int messageId) {
        Toast.makeText(getContext(), getString(messageId), Toast.LENGTH_LONG).show();
    }

    public void showProgressBar() {
        movieProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        movieProgressBar.setVisibility(View.GONE);
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
}
