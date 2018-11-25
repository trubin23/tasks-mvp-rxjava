package ru.trubin23.tasks_mvp_rxjava.data.source.remote;

import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

    private static final String BASE_URL = "https://trubin23.ru";

    private static RemoteService sRemoteService;

    @NonNull
    private static RemoteService getRemoteService() {
        if (sRemoteService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            sRemoteService = retrofit.create(RemoteService.class);
        }
        return sRemoteService;
    }

    static Flowable<List<NetworkTask>> getTasks() {
        return getRemoteService().getTasks();
    }

    static Flowable<NetworkTask> getTask(@NonNull String taskId) {
        return getRemoteService().getTask(taskId);
    }

    static void addTask(@NonNull NetworkTask task, @NonNull Callback<NetworkTask> callback) {
        RemoteService remoteService = getRemoteService();
        remoteService.addTask(task).enqueue(callback);
    }

    static void updateTask(@NonNull NetworkTask task, @NonNull Callback<NetworkTask> callback) {
        RemoteService remoteService = getRemoteService();
        remoteService.updateTask(task.getId(), task).enqueue(callback);
    }

    static void completeTask(@NonNull String taskId, @NonNull StatusOfTask statusOfTask,
                             @NonNull Callback<NetworkTask> callback){
        RemoteService remoteService = getRemoteService();
        remoteService.completeTask(taskId, statusOfTask).enqueue(callback);
    }

    static void deleteTask(@NonNull String taskId, @NonNull Callback<NetworkTask> callback) {
        RemoteService remoteService = getRemoteService();
        remoteService.deleteTask(taskId).enqueue(callback);
    }

    static void deleteCompletedTasks(@NonNull Callback<Integer> callback){
        RemoteService remoteService = getRemoteService();
        remoteService.deleteCompletedTasks().enqueue(callback);
    }
}