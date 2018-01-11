package com.padcmyanmar.movieshelf.network.responses;

import com.google.gson.annotations.SerializedName;
import com.padcmyanmar.movieshelf.data.vo.PopularMoviesVO;
import com.padcmyanmar.movieshelf.network.MovieShelfResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yekokohtet on 12/20/17.
 */

public class GetPopularMoviesResponse extends MovieShelfResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("page")
    private int page;

    @SerializedName("popular-movies")
    private List<PopularMoviesVO> popularMoviesList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public int getPage() {
        return page;
    }

    public List<PopularMoviesVO> getPopularMoviesList() {
        if (popularMoviesList == null) {
            popularMoviesList = new ArrayList<>();
        }
        return popularMoviesList;
    }
}