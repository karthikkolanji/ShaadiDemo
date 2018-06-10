package com.startedup.base.ui.imdb;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.startedup.base.R;
import com.startedup.base.model.movies.ResultsItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopRatedMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ResultsItem> list;

    TopRatedMovieAdapter(List<ResultsItem> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView;

        itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_taop_rated_movie, parent, false);
        return new MovieViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
        movieViewHolder.bindView(movieViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_poster)
        ImageView ivPoster;
        @BindView(R.id.tv_movie_name)
        TextView tvMovieName;
        @BindView(R.id.tv_movie_desc)
        TextView tvMovieDesc;
        @BindView(R.id.tv_movie_date)
        TextView tvMovieDate;
        @BindView(R.id.tv_movie_rating)
        TextView tvMovieRating;

        MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(MovieViewHolder viewHolder, int position) {
            ResultsItem item=list.get(position);
            viewHolder.tvMovieName.setText(item.getTitle());
            viewHolder.tvMovieDesc.setText(item.getOverview());
            viewHolder.tvMovieDate.setText(item.getReleaseDate());
            //viewHolder.tvMovieRating.setText(item.getVoteCount());
            Glide.with(viewHolder.ivPoster.getContext()).load("http://image.tmdb.org/t/p/w185//"+item.getPosterPath())
                    .thumbnail(0.1f).into(viewHolder.ivPoster);

        }
    }
}