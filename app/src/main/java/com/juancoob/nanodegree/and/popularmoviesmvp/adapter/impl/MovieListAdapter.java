package com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl;

import android.content.Context;
import android.database.Cursor;
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
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.MovieContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.repository.database.impl.MovieDb;
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
    private ArrayList<Movie> mMovieList = new ArrayList<>();
    private ArrayList<Integer> mMovieFavoriteListIds = new ArrayList<>();

    public MovieListAdapter(Context context, IMovieListContract movieListContract) {
        mCtx = context;
        mIMovieListContract = movieListContract;
        Cursor cursor = MovieDb.getInstance().getFavoriteMovieIds();
        parseCursorToMovieIds(cursor);
        cursor.close();
    }

    @Override
    public void updateMovieList(ArrayList<Movie> updatedMovieList) {
        mMovieList.clear();
        mMovieList.addAll(updatedMovieList);
        notifyDataSetChanged();
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
        if (mMovieFavoriteListIds.contains(movie.getMovieId())) {
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

    private void parseCursorToMovieIds(Cursor cursor) {
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            mMovieFavoriteListIds.add(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));
        }
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
                    if (MovieDb.getInstance().removeMovie(movie.getMovieId()) == 0) {
                        Toast.makeText(mCtx, R.string.not_removed_favorite_movies_error, Toast.LENGTH_SHORT).show();
                        favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_favorite, null));
                    } else {
                        mMovieFavoriteListIds.remove(movie.getMovieId());
                        mMovieList.get(getAdapterPosition()).setFavorite(false);
                        if (Constants.FAVORITES.equals(mIMovieListContract.getChosenOption())) {
                            mMovieList.remove(getAdapterPosition());
                            if (mMovieFavoriteListIds.isEmpty()) {
                                mIMovieListContract.showNoFavoriteMovies();
                            }
                        }
                        notifyDataSetChanged();
                    }
                } else {
                    favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_favorite, null));
                    if (MovieDb.getInstance().addFavoriteMovie(movie) == -1) {
                        Toast.makeText(mCtx, R.string.not_added_favorite_movies_error, Toast.LENGTH_SHORT).show();
                        favoriteButtonImageView.setImageDrawable(VectorDrawableCompat.create(mCtx.getResources(), R.drawable.ic_no_favorite, null));
                    } else {
                        mMovieFavoriteListIds.add(movie.getMovieId());
                        mMovieList.get(getAdapterPosition()).setFavorite(true);
                    }
                }
            } else {
                mIMovieListContract.onClickListener(mMovieList.get(getAdapterPosition()));
            }
        }
    }
}

