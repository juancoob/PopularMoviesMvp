package com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.usecase.base.UseCase;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 27/03/18.
 */

public interface FetchingMovieReviewsUseCase extends UseCase{

    interface Callback {
        void onReviewsRetrieved(final ArrayList<Review> movieReviewList);
        void noInternetConnection();
    }

}
