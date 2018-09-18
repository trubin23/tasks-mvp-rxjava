package ru.trubin23.tasks_mvp_rxjava.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksRepository implements TasksDataSource {

    @Nullable
    private static TasksRepository INSTANCE = null;

    private final TasksDataSource mTasksRemoteDataSource;
    private final TasksDataSource mTasksLocalDataSource;
    private final TasksDataSource mTasksCacheDataSource;

    private TasksRepository(@NonNull TasksDataSource tasksRemoteDataSource,
                            @NonNull TasksDataSource tasksLocalDataSource,
                            @NonNull TasksDataSource tasksCacheDataSource) {
        mTasksRemoteDataSource = tasksRemoteDataSource;
        mTasksLocalDataSource = tasksLocalDataSource;
        mTasksCacheDataSource = tasksCacheDataSource;
    }

    public static TasksRepository getInstance(@NonNull TasksDataSource tasksRemoteDataSource,
                                              @NonNull TasksDataSource tasksLocalDataSource,
                                              @NonNull TasksDataSource tasksCacheDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRepository(tasksRemoteDataSource,
                    tasksLocalDataSource, tasksCacheDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        return null;
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        return null;
    }
}
