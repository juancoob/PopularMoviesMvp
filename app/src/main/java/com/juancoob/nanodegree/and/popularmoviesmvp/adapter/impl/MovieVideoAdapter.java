package com.juancoob.nanodegree.and.popularmoviesmvp.adapter.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.juancoob.nanodegree.and.popularmoviesmvp.R;
import com.juancoob.nanodegree.and.popularmoviesmvp.adapter.IMovieVideoAdapterContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.domain.model.Video;
import com.juancoob.nanodegree.and.popularmoviesmvp.presentation.MovieDetail.IMovieDetailContract;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.ActivityUtils;
import com.juancoob.nanodegree.and.popularmoviesmvp.util.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Antonio Cobos Obrero on 31/03/18.
 */

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.MovieVideoViewHolder>
        implements IMovieVideoAdapterContract {

    private Context mCtx;
    private ArrayList<Video> mVideoList = new ArrayList<>();
    private IMovieDetailContract mIMovieDetailContract;

    public MovieVideoAdapter(Context context, IMovieDetailContract iMovieDetailContract) {
        mCtx = context;
        mIMovieDetailContract = iMovieDetailContract;
    }


    @NonNull
    @Override
    public MovieVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_video, parent, false);
        return new MovieVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideoViewHolder holder, int position) {
        Video video = mVideoList.get(position);
        if (video.getSite().equals(Constants.YOUTUBE)) {
            Picasso.with(mCtx)
                    .load(ActivityUtils.getYoutubeVideoImageUri(video.getKey()))
                    .placeholder(R.drawable.ic_tmdb_logo)
                    .into(holder.videoImageView);
        } else {
            Picasso.with(mCtx)
                    .load(R.drawable.ic_video)
                    .placeholder(R.drawable.ic_tmdb_logo)
                    .into(holder.videoImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    @Override
    public void updateMovieVideos(ArrayList<Video> videoList) {
        mVideoList.clear();
        mVideoList.addAll(videoList);
        notifyDataSetChanged();
    }

    public class MovieVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @BindView(R.id.iv_video_image)
        public ImageView videoImageView;

        public MovieVideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mIMovieDetailContract.onClickVideoListener(
                    ActivityUtils.getYoutubeVideoUri(mVideoList.get(getAdapterPosition()).getKey()));
        }

        @Override
        public boolean onLongClick(View view) {
            mIMovieDetailContract.onLongClickVideoListener(
                    ActivityUtils.getYoutubeVideoUri(mVideoList.get(getAdapterPosition()).getKey()));
            return true;
        }
    }
}
