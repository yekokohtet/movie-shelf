package com.padcmyanmar.movieshelf.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.padcmyanmar.movieshelf.R;
import com.padcmyanmar.movieshelf.adapters.PopularMoviesAdapter;
import com.padcmyanmar.movieshelf.components.EmptyViewPod;
import com.padcmyanmar.movieshelf.components.SmartRecyclerView;
import com.padcmyanmar.movieshelf.components.SmartScrollListener;
import com.padcmyanmar.movieshelf.data.models.PopularMoviesModel;
import com.padcmyanmar.movieshelf.data.persistent.MovieContract;
import com.padcmyanmar.movieshelf.data.vo.PopularMoviesVO;
import com.padcmyanmar.movieshelf.delegates.MoviesItemDelegate;
import com.padcmyanmar.movieshelf.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularMoviesActivity extends AppCompatActivity
        implements MoviesItemDelegate, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIES_LIST_LOADER_ID = 1001;

    @BindView(R.id.rv_movies)
    SmartRecyclerView rvMovies;

    @BindView(R.id.vp_empty_popular_movies)
    EmptyViewPod emptyViewPod;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private SmartScrollListener mSmartScrollListener;

    private PopularMoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this, this);

        PopularMoviesModel.getInstance().startLoadingPopularMovies(getApplicationContext());

        rvMovies.setmEmptyView(emptyViewPod);

        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mMoviesAdapter = new PopularMoviesAdapter(getApplicationContext(), this);
        rvMovies.setAdapter(mMoviesAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
//                Snackbar.make(rvMovies, "This is all the data for Now.", Snackbar.LENGTH_LONG).show();
                PopularMoviesModel.getInstance().loadMoreMovies(getApplicationContext());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PopularMoviesModel.getInstance().forceRefreshMovies(getApplicationContext());
            }
        });

        rvMovies.addOnScrollListener(mSmartScrollListener);

        getSupportLoaderManager().initLoader(MOVIES_LIST_LOADER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        List<PopularMoviesVO> moviesList = PopularMoviesModel.getInstance().getMovies();
        if (!moviesList.isEmpty()) {
            mMoviesAdapter.setNewData(moviesList);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onTapImageFullScreenButton() {
        Toast.makeText(this, "Hello World!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTapMovieOverviewButton() {
        Intent intent = PopularMoviesDetailsActivity.newIntent(getApplicationContext());
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPopularMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
//        mMoviesAdapter.appendNewData(event.getLoadedPopularMovies());
//        swipeRefreshLayout.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvMovies, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(), MovieContract.PopularMovieEntry.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<PopularMoviesVO> moviesList = new ArrayList<>();

            do {
                PopularMoviesVO movie = PopularMoviesVO.parseFromCursor(data);
                moviesList.add(movie);
            } while (data.moveToNext());

            mMoviesAdapter.setNewData(moviesList);
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
