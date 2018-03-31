package com.juancoob.nanodegree.and.popularmoviesmvp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 25/03/18.
 */

public class ReviewResponse {

    @SerializedName("id")
    private int mMovieId;

    @SerializedName("page")
    private int mPage;

    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("total_pages")
    private int mTotalPages;

    @SerializedName("results")
    private ArrayList<Review> mReviewList;

    public int getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        mPage = page;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public void setTotalResults(int totalResults) {
        mTotalResults = totalResults;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int totalPages) {
        mTotalPages = totalPages;
    }

    public ArrayList<Review> getReviewList() {
        return mReviewList;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        mReviewList = reviewList;
    }
}
