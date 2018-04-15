package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.executor.impl.ThreadExecutor;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail.MovieDetailActivity;
import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.MoviesRepository;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.threading.impl.MainThreadImpl;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.ActivityUtils;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;

public class MovieListActivity extends AppCompatActivity implements IMovieListContract {

    private MovieListFragment mMovieListFragment;
    private MovieListPresenter mMovieListPresenter;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        String mChosenOption;

        // If it loaded movies, it'll retrieve them
        if (savedInstanceState != null) {
            mChosenOption = savedInstanceState.getString(Constants.MOVIE_OPTION);
        } else {
            mChosenOption = Constants.POPULAR;
        }

        mMovieListFragment = (MovieListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (mMovieListFragment == null) {
            // Create the movie list fragment
            mMovieListFragment = MovieListFragment.getInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mMovieListFragment, R.id.contentFrame);
        }

        // Create the movie list presenter
        mMovieListPresenter = new MovieListPresenter(
                mMovieListFragment,
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstane(),
                new MoviesRepository(),
                mChosenOption,
                getContentResolver());

        mMovieListFragment.setPresenter(mMovieListPresenter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.MOVIE_OPTION, mMovieListPresenter.getChosenOption());
    }

    @Override
    public void onClickListener(Movie movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(Constants.MOVIE_DETAIL, movie);
        startActivityForResult(intent, Constants.FAVORITE_REQUEST_CODE);
    }

    // It is going to update the empty list when it's called from favorite movies
    @Override
    public void showNoFavoriteMovies() {
        mMovieListFragment.showNoFavoriteMovies();
    }

    @Override
    public String getChosenOption() {
        return mMovieListPresenter.getChosenOption();
    }


    //TODO CHANGE TO VIEWPAGER

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        showOption(mMovieListPresenter.getChosenOption());
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular_movies:
                mMovieListPresenter.setChosenOption(Constants.POPULAR);
                mMovieListFragment.hideErrorTextAndButton();
                mMovieListFragment.showProgress();
                mMovieListPresenter.fetchingMoviesUseCase();
                showOption(Constants.POPULAR);
                break;
            case R.id.top_rated:
                mMovieListPresenter.setChosenOption(Constants.TOP);
                mMovieListFragment.hideErrorTextAndButton();
                mMovieListFragment.showProgress();
                mMovieListPresenter.fetchingMoviesUseCase();
                showOption(Constants.TOP);
                break;
            case R.id.favorites:
                mMovieListPresenter.setChosenOption(Constants.FAVORITES);
                mMovieListFragment.hideErrorTextAndButton();
                mMovieListFragment.showProgress();
                mMovieListPresenter.fetchingMoviesUseCase();
                showOption(Constants.FAVORITES);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showOption(String option) {
        switch (option) {
            case Constants.POPULAR:
                mMenu.findItem(R.id.popular_movies_label).setVisible(true);
                mMenu.findItem(R.id.top_rated_label).setVisible(false);
                mMenu.findItem(R.id.favorite_label).setVisible(false);
                break;
            case Constants.TOP:
                mMenu.findItem(R.id.top_rated_label).setVisible(true);
                mMenu.findItem(R.id.popular_movies_label).setVisible(false);
                mMenu.findItem(R.id.favorite_label).setVisible(false);
                break;
            case Constants.FAVORITES:
                mMenu.findItem(R.id.favorite_label).setVisible(true);
                mMenu.findItem(R.id.top_rated_label).setVisible(false);
                mMenu.findItem(R.id.popular_movies_label).setVisible(false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.FAVORITE_REQUEST_CODE && resultCode == RESULT_OK) {
            updateMovieList(data.getIntExtra(Constants.FAVORITE_MOVIE_ID, 0));
        }
    }

    private void updateMovieList(int updatedMovieId) {
        // If the movie is not favorite any more, update the movieIdList
        if(mMovieListFragment.getMovieIdList().contains(updatedMovieId)) {
            mMovieListFragment.getMovieIdList().remove((Integer)updatedMovieId);
        } else {
            mMovieListFragment.getMovieIdList().add(updatedMovieId);
        }
    }
}
