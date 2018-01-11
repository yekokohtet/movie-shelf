package com.padcmyanmar.movieshelf.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.padcmyanmar.movieshelf.R;
import com.padcmyanmar.movieshelf.data.vo.PopularMoviesVO;
import com.padcmyanmar.movieshelf.delegates.MoviesItemDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yekokohtet on 12/20/17.
 */

public class PopularMoviesViewHolder extends BaseViewHolder<PopularMoviesVO> {

    private MoviesItemDelegate mMoviesItemDelegate;

    @BindView(R.id.tv_cinema_name)
    TextView tvCinemaName;

    @BindView(R.id.tv_cinema_rating)
    TextView tvCinemaRating;

    @BindView(R.id.tv_cinema_genre)
    TextView tvCinemaGenre;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.iv_cinema_poster)
    ImageView ivCinemaPoster;

    public PopularMoviesViewHolder(View itemView, MoviesItemDelegate moviesItemDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mMoviesItemDelegate = moviesItemDelegate;
    }

    @Override
    public void setData(PopularMoviesVO data) {
        tvCinemaName.setText(data.getTitle());
        tvCinemaRating.setText(String.valueOf(data.getVoteAverage()));
        float rate = data.getVoteAverage() - 2;
        ratingBar.setRating(rate);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_placeholder_poster)
                .centerCrop();

        Glide
                .with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/original" + data.getPosterPath())
                .apply(requestOptions)
                .into(ivCinemaPoster);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_full_screen:
                mMoviesItemDelegate.onTapImageFullScreenButton();
                break;

            case R.id.btn_movie_overview:
                mMoviesItemDelegate.onTapMovieOverviewButton();
                break;
        }

    }

}
