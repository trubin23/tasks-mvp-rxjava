package ru.trubin23.tasks_mvp_rxjava.data.source.cache;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import ru.trubin23.tasks_mvp_rxjava.data.Task;

public interface TasksCacheDataSource {

    void setTasks(@NonNull List<Task> tasks);

    void irrelevantState();

    @Nullable
    List<Task> getTasks();

    @Nullable
    Task getTaskById(@NonNull String taskId);

    void addTask(@NonNull Task task);

    void removeTask(@NonNull String taskId);

    void completedTask(@NonNull String taskId, boolean completed);

    void clearCompletedTask();
}
