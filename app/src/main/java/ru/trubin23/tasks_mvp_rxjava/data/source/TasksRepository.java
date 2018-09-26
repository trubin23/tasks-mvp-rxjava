package ru.trubin23.tasks_mvp_rxjava.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;
import ru.trubin23.tasks_mvp_rxjava.data.source.cache.TasksCacheDataSource;
import ru.trubin23.tasks_mvp_rxjava.data.source.local.TasksLocalDataSource;

public class TasksRepository implements TasksMainDataSource {

    @Nullable
    private static TasksRepository INSTANCE = null;

    private final TasksDataSource mTasksRemoteDataSource;
    private final TasksLocalDataSource mTasksLocalDataSource;
    private final TasksCacheDataSource mTasksCacheDataSource;

    private boolean mForceRefresh = false;

    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                            @NonNull TasksLocalDataSource tasksLocalDataSource,
                            @NonNull TasksCacheDataSource tasksCacheDataSource) {
        mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
        mTasksCacheDataSource = tasksCacheDataSource;
    }

    public static TasksRepository getInstance(@NonNull TasksDataSource tasksRemoteDataSource,
                                              @NonNull TasksLocalDataSource tasksLocalDataSource,
                                              @NonNull TasksCacheDataSource tasksCacheDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource,
                    tasksLocalDataSource, tasksCacheDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        List<Task> tasks = mTasksCacheDataSource.getTasks();
        if (tasks != null) {
            return Flowable.fromIterable(mTasksCacheDataSource.getTasks()).toList().toFlowable();
        }

        return null;
        //if (mForceRefresh) {
        //    getTasksFromRemoteDataSource(true);
        //} else {
        //    getTasksFromLocalDataSource(true);
        //}
    }

    private Flowable<List<Task>> getTasksFromLocalDataSource(boolean handleErrors) {
        return null;
    }

    private Flowable<List<Task>> getTasksFromRemoteDataSource(boolean handleErrors) {
        return mTasksRemoteDataSource
                .getTasks()
                .flatMap(tasks -> {
                    mTasksCacheDataSource.setTasks(tasks);
                    mTasksLocalDataSource.setTasks(tasks);
                    mForceRefresh = false;
                    return Flowable.fromIterable(tasks).toList().toFlowable();
                });
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        return null;
    }

    @Override
    public void saveTask(@NonNull Task task) {

    }

    @Override
    public void updateTask(@NonNull Task task) {

    }

    @Override
    public void deleteTask(@NonNull String taskId) {

    }

    @Override
    public void completedTask(@NonNull String taskId, boolean completed) {

    }

    @Override
    public void clearCompletedTask() {

    }

    @Override
    public void refreshTasks() {

    }
}
