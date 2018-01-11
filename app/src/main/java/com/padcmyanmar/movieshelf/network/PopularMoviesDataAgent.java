package com.padcmyanmar.movieshelf.network;

import android.content.Context;

/**
 * Created by yekokohtet on 12/20/17.
 */

public interface PopularMoviesDataAgent {

    void loadPopularMovies(String accessToken, int pageNo, Context context);

}
