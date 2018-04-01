package com.juancoob.nanodegree.and.popularmoviesmvp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Juan Antonio Cobos Obrero on 25/03/18.
 */

public class Video implements Parcelable {

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };

    @SerializedName("id")
    private String mVideoId;

    @SerializedName("iso_639_1")
    private String mIso6391;

    @SerializedName("iso_3166_1")
    private String mIso31661;

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;

    @SerializedName("site")
    private String mSite;

    @SerializedName("size")
    private int mSize;

    @SerializedName("type")
    private String mType;

    protected Video(Parcel in) {
        mVideoId = in.readString();
        mIso6391 = in.readString();
        mIso31661 = in.readString();
        mKey = in.readString();
        mName = in.readString();
        mSite = in.readString();
        mSize = in.readInt();
        mType = in.readString();
    }

    public String getVideoId() {
        return mVideoId;
    }

    public void setVideoId(String mVideoId) {
        this.mVideoId = mVideoId;
    }

    public String getIso6391() {
        return mIso6391;
    }

    public void setIso6391(String mIso6391) {
        this.mIso6391 = mIso6391;
    }

    public String getIso31661() {
        return mIso31661;
    }

    public void setIso31661(String mIso31661) {
        this.mIso31661 = mIso31661;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getSite() {
        return mSite;
    }

    public void setSite(String mSite) {
        this.mSite = mSite;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int mSize) {
        this.mSize = mSize;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mVideoId);
        parcel.writeString(mIso6391);
        parcel.writeString(mIso31661);
        parcel.writeString(mKey);
        parcel.writeString(mName);
        parcel.writeString(mSite);
        parcel.writeInt(mSize);
        parcel.writeString(mType);
    }
}
