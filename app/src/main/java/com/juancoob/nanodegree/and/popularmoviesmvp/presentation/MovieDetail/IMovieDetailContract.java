package com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail;

import android.net.Uri;

import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Review;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Video;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.presenters.BasePresenter;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.ui.BaseView;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 20/02/18.
 */

public interface IMovieDetailContract {

    void onClickVideoListener(Uri videoUri);
    void onLongClickVideoListener(Uri videoUri);

    interface View extends BaseView {
        void showMovieReviews(ArrayList<Review> reviews);
        void showMovieVideos(ArrayList<Video> videos);
        ArrayList<Video> getMovieVideoList();
        ArrayList<Review> getMovieReviewList();
    }

    interface Presenter extends BasePresenter {
    }

}
