package com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.IMovieListAdapterContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Movie;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieList.IMovieListContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.ActivityUtils;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 22/02/18.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> implements IMovieListAdapterContract {

    private final Context mCtx;
    private final IMovieListContract mIMovieListContract;
    private final ArrayList<Movie> mMovieList = new ArrayList<>();
    private final ArrayList<Integer> mMovieFavoriteIdList = new ArrayList<>();

    public MovieListAdapter(Context context, IMovieListContract movieListContract) {
        mCtx = context;
        mIMovieListContract = movieListContract;
    }

    @Override
    public void updateMovieList(ArrayList<Movie> updatedMovieList) {
        mMovieList.clear();
        mMovieList.addAll(updatedMovieList);
        notifyDataSetChanged();
    }

    @Override
    public void getFavoriteMovieIdList(ArrayList<Integer> favoriteMovieIdList) {
        mMovieFavoriteIdList.clear();
        mMovieFavoriteIdList.addAll(favoriteMovieIdList);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mCtx).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        // Get year from release date
        String[] releaseDate = movie.getReleaseDate().split("-");
        holder.movieTitleTextView.setText(movie.getTitle());
        holder.movieDateTextView.setText(releaseDate[0]);
        Picasso.with(mCtx).load(ActivityUtils.getMovieImageUri(movie.getImagePath()))
                .placeholder(R.drawable.ic_tmdb_logo)
                .into(holder.moviePosterImageView);
        // Read from the local data if the user select sort by favorites, otherwise from the favorite list
        if (Constants.FAVORITES.equals(mIMovieListContract.getChosenOption()) ?
                movie.isFavorite() : mMovieFavoriteIdList.contains(movie.getMovieId())) {
            mMovieList.get(position).setFavorite(true);
            holder.favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_favorite, null));
        } else {
            holder.favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_no_favorite, null));
        }
    }

    @Override
    public int getItemCount() {
        return mMovieList != null ? mMovieList.size() : 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_movie_title)
        TextView movieTitleTextView;

        @BindView(R.id.tv_movie_date)
        TextView movieDateTextView;

        @BindView(R.id.iv_movie_poster)
        ImageView moviePosterImageView;

        @BindView(R.id.iv_favorite_button)
        ImageView favoriteButtonImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            favoriteButtonImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.equals(favoriteButtonImageView)) {
                Movie movie = mMovieList.get(getAdapterPosition());
                if (movie.isFavorite()) {
                    favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_no_favorite, null));
                    int rowsDeleted = ActivityUtils.deleteMovie(movie.getMovieId(), mCtx.getContentResolver());
                    if (rowsDeleted == 0) {
                        Toast.makeText(mCtx, R.string.not_removed_favorite_movies_error, Toast.LENGTH_SHORT).show();
                        favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_favorite, null));
                    } else {
                        mMovieFavoriteIdList.remove(movie.getMovieId());
                        mMovieList.get(getAdapterPosition()).setFavorite(false);
                        if (Constants.FAVORITES.equals(mIMovieListContract.getChosenOption())) {
                            movie.setFavorite(false);
                            mMovieList.remove(movie);
                            if (mMovieList.isEmpty()) {
                                mIMovieListContract.showNoFavoriteMovies();
                            }
                        }
                        notifyDataSetChanged();
                    }
                } else {
                    favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_favorite, null));
                    mMovieList.get(getAdapterPosition()).setFavorite(true);
                    Uri uri = ActivityUtils.insertMovie(mMovieList.get(getAdapterPosition()), mCtx.getContentResolver());
                    if (uri == null) {
                        Toast.makeText(mCtx, R.string.not_added_favorite_movies_error, Toast.LENGTH_SHORT).show();
                        favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_no_favorite, null));
                    } else {
                        mMovieFavoriteIdList.add(movie.getMovieId());
                    }
                }
            } else {
                mIMovieListContract.onClickListener(mMovieList.get(getAdapterPosition()));
            }
        }
    }
}

