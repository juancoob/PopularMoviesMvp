package com.juancoob.nanodegree.and.popularmoviesmvp.adapter;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 29/03/18.
 */

public interface IMovieReviewAdapterContract {
    void updateMovieReviews(ArrayList<Review> reviewList);
}
