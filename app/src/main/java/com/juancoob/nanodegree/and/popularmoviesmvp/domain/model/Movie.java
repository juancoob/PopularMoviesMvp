package com.juancoob.nanodegree.and.popularmoviesmvp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Juan Antonio Cobos Obrero on 20/02/18.
 */

public class Movie implements Parcelable {


    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @SerializedName("id")
    private int mMovieId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("poster_path")
    private String mImagePath;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("vote_average")
    private String mVoteAverage;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("vote_count")
    private int mVoteCount;

    @SerializedName("video")
    private boolean mVideo;

    @SerializedName("popularity")
    private float mPopularity;

    @SerializedName("original_language")
    private String mOriginalLanguage;

    @SerializedName("original_title")
    private String mOriginalTitle;

    @SerializedName("genre_ids")
    private ArrayList<Integer> mGenreIdsList;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @SerializedName("adult")
    private boolean mIsAdult;

    public Movie() {
    }

    private Movie(Parcel in) {
        mMovieId = in.readInt();
        mTitle = in.readString();
        mImagePath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readString();
        mReleaseDate = in.readString();
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int movieId) {
        mMovieId = movieId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String mImagePath) {
        this.mImagePath = mImagePath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int voteCount) {
        mVoteCount = voteCount;
    }

    public boolean isVideo() {
        return mVideo;
    }

    public void setVideo(boolean video) {
        mVideo = video;
    }

    public float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(float popularity) {
        mPopularity = popularity;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        mOriginalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public ArrayList<Integer> getGenreIdsList() {
        return mGenreIdsList;
    }

    public void setGenreIdsList(ArrayList<Integer> genreIdsList) {
        mGenreIdsList = genreIdsList;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public boolean isIsAdult() {
        return mIsAdult;
    }

    public void setIsAdult(boolean isAdult) {
        mIsAdult = isAdult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mMovieId);
        parcel.writeString(mTitle);
        parcel.writeString(mImagePath);
        parcel.writeString(mOverview);
        parcel.writeString(mVoteAverage);
        parcel.writeString(mReleaseDate);
        parcel.writeInt(mVoteCount);
        parcel.writeByte((byte) (mVideo ? 1 : 0));
        parcel.writeFloat(mPopularity);
        parcel.writeString(mOriginalLanguage);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mBackdropPath);
        parcel.writeByte((byte) (mIsAdult ? 1 : 0));
    }
}
