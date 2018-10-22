package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessingResponse<T> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            T body = response.body();
            if (body != null) {
                responseBody(body);
            } else {
                dataNotAvailable();
            }
        } else {
            dataNotAvailable();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        dataNotAvailable();
    }

    public void responseBody(@NonNull T body) {
    }

    public void dataNotAvailable() {
    }
}