package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RemoteService {

    @GET("/api_mvp_kotlin/tasks")
    Call<List<NetworkTask>> getTasks();

    @GET("/api_mvp_kotlin/tasks/{id}")
    Call<NetworkTask> getTask(@Path("id") String id);

    @POST("/api_mvp_kotlin/tasks")
    Call<NetworkTask> addTask(@Body NetworkTask task);
}
