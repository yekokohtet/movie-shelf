package com.padcmyanmar.movieshelf.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.movieshelf.R;
import com.padcmyanmar.movieshelf.data.vo.PopularMoviesVO;
import com.padcmyanmar.movieshelf.delegates.MoviesItemDelegate;
import com.padcmyanmar.movieshelf.viewholders.PopularMoviesViewHolder;

/**
 * Created by yekokohtet on 12/20/17.
 */

public class PopularMoviesAdapter extends BaseRecyclerAdapter<PopularMoviesViewHolder, PopularMoviesVO> {

    private MoviesItemDelegate mMoviesItemDelegate;

    public PopularMoviesAdapter(Context context, MoviesItemDelegate moviesItemDelegate) {
        super(context);
        mMoviesItemDelegate = moviesItemDelegate;
    }

    @Override
    public PopularMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_popular_movies, parent, false);
        return new PopularMoviesViewHolder(view, mMoviesItemDelegate);
    }

}
