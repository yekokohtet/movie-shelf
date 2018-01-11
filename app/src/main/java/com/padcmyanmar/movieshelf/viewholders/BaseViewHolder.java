package com.padcmyanmar.movieshelf.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.padcmyanmar.movieshelf.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yekokohtet on 12/20/17.
 */

public abstract class BaseViewHolder<W> extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.btn_movie_overview)
    Button btnMovieOverview;

    @BindView(R.id.btn_full_screen)
    ImageView btnFullScreen;

    private W wdata;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        btnMovieOverview.setOnClickListener(this);
        btnFullScreen.setOnClickListener(this);
    }

    public abstract void setData(W data);
}
