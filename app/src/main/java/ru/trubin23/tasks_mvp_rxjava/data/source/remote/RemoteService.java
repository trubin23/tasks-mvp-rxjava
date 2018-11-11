package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RemoteService {

    @GET("/api_mvp_rx/tasks")
    Call<List<NetworkTask>> getTasks();

    @GET("/api_mvp_rx/tasks/{id}")
    Call<NetworkTask> getTask(@Path("id") String id);

    @POST("/api_mvp_rx/tasks")
    Call<NetworkTask> addTask(@Body NetworkTask task);

    @PUT("/api_mvp_rx/tasks/{id}")
    Call<NetworkTask> updateTask(@Path("id") String id, @Body NetworkTask task);

    @PUT("/api_mvp_rx/tasks/{id}")
    Call<NetworkTask> completeTask(@Path("id") String id, @Body StatusOfTask statusOfTask);

    @DELETE("/api_mvp_rx/tasks/{id}")
    Call<NetworkTask> deleteTask(@Path("id") String id);

    @DELETE("/api_mvp_rx/tasks/completed")
    Call<Integer> deleteCompletedTasks();
}
