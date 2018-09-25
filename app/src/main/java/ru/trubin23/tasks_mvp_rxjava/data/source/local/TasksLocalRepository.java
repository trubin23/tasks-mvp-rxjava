package ru.trubin23.tasks_mvp_rxjava.data.source.local;

import android.support.annotation.NonNull;

import com.google.common.base.Optional;

import java.util.List;

import io.reactivex.Flowable;
import ru.trubin23.tasks_mvp_rxjava.data.Task;

public class TasksLocalRepository implements TasksLocalDataSource {


    @Override
    public void setTasks(@NonNull List<Task> tasks) {

    }

    @Override
    public Flowable<List<Task>> getTasks() {
        return null;
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
}
