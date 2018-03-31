package com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.IMovieReviewAdapterContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 29/03/18.
 */
public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieReviewViewHolder>
        implements IMovieReviewAdapterContract {

    private Context mCtx;
    private ArrayList<Review> mReviewList = new ArrayList<>();

    public MovieReviewAdapter(Context context) {
        mCtx = context;
    }

    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mCtx).inflate(R.layout.item_movie_review, parent, false);
        return new MovieReviewViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewViewHolder holder, int position) {
        Review review = mReviewList.get(position);
        holder.authorTextView.setText(review.getmAuthor());
        holder.reviewTextView.setText(review.getmReview());
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    @Override
    public void updateMovieReviews(ArrayList<Review> reviewList) {
        mReviewList.clear();
        mReviewList.addAll(reviewList);
        notifyDataSetChanged();
    }

    public class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author)
        TextView authorTextView;

        @BindView(R.id.tv_review)
        TextView reviewTextView;


        public MovieReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
