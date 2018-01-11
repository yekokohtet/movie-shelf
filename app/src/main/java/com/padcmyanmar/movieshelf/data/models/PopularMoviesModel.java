package com.padcmyanmar.movieshelf.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.padcmyanmar.movieshelf.MovieShelfApp;
import com.padcmyanmar.movieshelf.data.persistent.MovieContract;
import com.padcmyanmar.movieshelf.data.vo.PopularMoviesVO;
import com.padcmyanmar.movieshelf.events.RestApiEvents;
import com.padcmyanmar.movieshelf.network.PopularMoviesDataAgentImpl;
import com.padcmyanmar.movieshelf.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yekokohtet on 12/20/17.
 */

public class PopularMoviesModel {

    private static PopularMoviesModel objInstance;

    private List<PopularMoviesVO> mPopularMovies;
    private int popularMoviesPageIndex = 1;

    private PopularMoviesModel() {
        EventBus.getDefault().register(this);
        mPopularMovies = new ArrayList<>();
    }

    public static PopularMoviesModel getInstance() {
        if (objInstance == null) {
            objInstance = new PopularMoviesModel();
        }
        return objInstance;
    }

    public void startLoadingPopularMovies(Context context) {
        PopularMoviesDataAgentImpl.getInstance().loadPopularMovies(AppConstants.ACCESS_TOKEN, popularMoviesPageIndex, context);
    }

    public List<PopularMoviesVO> getMovies() {
        return mPopularMovies;
    }

    public void loadMoreMovies(Context context) {
        PopularMoviesDataAgentImpl.getInstance().loadPopularMovies(AppConstants.ACCESS_TOKEN, popularMoviesPageIndex, context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onPopularMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mPopularMovies.addAll(event.getLoadedPopularMovies());
        popularMoviesPageIndex = event.getLoadedPageIndex() + 1;

        //TODO Logic to save the data in Persistence Layer

        ContentValues[] popularMoviesCVS = new ContentValues[event.getLoadedPopularMovies().size()];

        for (int index = 0; index < popularMoviesCVS.length; index++) {
            popularMoviesCVS[index] = event.getLoadedPopularMovies().get(index).parseToContentValues();
        }

        int insertedRow = event.getContext().getContentResolver().bulkInsert(MovieContract.PopularMovieEntry.CONTENT_URI, popularMoviesCVS);

        Log.d(MovieShelfApp.LOG_TAG, "Inserted Row : " + insertedRow);
    }

    public void forceRefreshMovies(Context context) {
        mPopularMovies = new ArrayList<>();
        popularMoviesPageIndex = 1;
        startLoadingPopularMovies(context);
    }
}
