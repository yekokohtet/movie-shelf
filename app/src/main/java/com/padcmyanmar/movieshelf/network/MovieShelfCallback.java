package com.padcmyanmar.movieshelf.network;


import com.padcmyanmar.movieshelf.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yekokohtet on 12/20/17.
 */

public class MovieShelfCallback<T extends MovieShelfResponse> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        MovieShelfResponse movieShelfResponse = response.body();
        if (movieShelfResponse == null) {
            RestApiEvents.ErrorInvokingAPIEvent errorEvent
                    = new RestApiEvents.ErrorInvokingAPIEvent("No data could be loaded for now. Pls try again later.");
            EventBus.getDefault().post(errorEvent);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        RestApiEvents.ErrorInvokingAPIEvent errorEvent = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
        EventBus.getDefault().post(errorEvent);
    }
}
